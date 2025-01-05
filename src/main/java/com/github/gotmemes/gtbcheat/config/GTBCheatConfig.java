package com.github.gotmemes.gtbcheat.config;

import com.github.gotmemes.gtbcheat.GTBCheat;
import com.github.gotmemes.gtbcheat.hud.GTBCheatHud;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See <a href="https://docs.polyfrost.cc/oneconfig/config/adding-options">this link</a> for more config Options
 */
public class GTBCheatConfig extends Config {
    @HUD(
            name = "GTB Cheat HUD"
    )
    public GTBCheatHud hud = new GTBCheatHud();

    @Switch(
            name = "Example Switch",
            size = OptionSize.SINGLE // Optional
    )
    public static boolean exampleSwitch = false; // The default value for the boolean Switch.

    @Slider(
            name = "Example Slider",
            min = 0f, max = 100f, // Minimum and maximum values for the slider.
            step = 10 // The amount of steps that the slider should have.
    )
    public static float exampleSlider = 50f; // The default value for the float Slider.

    @Dropdown(
            name = "Example Dropdown", // Name of the Dropdown
            options = {"Option 1", "Option 2", "Option 3", "Option 4"} // Options available.
    )
    public static int exampleDropdown = 1; // Default option (in this case "Option 2")

    public GTBCheatConfig() {
        super(new Mod(GTBCheat.NAME, ModType.UTIL_QOL), GTBCheat.MODID + ".json");
        initialize();
    }
}
