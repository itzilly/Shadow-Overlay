package com.itzilly.shadowOverlay;

import java.io.*;
import java.util.Properties;

public class SettingsManager {
    public SettingsManager() {
        File configDir = new File(Constants.CONFIG_DIR_PATH);
        File mainConfig = new File(Constants.CONFIG_FILE_PATH);
        checkConfigDir(configDir);
        checkMainConfig(mainConfig);

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(Constants.CONFIG_FILE_PATH);
            Constants.MAIN_CONFIG = new Properties();
            Constants.MAIN_CONFIG.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String getApiKey() {
        return Constants.MAIN_CONFIG.getProperty("API_KEY");
    }

    public void saveApiKey(String apiKey) {
        Constants.MAIN_CONFIG.setProperty("API_KEY", apiKey);
    }

    public String getLogPath() {
        return Constants.MAIN_CONFIG.getProperty("LOG_PATH");
    }

    public void saveLogPath(String logPath) {
        Constants.MAIN_CONFIG.setProperty("LOG_PATH", logPath);
    }

    private void checkMainConfig(File mainConfig) {
        if (!mainConfig.exists() || !mainConfig.isFile()) {
            generateMainConfig(mainConfig);
        }
    }

    private void generateMainConfig(File mainConfig) {
        try {
            boolean wasCreated = mainConfig.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mainConfig));
            bufferedWriter.write("API_KEY=null");
            bufferedWriter.write("LOG_PATH=null");
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigDir(File configDir) {
        if (!configDir.exists()) {
            createConfigDir();
        }

        if (!configDir.isDirectory()) {
            throw new RuntimeException(new IOException("Unknown error, config file not generated!"));
        }

    }

    private void createConfigDir() {
        boolean wasCreated = new File(Constants.CONFIG_DIR_PATH).mkdir();

    }

}
