package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClanChestInteract implements Listener {

    private final Main main;

    public ClanChestInteract(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        if(e.getView().getTitle().startsWith("Coffre du clan:")){
            if(e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)){
                e.setCancelled(true);
                return;
            }
        }
    }
}
