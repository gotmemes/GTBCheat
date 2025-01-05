package com.github.gotmemes.gtbcheat;

import com.github.gotmemes.gtbcheat.command.GTBCheatCommand;
import com.github.gotmemes.gtbcheat.config.GTBCheatConfig;
import cc.polyfrost.oneconfig.events.event.InitializationEvent;
import net.minecraftforge.fml.common.Mod;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * The entrypoint of the Example Mod that initializes it.
 *
 * @see Mod
 * @see InitializationEvent
 */
@Mod(modid = GTBCheat.MODID, name = GTBCheat.NAME, version = GTBCheat.VERSION)
public class GTBCheat {

    // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @Mod.Instance(MODID)
    public static GTBCheat INSTANCE; // Adds the instance of the mod, so we can access other variables.
    public static GTBCheatConfig config;

    // Register the config and commands.
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new GTBCheatConfig();
        CommandManager.INSTANCE.registerCommand(new GTBCheatCommand());
    }
}
