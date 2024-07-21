package com.plusls.ommc.compat.modmenu;

import com.plusls.ommc.OhMyMinecraftClientReference;
import com.plusls.ommc.gui.GuiConfigs;
import top.hendrixshen.magiclib.api.compat.modmenu.ModMenuApiCompat;

public class ModMenuApiImpl implements ModMenuApiCompat {
    @Override
    public ConfigScreenFactoryCompat<?> getConfigScreenFactoryCompat() {
        return (screen) -> {
            GuiConfigs gui = GuiConfigs.getInstance();
            //#if MC > 11903
            gui.setParent(screen);
            //#else
            //$$ gui.setParentGui(screen);
            //#endif
            return gui;
        };
    }

    @Override
    public String getModIdCompat() {
        return OhMyMinecraftClientReference.getModIdentifier();
    }
}
