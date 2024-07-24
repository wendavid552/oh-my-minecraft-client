package com.plusls.ommc.mixin.feature.highlightWaypoint;

import com.plusls.ommc.config.Configs;
import com.plusls.ommc.impl.generic.highlightWaypoint.HighlightWaypointHandler;
import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC > 11802
import net.minecraft.client.GuiMessageTag;
//#endif

//#if MC >=12005
//$$ import net.minecraft.client.GuiMessage;
//#endif

@Mixin(value = ChatComponent.class, priority = 999)
public class MixinChatHud {
    @Inject(
            //#if MC > 11802
            method = "logChatMessage",
            //#else
            //$$ method = "addMessage(Lnet/minecraft/network/chat/Component;I)V",
            //#endif
            at = @At(
                    value = "HEAD"
            )
    )
    public void modifyMessage(
            //#if MC >= 12005
            //$$ GuiMessage message,
            //#else
            Component message,
            //#endif
            //#if MC > 11802 && MC < 12005
            GuiMessageTag guiMessageTag,
            //#elseif MC <=11802
            //$$ int messageId,
            //#endif
            CallbackInfo ci
    ) {
        if (Configs.parseWaypointFromChat.getBooleanValue()) {
            HighlightWaypointHandler.getInstance().parseMessage(ComponentCompat.of(
                    //#if MC > 12004
                    //$$ message.content()
                    //#else
                    message
                    //#endif
            ));
        }
    }
}
