package io.github.elihuso.morefurnacerecipes.utils;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CustomItem implements ConfigurationSerializable {
    private final String from;
    private final String to;
    private final double experience;
    private final int time;
    private final boolean allowBlast;

    public CustomItem(String from, String to, double experience, int time, boolean allowBlast) {
        this.from = from;
        this.to = to;
        this.experience = experience;
        this.time = time;
        this.allowBlast = allowBlast;
    }

    public CustomItem(String from, String to) {
        this.from = from;
        this.to = to;
        this.experience = 0.7;
        this.time = 200;
        this.allowBlast = false;
    }

    public CustomItem(Map<String, Object> config) {
        if (!config.containsKey("from") || !config.containsKey("to")) {
            throw new RuntimeException("Config entry 'from' or 'to' is missing.");
        }

        var objFrom = config.get("from");
        var objTo = config.get("to");
        if (!(objFrom instanceof String) || !(objTo instanceof String)) {
            throw new RuntimeException("Config entry 'from' or 'to' is not a string.");
        }

        from = (String) objFrom;
        to = (String) objTo;

        var valueExperience = 0.7;
        if (config.containsKey("experience")) {
            var objExperience = config.get("experience");
            if (objExperience instanceof Double) {
                valueExperience = (Double) objExperience;
            }
        }

        var valueTime = 200;
        if (config.containsKey("time")) {
            var objTime = config.get("time");
            if (objTime instanceof Integer) {
                valueTime = (Integer) objTime;
            }
        }

        boolean valueAllowBlast = false;
        if (config.containsKey("allowBlast")) {
            var objAllowBlast = config.get("allowBlast");
            if (objAllowBlast instanceof Boolean) {
                valueAllowBlast = (Boolean) objAllowBlast;
            }
        }

        experience = valueExperience;
        time = valueTime;
        allowBlast = valueAllowBlast;
    }

    @Override
    public Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();
        map.put("from", from);
        map.put("to", to);
        map.put("experience", experience);
        map.put("time", time);
        map.put("allowBlast", allowBlast);
        return map;
    }

    public String getFromString() {
        return from;
    }

    public String getToString() {
        return to;
    }

    public double getExperience() {
        return experience;
    }

    public int getTime() {
        return time;
    }

    public boolean isAllowBlast() {
        return allowBlast;
    }

    public Material getFromMaterial() {
        return Material.getMaterial(from);
    }

    public Material getToMaterial() {
        return Material.getMaterial(to);
    }
}
