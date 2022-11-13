package me.kikugie.tmcutils.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import me.kikugie.tmcutils.TMCUtilsMod;

import java.util.ArrayList;
import java.util.List;

public class Configs {
    public static FeatureConfigs FEATURE_CONFIGS = new FeatureConfigs();

    public static class BaseConfigs {
        public final ImmutableList<IConfigValue> OPTIONS;

        public BaseConfigs(ImmutableList<IConfigValue> options) {
            this.OPTIONS = options;
        }

        public ImmutableList<IConfigValue> get() {
            return OPTIONS;
        }

        public ImmutableList<IHotkey> getHotkeys() {
            List<IHotkey> list = new ArrayList<>();
            for (IConfigValue configValue : this.OPTIONS) {
                if (configValue instanceof IHotkey) {
                    list.add(((IHotkey) configValue));
                }
            }
            return ImmutableList.copyOf(list);
        }

        public ImmutableList<IKeybind> getKeybinds() {
            List<IKeybind> list = new ArrayList<>();
            for (IConfigValue configValue : this.OPTIONS) {
                if (configValue instanceof IHotkey) {
                    list.add(((IHotkey) configValue).getKeybind());
                }
            }
            return ImmutableList.copyOf(list);
        }
    }

    public static class FeatureConfigs extends BaseConfigs {
        public static final ConfigHotkey ISORENDER_SELECTION = new ConfigHotkey("isorenderSelection", "I", "Render current Litematica selection");
        public static final ConfigBooleanHotkeyed AUTO_WE_SYNC = new ConfigBooleanHotkeyed("autoWeSync", false, "", "Synchronises WorldEdit selection n ticks after configured value");
        public static final ConfigInteger AUTO_WE_SYNC_TICKS = new ConfigInteger("autoWeSyncTicks", 20, 1, 1000, false, "Ticks to wait before synchronising WorldEdit selection");

        public FeatureConfigs() {
            super(getOptions());
        }

        private static ImmutableList<IConfigValue> getOptions() {
            // TODO: Somehow disable options instead of removing them
            List<IConfigValue> listInProcess = new ArrayList<>(List.of(AUTO_WE_SYNC, AUTO_WE_SYNC_TICKS));
            if (TMCUtilsMod.isIsoRenderInstalled()) {
                listInProcess.add(ISORENDER_SELECTION);
            }

            return ImmutableList.copyOf(listInProcess);
        }
    }


}