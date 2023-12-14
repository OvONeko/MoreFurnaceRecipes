package io.github.elihuso.morefurnacerecipes.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class RecipeManager {
    private final Plugin plugin;

    public RecipeManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void addFurnaceRecipe(Material from, Material to, float experience, int time) {
        ItemStack item = new ItemStack(to);
        Bukkit.getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(plugin, from.getKey().getKey()), item, from, experience, time));
    }

    public void delFurnaceRecipe(Material from) {
        Bukkit.getServer().removeRecipe(new NamespacedKey(plugin, from.getKey().getKey()));
    }

    public void addBlastRecipe(Material from, Material to, float experience, int time) {
        ItemStack item = new ItemStack(to);
        Bukkit.getServer().addRecipe(new BlastingRecipe(new NamespacedKey(plugin, from.getKey().getKey() + "_Blast"), item, from, experience, time));
    }

    public void delBlastRecipe(Material from) {
        Bukkit.getServer().removeRecipe(new NamespacedKey(plugin, from.getKey().getKey() + "_Blast"));
    }
}
