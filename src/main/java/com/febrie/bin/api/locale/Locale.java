package com.febrie.bin.api.locale;

import com.febrie.bin.BinSystemMain;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Locale {

    private static Locale instance;
    private final YamlConfiguration config;

    private Locale() {
        instance = this;
        File file = new File(BinSystemMain.getInstance().getDataFolder() + "/locale/Korean.yml");
        if (!file.exists())
            createFile();
        config = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessageList(String path) {
        StringBuilder sb = new StringBuilder();
        for (String s : getConfig().getStringList(path))
            sb.append(getAltM(s)).append('\n');
        return sb.substring(0, sb.length() - 1);
    }

    public String getMessage(String path) {
        return getAltM(getConfig().getString(path));
    }

    private String getAltM(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void createFile() {
        BinSystemMain.getInstance().saveResource("locale/Korean.yml", false);
    }

    public static Locale getInstance() {
        if (instance == null)
            new Locale();
        return instance;
    }
}
