package fr.erased.clans.storage;

import fr.erased.clans.Main;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChestManager {

    private final Player player;
    private final ClanManager clanManager;

    public ChestManager(Main main, Player player) {
        this.player = player;
        this.clanManager = new ClanManager(main, new PlayerManager(main, player).getClan());
    }

    public void openChest(){
        Inventory inv = clanManager.getClanChest();
        inv = addGlass(inv);

        player.openInventory(inv);
    }

    public Inventory addGlass(Inventory inv){

        int level = clanManager.getClanLevel();

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

    public Inventory removeGlass(Inventory inv){
        for (int i = 9; i < 54; i++) {
            if(inv.getItem(i) == null) continue;
            if(inv.getItem(i).getType().equals(Material.GRAY_STAINED_GLASS_PANE)){
                inv.setItem(i, null);
            }
        }
        return inv;
    }
}
