package com.github.gotmemes.gtbcheat.config;

import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Number;
import com.github.gotmemes.gtbcheat.GTBCheat;
import com.github.gotmemes.gtbcheat.hud.GTBCheatHud;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See <a href="https://docs.polyfrost.cc/oneconfig/config/adding-options">this link</a> for more config Options
 */
public class GTBCheatConfig extends Config {

    @HUD(name = "GTB Cheat HUD")
    public GTBCheatHud hud = new GTBCheatHud();

    @Number(
            name = "Minimum Number of Characters for New Line",
            min = 0, max = 100,
            step = 1
    )
    public static int minWidth = 60;

    public GTBCheatConfig() {
        super(new Mod(GTBCheat.NAME, ModType.UTIL_QOL), GTBCheat.MODID + ".json");
        initialize();
    }
}

