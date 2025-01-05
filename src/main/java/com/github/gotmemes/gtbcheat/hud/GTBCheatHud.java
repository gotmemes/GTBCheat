package com.github.gotmemes.gtbcheat.hud;

import cc.polyfrost.oneconfig.hud.TextHud;
import com.github.gotmemes.gtbcheat.config.GTBCheatConfig;
import java.util.List;

/**
 * An example OneConfig HUD that is started in the config and displays text.
 *
 * @see TestConfig#hud
 */
public class GTBCheatHud extends TextHud {
    public GTBCheatHud() {
        super(true, 0, 0);
    }

    @Override
    protected void getLines(List<String> lines, boolean example) {
        lines.add("I'm line 1 of the example HUD");
        lines.add("I'm line 2 of the example HUD");
        lines.add("I'm line 3 of the example HUD");
    }
}