package me.kikugie.tmcutils;

import fi.dy.masa.malilib.event.InitializationHandler;
import me.kikugie.tmcutils.command.IsorenderSelectionCommand;
import me.kikugie.tmcutils.command.WorldEditSyncCommand;
import me.kikugie.tmcutils.features.WorldEditSync;
import me.kikugie.tmcutils.util.ResponseMuffler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TMCUtilsMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("tmc-utils");
    public static final String MOD_ID = "tmc-utils";
    public static final String MOD_VERSION = "0.1.0";
    public static final String MOD_NAME = "TMC Utils";

    public static boolean isIsoRenderInstalled() {
        return FabricLoader.getInstance().isModLoaded("isometric-renders");
    }

    @Override
    public void onInitialize() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        ClientCommandRegistrationCallback.EVENT.register(IsorenderSelectionCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(WorldEditSyncCommand::register);

        ClientPlayConnectionEvents.JOIN.register(WorldEditSync::onJoinGame);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ResponseMuffler.clear());
    }
}