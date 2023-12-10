package io.github.elihuso.morefurnacerecipes;

import io.github.elihuso.morefurnacerecipes.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoreFurnaceRecipes extends JavaPlugin {
    ConfigManager config = new ConfigManager(this);
    @Override
    public void onEnable() {
        // Plugin startup logic
        addRecipe(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK, config.Experience(), config.Time());
        addRecipe(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK, config.Experience(), config.Time());
        addRecipe(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK, config.Experience(), config.Time());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        delRecipe(Material.RAW_IRON_BLOCK);
        delRecipe(Material.RAW_GOLD_BLOCK);
        delRecipe(Material.RAW_COPPER_BLOCK);
    }

    public void addRecipe(Material from, Material to, float experience, int time) {
        ItemStack item = new ItemStack(to);
        getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, from.getKey().getKey()), item, from, experience, time));
    }

    public void delRecipe(Material from) {
        getServer().removeRecipe(new NamespacedKey(this, from.getKey().getKey()));
    }
}
