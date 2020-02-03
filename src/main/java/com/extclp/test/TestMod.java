package com.extclp.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TestMod implements ModInitializer {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static TestConfig config;

    public static TestConfig getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        setupConfig();
    }

    public static void setupConfig() {
        File configFile = new File("config/test.json");
        try {
            if(configFile.exists()){
                try (FileReader reader = new FileReader(configFile)){
                    config = GSON.fromJson(reader, TestConfig.class);
                }
            }else {
                configFile.getParentFile().mkdir();
                config = new TestConfig();
            }
            try (FileWriter writer = new FileWriter(configFile)){
                GSON.toJson(config, config.getClass(), writer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
