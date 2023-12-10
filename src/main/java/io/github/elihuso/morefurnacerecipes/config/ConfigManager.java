package io.github.elihuso.morefurnacerecipes.config;

import io.github.elihuso.morefurnacerecipes.utils.CustomItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<Map<?, ?>> maps = config.getMapList("custom");
        ArrayList<CustomItem> customItems = new ArrayList<>();
        if ((maps == null) || (maps.isEmpty()))
            return null;
        for (var v : maps) {
            if (!(v.containsKey("from") && v.containsKey("to")))
                return null;
            if (!((v.get("from") instanceof String) && (v.get("to") instanceof String)))
                return null;
            String from = (String) v.get("from");
            String to = (String) v.get("to");
            if ((v.containsKey("experience") && v.containsKey("time"))) {
                double experience = (double) v.get("experience");
                int time = (int) v.get("time");
                customItems.add(new CustomItem(from, to, experience, time));
            }
            else {
                customItems.add(new CustomItem(from, to));
            }
        }
        return customItems.toArray(new CustomItem[customItems.size()]);
    }
}
