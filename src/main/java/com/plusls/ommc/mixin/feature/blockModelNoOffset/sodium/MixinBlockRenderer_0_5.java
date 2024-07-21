package com.plusls.ommc.mixin.feature.blockModelNoOffset.sodium;

import com.plusls.ommc.feature.blockModelNoOffset.BlockModelNoOffsetUtil;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;

@Dependencies(require = @Dependency(value = "sodium", versionPredicates = "~0.5"))
@Mixin(value = BlockRenderer.class, remap = false)
public class MixinBlockRenderer_0_5 {
    @Dynamic
    @Redirect(
            method = "renderModel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;hasOffsetFunction()Z",
                    ordinal = 0, remap = true
            )
    )
    private boolean blockModelNoOffset(BlockState blockState) {
        return !BlockModelNoOffsetUtil.shouldNoOffset(blockState);
    }
}
