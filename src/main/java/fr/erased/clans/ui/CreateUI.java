package fr.erased.clans.ui;

import fr.erased.clans.Main;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CreateUI {

    private final Main main;
    private final Player player;

    public CreateUI(Player player, Main main) {
        this.main = main;
        this.player = player;
    }

    public void openCreateUI(){
        Inventory inv = Bukkit.createInventory(null, 27, "Créer un clan");

        ItemStack create = new ItemBuilder(Material.LIME_TERRACOTTA).setDisplayName("§aCréer un clan").build(false);
        ItemStack cancel = new ItemBuilder(Material.RED_TERRACOTTA).setDisplayName("§cAnnuler").build(false);

        inv.setItem(12, create);
        inv.setItem(14, cancel);

        player.openInventory(inv);
    }
}
