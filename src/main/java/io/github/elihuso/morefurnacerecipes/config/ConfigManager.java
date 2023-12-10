package io.github.elihuso.morefurnacerecipes.config;

import io.github.elihuso.morefurnacerecipes.utils.CustomItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final FileConfiguration config;

    public ConfigManager(Plugin plugin) {
        plugin.saveDefaultConfig();

        config = plugin.getConfig();
    }

    public float Experience() {
        return (float) config.getDouble("experience", 6.3F);
    }

    public int Time() {
        return config.getInt("time", 1600);
    }

    public CustomItem[] Custom() {
        // qyl27: Bad type challenge but may work.
        List<CustomItem> customItems = (List<CustomItem>) config.getList("custom", new ArrayList<CustomItem>());
        if (customItems.isEmpty())
            return null;
        return customItems.toArray(CustomItem[]::new);
    }
}
