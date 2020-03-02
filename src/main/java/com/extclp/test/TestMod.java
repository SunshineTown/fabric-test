package com.extclp.test;

import com.extclp.test.commands.FlySpeedCommand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.SharedConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
            CommandRegistry.INSTANCE.register(false, FlySpeedCommand::register);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setupConfig() throws IOException {
        File configFile = new File("config/test.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (configFile.exists()) {
            try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                config = gson.fromJson(reader, TestConfig.class);
            }
        } else {
            configFile.getParentFile().mkdir();
            config = new TestConfig();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
            gson.toJson(config, config.getClass(), writer);
        }
    }
}
