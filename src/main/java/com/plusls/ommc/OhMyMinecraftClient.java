package com.plusls.ommc;

import com.plusls.ommc.config.Configs;
import com.plusls.ommc.feature.highlightLavaSource.LavaSourceResourceLoader;
import com.plusls.ommc.feature.preventWastageOfWater.PreventWastageOfWaterHandler;
import com.plusls.ommc.feature.realSneaking.RealSneakingEventHandler;
import com.plusls.ommc.impl.generic.highlightWaypoint.HighlightWaypointHandler;
import fi.dy.masa.malilib.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;

public class OhMyMinecraftClient implements ClientModInitializer {
    @Dependencies(require = {
            //#if MC > 11701
            @Dependency(value = "canvas", versionPredicates = ">=1.0.2308", optional = true),
            //#if MC > 11903
            @Dependency(value = "fabric", versionPredicates = ">=0.84.0", optional = true),
            //#else
            //$$ @Dependency(value = "fabric", versionPredicates = ">=0.75.0", optional = true),
            //#endif
            @Dependency(value = "frex", versionPredicates = ">=6.0.242", optional = true),
            @Dependency(value = "sodium", versionPredicates = ">=0.4.1", optional = true),
            //#elseif MC > 11605
            //$$ @Dependency(value = "sodium", versionPredicates = ">=0.3.4", optional = true),
            //#elseif MC > 11502
            //$$ @Dependency(value = "sodium", versionPredicates = ">=0.2.0", optional = true),
            //#endif
    })
    @Override
    public void onInitializeClient() {
        LavaSourceResourceLoader.init();
        HighlightWaypointHandler.init();
        OhMyMinecraftClientReference.getConfigManager()
            .parseConfigClass(Configs.class);
        OhMyMinecraftClientReference.configHandler.setPostDeserializeCallback(Configs::postDeserialize);
        ConfigManager.getInstance().registerConfigHandler(
            OhMyMinecraftClientReference.configHandler.getIdentifier(),
            OhMyMinecraftClientReference.configHandler
        );
        Configs.init();
        RealSneakingEventHandler.init();
        PreventWastageOfWaterHandler.init();
    }
}

