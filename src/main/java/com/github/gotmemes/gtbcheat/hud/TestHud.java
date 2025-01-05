package com.github.gotmemes.gtbcheat.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import com.github.gotmemes.gtbcheat.config.TestConfig;

/**
 * An example OneConfig HUD that is started in the config and displays text.
 *
 * @see TestConfig#hud
 */
public class TestHud extends SingleTextHud {
    public TestHud() {
        super("Test", true);
    }

    @Override
    public String getText(boolean example) {
        return "I'm an example HUD";
    }
}
