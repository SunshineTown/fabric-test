package com.extclp.test;

import com.extclp.test.commands.FlySpeedCommand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.SharedConstants;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestMod implements PreLaunchEntrypoint {

    private static TestConfig config;

    public static TestConfig getConfig() {
        return config;
    }

    @Override
    public void onPreLaunch() {
        SharedConstants.isDevelopment = true;
        try {
            setupConfig();
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                FlySpeedCommand.register(dispatcher);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setupConfig() throws IOException {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("test.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (Files.exists(configFile)) {
            try (Reader reader = Files.newBufferedReader(configFile)) {
                config = gson.fromJson(reader, TestConfig.class);
            }
        } else {
            config = new TestConfig();
        }
        try (Writer writer = Files.newBufferedWriter(configFile)) {
            gson.toJson(config, writer);
        }
    }
}
