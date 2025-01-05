package com.github.gotmemes.gtbcheat.command;

import com.github.gotmemes.gtbcheat.GTBCheat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;

/**
 * A command implementing the Command api of OneConfig.
 * Registered in GTBCheat.java with `CommandManager.INSTANCE.registerCommand(new GTBCheatCommand());`
 *
 * @see Command
 * @see Main
 * @see GTBCheat
 */
@Command(value = GTBCheat.MODID, description = "Access the " + GTBCheat.NAME + " GUI.")
public class GTBCheatCommand {
    @Main
    private void handle() {
        GTBCheat.INSTANCE.config.openGui();
    }
}