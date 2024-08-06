package com.plusls.ommc;

import com.plusls.ommc.impl.feature.highlightLavaSource.LavaSourceResourceLoader;
import com.plusls.ommc.impl.feature.preventWastageOfWater.PreventWastageOfWaterHelper;
import com.plusls.ommc.impl.feature.realSneaking.RealSneakingEventHelper;
import com.plusls.ommc.game.Configs;
import com.plusls.ommc.impl.generic.highlightWaypoint.HighlightWaypointHandler;
import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;

public class OhMyMinecraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Configs.init();
        LavaSourceResourceLoader.init();
        HighlightWaypointHandler.init();
        RealSneakingEventHelper.init();
        PreventWastageOfWaterHelper.init();
    }
}

