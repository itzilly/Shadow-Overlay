package com.itzilly.shadowOverlay;

import org.bspfsystems.yamlconfiguration.configuration.InvalidConfigurationException;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public ConfigManager() {
        File configPath = new File("config/config.properties");
        YamlConfiguration config = new YamlConfiguration();
        if (!configPath.exists()) {
            createConfigFolder();
            createConfigFile(config);
        }

        try {
            config.load(configPath);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    private void loadConfig() {
//        if (!configPath.exists()) {
//            createConfigFolder();
//            createConfigFile(config);
//        }
//
//        try {
//            config.load(configPath);
//        } catch (IOException | InvalidConfigurationException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

//    public void save(String key, Object object) {
//        config.set(key, object);
//    }

    private static void createConfigFolder() {
        File dir = new File("config/");
        boolean wasCreated = dir.mkdir();
    }

    private static void createConfigFile(YamlConfiguration yamlConfiguration) {
        File path = new File("config/config.properties");
        yamlConfiguration.set("API_KEY", "thing1");
        yamlConfiguration.set("log_path", "thing2");
        try {
            yamlConfiguration.save(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
