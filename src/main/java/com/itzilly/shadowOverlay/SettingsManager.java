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
        String key = Constants.MAIN_CONFIG.getProperty("API_KEY");
        if (key.equals("null")) {
            key = null;
        }
        return key;
    }

    public void saveApiKey(String apiKey) {
        Constants.MAIN_CONFIG.setProperty("API_KEY", apiKey);
    }

    public String getLogPath() {
        String logPath = Constants.MAIN_CONFIG.getProperty("LOG_PATH");
        if (logPath.equals("null")) {
            logPath = null;
        }
        return logPath;
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
            bufferedWriter.write("API_KEY=" + null + "\n");
            bufferedWriter.write("LOG_PATH=" + null + "\n");
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
