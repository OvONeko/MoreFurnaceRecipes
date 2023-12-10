package io.github.elihuso.morefurnacerecipes;

import io.github.elihuso.morefurnacerecipes.config.ConfigManager;
import io.github.elihuso.morefurnacerecipes.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MoreFurnaceRecipes extends JavaPlugin {
    static {
        ConfigurationSerialization.registerClass(CustomItem.class);
    }

    private final ConfigManager config = new ConfigManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        CustomItem[] custom = config.Custom();
        if (custom != null) {
            for (var v : custom) {
                try {
                    addRecipe(v.getFromMaterial(), v.getToMaterial(), (float) v.getExperience(), v.getTime());
                }
                catch (Exception ex) {
                    getServer().getLogger().log(Level.WARNING, "Failed to add recipe from " + v.getFromString() + " to " + v.getToString());
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        delRecipe(Material.RAW_IRON_BLOCK);
        delRecipe(Material.RAW_GOLD_BLOCK);
        delRecipe(Material.RAW_COPPER_BLOCK);
        CustomItem[] custom = config.Custom();
        if (custom != null) {
            for (var v : custom) {
                try {
                    delRecipe(v.getFromMaterial());
                }
                catch (Exception ex) {
                    getServer().getLogger().log(Level.WARNING, "Failed to delete recipe from " + v.getFromString() + " to " + v.getToString());
                }
            }
        }
    }

    public void addRecipe(Material from, Material to, float experience, int time) {
        ItemStack item = new ItemStack(to);
        getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, from.getKey().getKey()), item, from, experience, time));
    }

    public void delRecipe(Material from) {
        getServer().removeRecipe(new NamespacedKey(this, from.getKey().getKey()));
    }
}
