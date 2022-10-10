package com.itzilly.shadowOverlay;

import org.bspfsystems.yamlconfiguration.configuration.InvalidConfigurationException;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public ConfigManager() {
        File configPath = new File("config/config.properties");
        if (!configPath.exists()) {
            createConfigFolder();
            createConfigFile();
        }

        try {
            Constants.yamlConfiguration.load(configPath);
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

    public void save(String key, Object object) {
        try {
            File configPath = new File("config/config.properties");
            Constants.yamlConfiguration.load(configPath);
            Constants.yamlConfiguration.set(key, object);
            Constants.yamlConfiguration.save(configPath);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createConfigFolder() {
        File dir = new File("config/");
        boolean wasCreated = dir.mkdir();
    }

    private static void createConfigFile() {
        File path = new File("config/config.properties");
        Constants.yamlConfiguration.set("API_KEY", "");
        Constants.yamlConfiguration.set("log_path", "");
        try {
            Constants.yamlConfiguration.save(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
