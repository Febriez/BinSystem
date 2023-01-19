package com.febrie.bin;

import com.febrie.bin.api.data.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BinSystemMain extends JavaPlugin {

    private static BinSystemMain instance;

    public void onEnable() {
        instance = this;
        DataManager.getInstance().load();
    }

    public void onDisable() {
        try {
            DataManager.getInstance().save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BinSystemMain getInstance() {
        return instance;
    }
}
