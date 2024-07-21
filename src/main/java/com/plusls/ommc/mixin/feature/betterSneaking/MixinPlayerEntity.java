package com.plusls.ommc.mixin.feature.betterSneaking;

import com.plusls.ommc.config.Configs;
import com.plusls.ommc.util.CompatGetUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.LivingEntityCompat;

//#if MC > 11404
@Mixin(Player.class)
//#else
//$$ @Mixin(Entity.class)
//#endif
public abstract class MixinPlayerEntity {
    @Unique
    final private static float MAX_STEP_HEIGHT = 1.25f;
    @Unique
    final private static float DEFAULT_STEP_HEIGHT = 114514;
    @Unique
    private float prevStepHeight = DEFAULT_STEP_HEIGHT;

    @Inject(method = "maybeBackOffFromEdge", at = @At(value = "FIELD", target = "Lnet/minecraft/world/phys/Vec3;x:D", opcode = Opcodes.GETFIELD, ordinal = 0))
    private void setStepHeight(Vec3 movement, MoverType type, CallbackInfoReturnable<Vec3> cir) {
        LivingEntityCompat entityCompat = CompatGetUtil.getLivingEntityCompat(this);
        if (!Configs.betterSneaking.getBooleanValue() || !entityCompat.getLevelCompat().get().get().isClientSide()) {
            return;
        }
        prevStepHeight = entityCompat.getMaxUpStep();
        entityCompat.setMaxUpStep(MAX_STEP_HEIGHT);
    }

    @Inject(method = "maybeBackOffFromEdge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", ordinal = 0))
    private void restoreStepHeight(Vec3 movement, MoverType type, CallbackInfoReturnable<Vec3> cir) {
        LivingEntityCompat entityCompat = CompatGetUtil.getLivingEntityCompat(this);
        if (!Configs.betterSneaking.getBooleanValue()
            || !entityCompat.getLevelCompat().get().get().isClientSide()
            || Math.abs(prevStepHeight - DEFAULT_STEP_HEIGHT) <= 0.001
        ) {
            return;
        }
        entityCompat.setMaxUpStep(prevStepHeight);
    }

    @Redirect(
        method = "maybeBackOffFromEdge",
        at = @At(
            value = "INVOKE",
            //#if MC < 12005
            target = "Lnet/minecraft/world/level/Level;noCollision(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Z",
            //#else
            //$$ target = "Lnet/minecraft/world/entity/player/Player;canFallAtLeast(DDF)Z",
            //#endif
            ordinal = -1
        )
    )
    private boolean myIsSpaceEmpty(
        //#if MC < 12005
        Level world, Entity entity, AABB box
        //#else
        //$$ Player entity, double d, double e, float f
        //#endif
    ) {
        //#if MC > 12004
        //$$ Level world = entity.level();
        //$$ AABB box = entity.getBoundingBox().move(d, -entity.maxUpStep(), 0.0);
        //#endif
        //#if MC > 11903
        boolean retOld = world.noCollision(entity, box.move(0, entity.maxUpStep() - prevStepHeight, 0));
        //#else
        //$$ boolean retOld = world.noCollision(entity, box.move(0, entity.maxUpStep - prevStepHeight, 0));
        //#endif
        boolean retNew = world.noCollision(entity, box);
        if (Configs.betterSneaking.getBooleanValue() && world.isClientSide() && (retOld && !retNew) &&
                //#if MC > 11502
                world.getFluidState(entity.blockPosition().below()).getType() instanceof LavaFluid) {
                //#else
                //$$ world.getFluidState(entity.getCommandSenderBlockPosition().below()).getType() instanceof LavaFluid) {
                //#endif
            return true;
        }
        return retNew;
    }

    //#if MC > 11502
    @Inject(method = "isAboveGround", at = @At(value = "HEAD"))
    private void setStepHeight(CallbackInfoReturnable<Boolean> cir) {
        LivingEntityCompat entityCompat = CompatGetUtil.getLivingEntityCompat(this);
        if (!Configs.betterSneaking.getBooleanValue() || !entityCompat.getLevelCompat().get().get().isClientSide()) {
            return;
        }
        prevStepHeight = entityCompat.getMaxUpStep();
        entityCompat.setMaxUpStep(MAX_STEP_HEIGHT);
    }

    @Inject(method = "isAboveGround", at = @At(value = "RETURN"))
    private void restoreStepHeight(CallbackInfoReturnable<Boolean> cir) {
        LivingEntityCompat entityCompat = CompatGetUtil.getLivingEntityCompat(this);
        if (!Configs.betterSneaking.getBooleanValue() || !entityCompat.getLevelCompat().get().get().isClientSide() || Math.abs(prevStepHeight - DEFAULT_STEP_HEIGHT) <= 0.001) {
            return;
        }
        entityCompat.setMaxUpStep(prevStepHeight);
        prevStepHeight = DEFAULT_STEP_HEIGHT;
    }
    //#endif
}
