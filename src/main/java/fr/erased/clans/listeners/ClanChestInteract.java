package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ChestManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

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

            Player player = (Player) e.getWhoClicked();
            String clan = main.getPlayerManager().getClan(player);

            Inventory inv = e.getInventory();
            for (int i = 0; i < e.getInventory().getSize(); i++) {
                if(e.getInventory().getItem(i) != null){
                    if(e.getInventory().getItem(i).getType().equals(Material.GRAY_STAINED_GLASS_PANE)){
                        inv.setItem(i, null);
                    }
                }
            }

            ChestManager cm = new ChestManager(main, player);
            main.getClanManager().setClanChest(clan, inv);

            for(Player p : main.getServer().getOnlinePlayers()){
                Inventory inv2 = main.getClanManager().getClanChest(clan);
                cm.addGlass(inv2);
                if(main.getPlayerManager().getClan(p).equals(clan)){
                    if(p.getOpenInventory().getTitle().startsWith("Coffre du clan:")){
                        p.getOpenInventory().getTopInventory().setContents(inv2.getContents());
                    }
                }
            }
        }
    }
}
