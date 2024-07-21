package com.plusls.ommc.mixin.feature.highlightWaypoint;


import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12005
//$$ import com.mojang.math.Axis;
//#endif

//#if MC >= 12100
//$$ import net.minecraft.client.DeltaTracker;
//#endif

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    @Inject(method = "renderLevel", at = @At("RETURN"))
    private void postRender(
        //#if MC < 12005
        PoseStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci
        //#elseif MC >= 12005 && MC < 12100
        //$$ float tickDelta, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci
        //#else
        //$$ DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci
        //#endif
    ) {
        //#if MC >= 12005
            //#if MC >= 12100
            //$$ float tickDelta = deltaTracker.getGameTimeDeltaPartialTick(false);
            //#endif
        //$$ PoseStack matrices = new PoseStack();
        //$$ matrices.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        //$$ matrices.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        //#endif
        HighlightWaypointUtil.drawWaypoint(matrices, tickDelta);
    }
}
