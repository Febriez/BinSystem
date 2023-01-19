package com.febrie.bin.api.data;

import com.febrie.bin.BinSystemMain;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class DataManager {

    private static DataManager instance;

    private final YamlConfiguration config;
    private final Map<UUID, JsonObject> map;
    private final File file = new File(BinSystemMain.getInstance().getDataFolder() + "/data.yml");

    private DataManager() {
        instance = this;
        map = new HashMap<>();
        if (!file.exists()) file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public JsonObject getData(UUID uuid) {
        if (!map.containsKey(uuid)) map.put(uuid, new JsonObject());
        return map.get(uuid);
    }

    public void setData(UUID uuid, JsonObject data) {
        map.put(uuid, data);
    }

    public void load() {
        for (String key : config.getKeys(false))
            map.put(UUID.fromString(key), JsonParser.parseString(Objects.requireNonNull(config.getString(key))).getAsJsonObject());
    }

    public void save() throws IOException {
        for (UUID uuid : map.keySet())
            config.set(uuid.toString(), new GsonBuilder()
                    .setPrettyPrinting().create().toJson(map.get(uuid)));
        config.save(file);
    }

    public static DataManager getInstance() {
        if (instance == null) new DataManager();
        return instance;
    }
}
