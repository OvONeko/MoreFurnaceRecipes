package io.github.elihuso.morefurnacerecipes.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItem {
    private final String from;
    private final String to;
    private final double experience;
    private final int time;

    public CustomItem(String from, String to, double experience, int time) {
        this.from = from;
        this.to = to;
        this.experience = experience;
        this.time = time;
    }

    public CustomItem(String from, String to) {
        this.from = from;
        this.to = to;
        this.experience = 0.7;
        this.time = 200;
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

    public Material getFromMaterial() {
        return Material.getMaterial(from);
    }

    public Material getToMaterial() {
        return Material.getMaterial(to);
    }

    public ItemStack getFromItem() {
        return new ItemStack(getFromMaterial());
    }

    public ItemStack getToItem() {
        return new ItemStack(getToMaterial());
    }
}
