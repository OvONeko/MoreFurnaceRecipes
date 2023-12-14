package io.github.elihuso.morefurnacerecipes;

import io.github.elihuso.morefurnacerecipes.config.ConfigManager;
import io.github.elihuso.morefurnacerecipes.modules.RecipeManager;
import io.github.elihuso.morefurnacerecipes.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MoreFurnaceRecipes extends JavaPlugin {
    static {
        ConfigurationSerialization.registerClass(CustomItem.class);
    }

    private final ConfigManager config = new ConfigManager(this);
    private final RecipeManager recipe = new RecipeManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (config.AddDefault()) {
            recipe.addFurnaceRecipe(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK, config.Experience(), config.Time());
            recipe.addFurnaceRecipe(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK, config.Experience(), config.Time());
            recipe.addFurnaceRecipe(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK, config.Experience(), config.Time());
            if (config.AllowBlast()) {
                recipe.addBlastRecipe(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK, config.Experience() * 2, config.Time() / 2);
                recipe.addBlastRecipe(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK, config.Experience() * 2, config.Time() / 2);
                recipe.addBlastRecipe(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK, config.Experience() * 2, config.Time() / 2);
            }
        }
        CustomItem[] custom = config.Custom();
        if (custom != null) {
            for (var v : custom) {
                try {
                    recipe.addFurnaceRecipe(v.getFromMaterial(), v.getToMaterial(), (float) v.getExperience(), v.getTime());
                    if (v.isAllowBlast())
                        recipe.addBlastRecipe(v.getFromMaterial(), v.getToMaterial(), (float) v.getExperience() * 2, v.getTime() / 2);
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
        if (config.AddDefault()) {
            recipe.delFurnaceRecipe(Material.RAW_IRON_BLOCK);
            recipe.delFurnaceRecipe(Material.RAW_GOLD_BLOCK);
            recipe.delFurnaceRecipe(Material.RAW_COPPER_BLOCK);
            if (config.AllowBlast()) {
                recipe.delBlastRecipe(Material.RAW_IRON_BLOCK);
                recipe.delBlastRecipe(Material.RAW_GOLD_BLOCK);
                recipe.delBlastRecipe(Material.RAW_COPPER_BLOCK);
            }
        }
        CustomItem[] custom = config.Custom();
        if (custom != null) {
            for (var v : custom) {
                try {
                    recipe.delFurnaceRecipe(v.getFromMaterial());
                    if (v.isAllowBlast()) {
                        recipe.delBlastRecipe(v.getFromMaterial());
                    }
                }
                catch (Exception ex) {
                    getServer().getLogger().log(Level.WARNING, "Failed to delete recipe from " + v.getFromString() + " to " + v.getToString());
                }
            }
        }
    }
}
