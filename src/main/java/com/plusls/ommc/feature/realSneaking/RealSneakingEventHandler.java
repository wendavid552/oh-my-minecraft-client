package com.plusls.ommc.feature.realSneaking;

import com.plusls.ommc.config.Configs;
import com.plusls.ommc.util.CompatGetUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.EntityCompat;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.LivingEntityCompat;

public class RealSneakingEventHandler {
    final private static float MIN_STEP_HEIGHT = 0.001f;
    private static float prevStepHeight;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(RealSneakingEventHandler::preClientTick);
    }

    private static void preClientTick(Minecraft minecraftClient) {
        if (minecraftClient.player != null) {
            LivingEntityCompat entityCompat = CompatGetUtil.getLivingEntityCompat(minecraftClient.player);
            if (EntityCompat.of(minecraftClient.player).getMaxUpStep() - MIN_STEP_HEIGHT >= 0.00001) {
                prevStepHeight = entityCompat.getMaxUpStep();
            }
            if (Configs.realSneaking.getBooleanValue() && minecraftClient.player.isShiftKeyDown()) {
                 entityCompat.setMaxUpStep(MIN_STEP_HEIGHT);
            } else {
                 entityCompat.setMaxUpStep(prevStepHeight);
            }
        }
    }
}
