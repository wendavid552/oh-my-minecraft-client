package com.plusls.ommc;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.api.compat.minecraft.resources.ResourceLocationCompat;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigManager;
import top.hendrixshen.magiclib.impl.malilib.config.GlobalConfigManager;
import top.hendrixshen.magiclib.impl.malilib.config.MagicConfigHandler;
import top.hendrixshen.magiclib.api.i18n.I18n;
import top.hendrixshen.magiclib.util.VersionUtil;

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
        return I18n.tr(OhMyMinecraftClientReference.getModIdentifier() + "." + key, objects);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ResourceLocation identifier(String path) {
        return ResourceLocationCompat.fromNamespaceAndPath(OhMyMinecraftClientReference.getModIdentifier(), path);
    }
}
