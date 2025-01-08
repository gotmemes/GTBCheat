package com.github.gotmemes.gtbcheat.hud;

import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.TextHud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawUtil;
import cc.polyfrost.oneconfig.events.event.LocrawEvent;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import com.github.gotmemes.gtbcheat.config.GTBCheatConfig;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An example OneConfig HUD that is started in the config and displays text.
 *
 * @see GTBCheatConfig#hud
 */
public class GTBCheatHud extends TextHud {
    private boolean isInGTB = false;
    private boolean isRunning = false;
    private String[] cachedWordList = null;
    private List<String> matchingWords = new ArrayList<>();
    private String hint = "";
    private ActionBarSubscriber actionBarSubscriber;

    public GTBCheatHud() {
        super(true, 0, 0);
        EventManager.INSTANCE.register(this);
    }

    private void loadWordList() {
        final String FILE_PATH = "/wordlist.csv";
        List<String> words = new ArrayList<>();

        try (
            BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream(FILE_PATH)
                )
            )
        ) {
            // skip header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }

            cachedWordList = words.toArray(new String[0]);
            System.out.println("WORDLIST: " + Arrays.toString(cachedWordList));

        } catch (Exception e) {
            System.err.println("Error reading file " + FILE_PATH + ": " + e.getMessage());
            // Empty array in case of error
            cachedWordList = new String[0];
        }
    }

    private void unloadWordList() {
        cachedWordList = null;
    }

    @Subscribe
    public void onLocraw(LocrawEvent event) {
        final String GTB_GAME_MODE = "BUILD_BATTLE_GUESS_THE_BUILD";
        isInGTB = GTB_GAME_MODE.equals(event.info.getGameMode());
        if (isInGTB && !isRunning) {
            // load word list and register listeners
            isRunning = true;
            loadWordList();
            actionBarSubscriber = new ActionBarSubscriber();
            MinecraftForge.EVENT_BUS.register(actionBarSubscriber);
        } else if (!isInGTB && isRunning) {
            // unload word list and unregister listeners
            isRunning = false;
            unloadWordList();
            MinecraftForge.EVENT_BUS.unregister(actionBarSubscriber);
            actionBarSubscriber = null;
            matchingWords = new ArrayList<>();
            hint = "";
        }
    }

    private class ActionBarSubscriber {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onChatReceive(ClientChatReceivedEvent event) {
            final String THEME_HINT_PREFIX = "§bThe theme is §e";
            String actionBar = event.message.getUnformattedTextForChat();
            if (actionBar.startsWith(THEME_HINT_PREFIX)) {
                GTBCheatHud.this.hint = actionBar.substring(THEME_HINT_PREFIX.length());
                GTBCheatHud.this.matchingWords = GTBCheatHud.this.findWords(GTBCheatHud.this.hint);
            }
        }
    }

    private List<String> findWords(String hint) {
        if (cachedWordList == null) return new ArrayList<>();

        int wordLength = hint.length();
        List<String> matches = new ArrayList<>();

        for (String word : cachedWordList) {
            if (word.length() != wordLength) continue;

            if (isWordMatch(word, hint)) {
                matches.add(word);
            }
        }
        return matches;
    }

    private boolean isWordMatch(String word, String hint) {
        for (int i = 0; i < word.length(); i++) {
            char hintChar = hint.charAt(i);
            char wordChar = word.charAt(i);

            if (hintChar != '_' && hintChar != wordChar) {
                return false;
            }
            if (hintChar == '_' && wordChar == ' ') {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void getLines(List<String> lines, boolean example) {
        if (example) {
            lines.add("GTB Cheat: Example Hint");
            lines.add("Example Word");
            return;
        }
        if (isInGTB) {
            lines.add("GTB Cheat: " + hint);

            StringBuilder line = new StringBuilder();
            for (String word : matchingWords) {
                if (line.length() == 0) {
                    line.append(word);
                } else {
                    line.append(", ").append(word);
                }

                if (line.length() > GTBCheatConfig.minWidth) {
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
            }
            if (line.length() > 0) {
                lines.add(line.toString());
            }
        }
    }

    @Override
    public void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        if (lines == null || lines.size() == 0) return;

        float textY = y;
        boolean header = true;
        for (String line : lines) {
            if (header) {
                TextRenderer.drawScaledString(line, x, y, GTBCheatConfig.headerColor.getRGB(),
                        TextRenderer.TextType.toType(textType), scale);
                textY += 12 * scale;
                header = false;
                continue;
            }
            drawLine(line, x, textY, scale);
            textY += 12 * scale;
        }
    }
}
