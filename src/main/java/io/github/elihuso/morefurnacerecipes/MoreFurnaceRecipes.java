package io.github.elihuso.morefurnacerecipes;

import io.github.elihuso.morefurnacerecipes.config.ConfigManager;
import io.github.elihuso.morefurnacerecipes.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.BlastingRecipe;
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
        if (config.AddDefault()) {
            addFurnaceRecipe(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK, config.Experience(), config.Time());
            addFurnaceRecipe(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK, config.Experience(), config.Time());
            addFurnaceRecipe(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK, config.Experience(), config.Time());
            if (config.AllowBlast()) {
                addBlastRecipe(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK, config.Experience() * 2, config.Time() / 2);
                addBlastRecipe(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK, config.Experience() * 2, config.Time() / 2);
                addBlastRecipe(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK, config.Experience() * 2, config.Time() / 2);
            }
        }
        CustomItem[] custom = config.Custom();
        if (custom != null) {
            for (var v : custom) {
                try {
                    addFurnaceRecipe(v.getFromMaterial(), v.getToMaterial(), (float) v.getExperience(), v.getTime());
                    if (v.isAllowBlast())
                        addBlastRecipe(v.getFromMaterial(), v.getToMaterial(), (float) v.getExperience() * 2, v.getTime() / 2);
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
        delFurnaceRecipe(Material.RAW_IRON_BLOCK);
        delFurnaceRecipe(Material.RAW_GOLD_BLOCK);
        delFurnaceRecipe(Material.RAW_COPPER_BLOCK);
        if (config.AllowBlast()) {
            delBlastRecipe(Material.RAW_IRON_BLOCK);
            delBlastRecipe(Material.RAW_GOLD_BLOCK);
            delBlastRecipe(Material.RAW_COPPER_BLOCK);
        }
        CustomItem[] custom = config.Custom();
        if (custom != null) {
            for (var v : custom) {
                try {
                    delFurnaceRecipe(v.getFromMaterial());
                    if (v.isAllowBlast()) {
                        delBlastRecipe(v.getFromMaterial());
                    }
                }
                catch (Exception ex) {
                    getServer().getLogger().log(Level.WARNING, "Failed to delete recipe from " + v.getFromString() + " to " + v.getToString());
                }
            }
        }
    }

    public void addFurnaceRecipe(Material from, Material to, float experience, int time) {
        ItemStack item = new ItemStack(to);
        getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, from.getKey().getKey()), item, from, experience, time));
    }

    public void delFurnaceRecipe(Material from) {
        getServer().removeRecipe(new NamespacedKey(this, from.getKey().getKey()));
    }

    public void addBlastRecipe(Material from, Material to, float experience, int time) {
        ItemStack item = new ItemStack(to);
        getServer().addRecipe(new BlastingRecipe(new NamespacedKey(this, from.getKey().getKey() + "_Blast"), item, from, experience, time));
    }

    public void delBlastRecipe(Material from) {
        getServer().removeRecipe(new NamespacedKey(this, from.getKey().getKey() + "_Blast"));
    }
}
