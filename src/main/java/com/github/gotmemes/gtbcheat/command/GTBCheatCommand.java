package com.github.gotmemes.gtbcheat.command;

import com.github.gotmemes.gtbcheat.GTBCheat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;

/**
 * An example command implementing the Command api of OneConfig.
 * Registered in GTBCheat.java with `CommandManager.INSTANCE.registerCommand(new ExampleCommand());`
 *
 * @see Command
 * @see Main
 * @see ExampleMod
 */
@Command(value = GTBCheat.MODID, description = "Access the " + GTBCheat.NAME + " GUI.")
public class GTBCheatCommand {
    @Main
    private void handle() {
        GTBCheat.INSTANCE.config.openGui();
    }
}