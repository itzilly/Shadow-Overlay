package com.itzilly.shadowOverlay;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class YmlConfig {
    public YmlConfig() {
        saveDefaults();
    }

    private void saveDefaults() {
        genConfigFolder();
        try {
            _saveDefaults(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        load();

    }

    private void genConfigFolder() {
        File configFolder = new File("config/");
        if (!configFolder.isDirectory()) {
            boolean wasCreated = configFolder.mkdir();
        }
    }

    private void _saveDefaults(boolean overwrite) throws FileNotFoundException {
        File file = new File("config/config.properties");
        if (!file.exists() || overwrite) {
            System.out.println("Regenerating config");
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("API_KEY", "null");
            data.put("LOG_PATH", "null");
            Yaml yaml = new Yaml();
            PrintWriter printWriter = new PrintWriter("config/config.properties");;
            yaml.dump(data, printWriter);
        }
    }

    public void set(String key, String value) {
        try {
            _set(key, value);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void _set(String key, String value) throws FileNotFoundException {
        if (value == null){
            value = "null";
        }

        Map<String, Object> data = _readMap();
        if (data.containsKey(key)) {
            data.replace(key, value);
        } else {
            data.put(key, value);
        }
        new Yaml().dump(data, new PrintWriter("config/config.properties"));
    }

    public void load() {
        try {
            _load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> _readMap() {
        Map<String, Object> map;
        try {
            map = _load();
            System.out.println("Map: " + map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return map;
    }

    private Map<String, Object> _load() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("config/config.properties");
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(inputStream);
        if (map == null) {
            _saveDefaults(true);
            return _load();
        }
        return yaml.load(inputStream);
    }

    public String getString(String key) {
        return _readMap().get(key).toString();
    }

    public Object get(String key) {
        return _readMap().get(key);
    }
}