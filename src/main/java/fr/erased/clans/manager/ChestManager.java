package fr.erased.clans.manager;

import fr.erased.clans.Main;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChestManager {

    private final Main main;
    private final Player player;

    public ChestManager(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    public void openChest(){
        String clan = main.getPlayerManager().getClan(player);
        Inventory inv = main.getClanManager().getClanChest(clan);
        inv = addGlass(inv);

        player.openInventory(inv);
    }

    public Inventory addGlass(Inventory inv){
        String clan = main.getPlayerManager().getClan(player);
        int level = main.getClanManager().getClanLevel(clan);

        if(level < 100){
            for (int i = 45; i < 54; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 100").build(false));
            }
        }

        if(level < 80){
            for (int i = 36; i < 45; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 80").build(false));
            }
        }

        if(level < 50){
            for (int i = 27; i < 36; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 50").build(false));
            }
        }

        if(level < 40){
            for (int i = 18; i < 27; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 40").build(false));
            }
        }

        if(level < 20){
            for (int i = 9; i < 18; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 20").build(false));
            }
        }
        return inv;
    }
}
