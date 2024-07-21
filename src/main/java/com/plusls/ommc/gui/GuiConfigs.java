package com.plusls.ommc.gui;

import com.plusls.ommc.OhMyMinecraftClientReference;
import lombok.Getter;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigManager;
import top.hendrixshen.magiclib.impl.malilib.config.gui.MagicConfigGui;

public class GuiConfigs extends MagicConfigGui {
    @Getter(lazy = true)
    private static final GuiConfigs instance = new GuiConfigs(OhMyMinecraftClientReference.getModIdentifier(), OhMyMinecraftClientReference.configHandler.getConfigManager());

    private GuiConfigs(String identifier, MagicConfigManager configManager) {
        super(identifier, configManager, () -> OhMyMinecraftClientReference.translate("gui.title.configs", OhMyMinecraftClientReference.getModVersion()));
    }
}
