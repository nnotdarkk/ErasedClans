package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ChestManager;
import fr.erased.clans.storage.ClanManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ClanChestInteract implements Listener {

    private final Main main;

    public ClanChestInteract(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        if(e.getView().getTitle().startsWith("Coffre du clan:")){
            if(e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)){
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void event(InventoryCloseEvent e){
        if(e.getView().getTitle().startsWith("Coffre du clan:")){
            String clanName = e.getView().getTitle().split(": ")[1];
            Player player = (Player) e.getPlayer();
            ChestManager chestManager = new ChestManager(main, player);
            Inventory inv = chestManager.removeGlass(e.getInventory());
            ClanManager clanManager = new ClanManager(main, clanName);
            clanManager.setClanChest(inv);
        }
    }
}
