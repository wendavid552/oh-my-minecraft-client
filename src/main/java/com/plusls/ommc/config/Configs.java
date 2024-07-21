package com.plusls.ommc.config;

import com.google.common.collect.ImmutableList;
import com.plusls.ommc.OhMyMinecraftClientReference;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.ommc.feature.sortInventory.SortInventoryUtil;
import com.plusls.ommc.gui.GuiConfigs;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.api.malilib.annotation.Statistic;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigManager;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.magiclib.api.malilib.annotation.Config;
import top.hendrixshen.magiclib.impl.malilib.config.MagicConfigFactory;
import top.hendrixshen.magiclib.impl.malilib.config.MagicConfigHandler;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigBoolean;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigHotkey;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigInteger;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigOptionList;
import top.hendrixshen.magiclib.game.malilib.ConfigGui;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigStringList;
import top.hendrixshen.magiclib.util.minecraft.InfoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Configs {
    private static final MagicConfigManager cm = OhMyMinecraftClientReference.getConfigManager();
    private static final MagicConfigFactory cf = Configs.cm.getConfigFactory();

    private static final List<String> OLD_WORLD_EATER_MINE_HELPER_WHITELIST = new ArrayList<>();
    private static final List<String> OLD_BLOCK_MODEL_NO_OFFSET_BLACKLIST = new ArrayList<>();
    private static final List<String> OLD_BLOCK_MODEL_NO_OFFSET_WHITELIST = new ArrayList<>();

    public static void updateOldStringList() {
        OLD_BLOCK_MODEL_NO_OFFSET_BLACKLIST.clear();
        OLD_BLOCK_MODEL_NO_OFFSET_BLACKLIST.addAll(blockModelNoOffsetBlacklist.getStrings());
        OLD_BLOCK_MODEL_NO_OFFSET_WHITELIST.clear();
        OLD_BLOCK_MODEL_NO_OFFSET_WHITELIST.addAll(blockModelNoOffsetWhitelist.getStrings());
        OLD_WORLD_EATER_MINE_HELPER_WHITELIST.clear();
        OLD_WORLD_EATER_MINE_HELPER_WHITELIST.addAll(worldEaterMineHelperWhitelist.getStrings());
    }

    public static void checkIsStringListChanged() {
        boolean dirty = false;
        if (!OLD_WORLD_EATER_MINE_HELPER_WHITELIST.equals(worldEaterMineHelperWhitelist) ||
                !OLD_BLOCK_MODEL_NO_OFFSET_BLACKLIST.equals(blockModelNoOffsetBlacklist) ||
                !OLD_BLOCK_MODEL_NO_OFFSET_WHITELIST.equals(blockModelNoOffsetWhitelist)) {
            Minecraft.getInstance().levelRenderer.allChanged();
            dirty = true;
        }


        if (dirty) {
            updateOldStringList();
        }
    }

    // GENERIC
    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigHotkey clearWaypoint = cf.newConfigHotkey("clearWaypoint", "C");

    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigBoolean debug = cf.newConfigBoolean("debug", false);

    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigBoolean dontClearChatHistory = cf.newConfigBoolean("dontClearChatHistory", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigBoolean forceParseWaypointFromChat =
        cf.newConfigBoolean("forceParseWaypointFromChat", false);

    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigInteger highlightBeamTime =
        cf.newConfigInteger("highlightBeamTime", 10, 0, Integer.MAX_VALUE);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigHotkey openConfigGui = cf.newConfigHotkey("openConfigGui", "O,C");

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigBoolean parseWaypointFromChat = cf.newConfigBoolean("parseWaypointFromChat", true);

    // hotkey = "O,P"
    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigHotkey sendLookingAtBlockPos = cf.newConfigHotkey("sendLookingAtBlockPos", "O,P");

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigBoolean sortInventorySupportEmptyShulkerBoxStack =
        cf.newConfigBoolean("sortInventorySupportEmptyShulkerBoxStack", true);

    // hotkey = "R"
    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigHotkey sortInventory = cf.newConfigHotkey("sortInventory", "R");

    @Config(category = ConfigCategory.GENERIC)
    public static MagicConfigOptionList sortInventoryShulkerBoxLast =
        cf.newConfigOptionList("sortInventoryShulkerBoxLast", SortInventoryShulkerBoxLastType.AUTO);

    // FEATURE_TOGGLE
    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean autoSwitchElytra = cf.newConfigBoolean("autoSwitchElytra", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean betterSneaking = cf.newConfigBoolean("betterSneaking", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">1.15.2"))
    public static MagicConfigBoolean disableBlocklistCheck = cf.newConfigBoolean("disableBlocklistCheck", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean disableBreakBlock = cf.newConfigBoolean("disableBreakBlock", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean disableBreakScaffolding = cf.newConfigBoolean("disableBreakScaffolding", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean disableMoveDownInScaffolding =
        cf.newConfigBoolean("disableMoveDownInScaffolding", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean disablePistonPushEntity = cf.newConfigBoolean("disablePistonPushEntity", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean flatDigger = cf.newConfigBoolean("flatDigger", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean forceBreakingCooldown = cf.newConfigBoolean("forceBreakingCooldown", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean highlightLavaSource = cf.newConfigBoolean("highlightLavaSource", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean highlightPersistentMob = cf.newConfigBoolean("highlightPersistentMob", false);

    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean highlightPersistentMobClientMode = cf.newConfigBoolean("highlightPersistentMobClientMode", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean preventWastageOfWater = cf.newConfigBoolean("preventWastageOfWater", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean preventIntentionalGameDesign = cf.newConfigBoolean("preventIntentionalGameDesign", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean realSneaking = cf.newConfigBoolean("realSneaking", false);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean removeBreakingCooldown = cf.newConfigBoolean("removeBreakingCooldown", false);


    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.FEATURE_TOGGLE)
    public static MagicConfigBoolean worldEaterMineHelper = cf.newConfigBoolean("worldEaterMineHelper", false);


    // LISTS
    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList blockModelNoOffsetBlacklist =
        cf.newConfigStringList("blockModelNoOffsetBlacklist");

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigOptionList blockModelNoOffsetListType =
        cf.newConfigOptionList("blockModelNoOffsetListType", UsageRestriction.ListType.WHITELIST);

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList blockModelNoOffsetWhitelist = 
        cf.newConfigStringList("blockModelNoOffsetWhitelist",
            ImmutableList.of("minecraft:wither_rose", "minecraft:poppy", "minecraft:dandelion"));

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList breakBlockBlackList =
        cf.newConfigStringList("breakBlockBlackList", ImmutableList.of("minecraft:budding_amethyst", "_bud"));

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList breakScaffoldingWhiteList =
        cf.newConfigStringList("breakScaffoldingWhiteList", ImmutableList.of("minecraft:air", "minecraft:scaffolding"));

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList highlightEntityBlackList = cf.newConfigStringList("highlightEntityBlackList");

    @Config(category = ConfigCategory.LISTS)
    public static IConfigOptionListEntry highlightEntityListType = UsageRestriction.ListType.WHITELIST;

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList highlightEntityWhiteList =
        cf.newConfigStringList("highlightEntityWhiteList", ImmutableList.of("minecraft:wandering_trader"));

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList moveDownInScaffoldingWhiteList =
        cf.newConfigStringList("moveDownInScaffoldingWhiteList",
            ImmutableList.of("minecraft:air", "minecraft:scaffolding"));

    @Config(category = ConfigCategory.LISTS)
    public static MagicConfigStringList worldEaterMineHelperWhitelist =
        cf.newConfigStringList("worldEaterMineHelperWhitelist",
            ImmutableList.of("_ore", "minecraft:ancient_debris", "minecraft:obsidian"));

    // ADVANCED_INTEGRATED_SERVER

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.ADVANCED_INTEGRATED_SERVER)
    public static MagicConfigBoolean onlineMode = cf.newConfigBoolean("onlineMode", true);;

    @Config(category = ConfigCategory.ADVANCED_INTEGRATED_SERVER)
    public static MagicConfigBoolean pvp = cf.newConfigBoolean("pvp", false);

    @Config(category = ConfigCategory.ADVANCED_INTEGRATED_SERVER)
    public static MagicConfigBoolean flight = cf.newConfigBoolean("flight", false);

    @Config(category = ConfigCategory.ADVANCED_INTEGRATED_SERVER)
    @Dependencies(conflict = @Dependency(value = "minecraft", versionPredicates = "<1.19.3"))
    public static MagicConfigInteger port = cf.newConfigInteger("port", 0, 0, 65535);

    private static boolean first = true;

    public static void postDeserialize(MagicConfigHandler configHandler) {
        if (Configs.first) {
            if (Configs.debug.getBooleanValue()) {
                Configurator.setLevel(OhMyMinecraftClientReference.getModIdentifier(), Level.DEBUG);
            }
            updateOldStringList();
            Configs.first = false;
        }
        checkIsStringListChanged();
    }


    public static void init() {
        debug.setValueChangeCallback((newValue) -> {
            Configurator.setLevel(
                OhMyMinecraftClientReference.getModIdentifier(),
                Configs.debug.getBooleanValue() ? Level.DEBUG : Level.INFO);
            ConfigGui.getCurrentInstance()
                .ifPresent(ConfigGui::reDraw);
        });

        clearWaypoint.getKeybind().setCallback((keyAction, iKeybind) -> {
            HighlightWaypointUtil.clearHighlightPos();
            return false;
        });

        MagicConfigManager.setHotkeyCallback(Configs.openConfigGui, () -> {
            GuiConfigs screen = GuiConfigs.getInstance();
            //#if MC > 11903
            screen.setParent(Minecraft.getInstance().screen);
            //#else
            //$$ screen.setParentGui(Minecraft.getInstance().screen);
            //#endif
            Minecraft.getInstance().setScreen(screen);
        }, true);

        MagicConfigManager.setHotkeyCallback(Configs.sendLookingAtBlockPos, () -> {
            Minecraft client = Minecraft.getInstance();
            Entity cameraEntity = client.getCameraEntity();
            MultiPlayerGameMode clientPlayerInteractionManager = client.gameMode;
            if (cameraEntity != null && clientPlayerInteractionManager != null) {
                //#if MC < 12005
                HitResult hitresult = cameraEntity.pick(clientPlayerInteractionManager.getPickRange(), client.getFrameTime(), false);
                //#elseif MC >= 12005 && MC <12007
                //$$ HitResult hitresult = cameraEntity.pick(clientPlayerInteractionManager.hasInfiniteItems() ? 5.0F : 4.5F, client.getFrameTime(), false);
                //#elseif MC >= 12100
                //$$ HitResult hitresult = cameraEntity.pick(clientPlayerInteractionManager.hasInfiniteItems() ? 5.0F : 4.5F, client.getFrameTimeNs(), false);
                //#endif
                if (hitresult.getType() == HitResult.Type.BLOCK) {
                    BlockPos lookPos = ((BlockHitResult) hitresult).getBlockPos();
                    if (client.player != null) {
                        String message = String.format("[%d, %d, %d]", lookPos.getX(), lookPos.getY(), lookPos.getZ());
                        InfoUtil.sendChat(message);
                    }
                }
            }
        }, false);

        sortInventory.getKeybind().setSettings(KeybindSettings.GUI);

        MagicConfigManager.setHotkeyCallback(Configs.sortInventory,
            () -> Optional.ofNullable(SortInventoryUtil.sort()).ifPresent(Runnable::run), false);

        // FEATURE_TOGGLE
        cf.newConfigBoolean("highlightLavaSource", false).setValueChangeCallback(option -> {
            OhMyMinecraftClientReference.getLogger().debug("set highlightLavaSource {}", option.getBooleanValue());
            Minecraft.getInstance().levelRenderer.allChanged();
        });

        cf.newConfigBoolean("worldEaterMineHelper", false).setValueChangeCallback(option -> {
            OhMyMinecraftClientReference.getLogger().debug("set worldEaterMineHelper {}", option.getBooleanValue());
            Minecraft.getInstance().levelRenderer.allChanged();
        });

        // LISTS
        blockModelNoOffsetListType.setValueChangeCallback(option -> Minecraft.getInstance().levelRenderer.allChanged());

        // ADVANCED_INTEGRATED_SERVER
        onlineMode.setValueChangeCallback(option -> {
            OhMyMinecraftClientReference.getLogger().debug("set onlineMode {}", option.getBooleanValue());
            if (Minecraft.getInstance().hasSingleplayerServer()) {
                Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer())
                    .setUsesAuthentication(onlineMode.getBooleanValue());
            }
        });

        pvp.setValueChangeCallback(option -> {
            OhMyMinecraftClientReference.getLogger().debug("set pvp {}", option.getBooleanValue());
            if (Minecraft.getInstance().hasSingleplayerServer()) {
                Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer())
                    .setPvpAllowed(pvp.getBooleanValue());
            }
        });

        flight.setValueChangeCallback(option -> {
            OhMyMinecraftClientReference.getLogger().debug("set flight {}", option.getBooleanValue());
            if (Minecraft.getInstance().hasSingleplayerServer()) {
                Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer())
                    .setFlightAllowed(flight.getBooleanValue());
            }
        });
    }

    public enum SortInventoryShulkerBoxLastType implements IConfigOptionListEntry {
        FALSE("false", OhMyMinecraftClientReference.getModIdentifier() + ".gui.label.sort_inventory_shulker_box_last_type.false"),
        TRUE("true", OhMyMinecraftClientReference.getModIdentifier() + ".gui.label.sort_inventory_shulker_box_last_type.true"),
        AUTO("auto", OhMyMinecraftClientReference.getModIdentifier() + ".gui.label.sort_inventory_shulker_box_last_type.auto");
        private final String configString;
        private final String translationKey;

        SortInventoryShulkerBoxLastType(String configString, String translationKey) {
            this.configString = configString;
            this.translationKey = translationKey;
        }

        @Override
        public String getStringValue() {
            return this.configString;
        }

        @Override
        public @NotNull String getDisplayName() {
            return I18n.get(this.translationKey);
        }

        @Override
        public IConfigOptionListEntry cycle(boolean forward) {
            int id = this.ordinal();
            if (forward) {
                ++id;
                if (id >= values().length) {
                    id = 0;
                }
            } else {
                --id;
                if (id < 0) {
                    id = values().length - 1;
                }
            }

            return values()[id % values().length];
        }

        @Override
        public IConfigOptionListEntry fromString(String name) {
            SortInventoryShulkerBoxLastType[] values = values();
            for (SortInventoryShulkerBoxLastType mode : values) {
                if (mode.configString.equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            return AUTO;
        }
    }

    public static class ConfigCategory {
        public static final String GENERIC = "generic";
        public static final String FEATURE_TOGGLE = "feature_toggle";
        public static final String LISTS = "lists";
        public static final String ADVANCED_INTEGRATED_SERVER = "advanced_integrated_server";
    }
}