package com.plusls.ommc;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.MutableComponentCompat;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigManager;
import top.hendrixshen.magiclib.impl.malilib.config.GlobalConfigManager;
import top.hendrixshen.magiclib.impl.malilib.config.MagicConfigHandler;
import top.hendrixshen.magiclib.api.i18n.I18n;
import top.hendrixshen.magiclib.util.VersionUtil;

//#if MC <= 11502
//$$ import net.minecraft.network.chat.BaseComponent;
//#endif

public class OhMyMinecraftClientReference {
    @Getter
    private static final String modIdentifier = "@MOD_IDENTIFIER@";
    @Getter
    private static final String modName = "@MOD_NAME@";
    @Getter
    private static final String modVersion = "@MOD_VERSION@";
    @Getter
    private static final String modVersionType = VersionUtil.getVersionType(OhMyMinecraftClientReference.modVersion);
    @Getter
    private static final Logger logger = LogManager.getLogger(OhMyMinecraftClientReference.modIdentifier);
    @Getter
    private static final MagicConfigManager configManager = GlobalConfigManager
        .getConfigManager(OhMyMinecraftClientReference.getModIdentifier());
    @Getter
    public static MagicConfigHandler configHandler = new MagicConfigHandler(configManager, 1);

    public static String translate(String key, Object... objects) {
        return I18n.tr(OhMyMinecraftClientReference.modIdentifier + "." + key, objects);
    }

    public static @NotNull
    //#if MC > 11502
    MutableComponentCompat
    //#else
    //$$ BaseComponent
    //#endif
    translatable(String key, Object... objects) {
        return ComponentCompat.translatable(OhMyMinecraftClientReference.modIdentifier + "." + key, objects)
            //#if MC <= 11502
            //$$ .get()
            //#endif
        ;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ResourceLocation identifier(String path) {
        //#if MC < 12100
        return new ResourceLocation(OhMyMinecraftClientReference.modIdentifier, path);
        //#else
        //$$ return ResourceLocation.fromNamespaceAndPath(OhMyMinecraftClientReference.modIdentifier, path);
        //#endif
    }
}
