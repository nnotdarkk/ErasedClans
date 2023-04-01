package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClaimsCancels implements Listener {

    private final Main main;

    public ClaimsCancels(Main main) {
        this.main = main;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(BlockBreakEvent e){
        Chunk chunk = e.getBlock().getLocation().getChunk();
        if(main.getChunkManager().isClaimed(chunk)){
            String uuid = e.getPlayer().getUniqueId().toString();
            String clan = main.getChunkManager().getClaimer(chunk);
            if(!new ClanManager(main, clan).getMembers().contains(uuid)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(BlockPlaceEvent e){
        Chunk chunk = e.getBlock().getLocation().getChunk();
        if(main.getChunkManager().isClaimed(chunk)){
            String uuid = e.getPlayer().getUniqueId().toString();
            String clan = main.getChunkManager().getClaimer(chunk);
            if(!new ClanManager(main, clan).getMembers().contains(uuid)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;

        Chunk chunk = e.getClickedBlock().getLocation().getChunk();
        if(main.getChunkManager().isClaimed(chunk)){
            String uuid = e.getPlayer().getUniqueId().toString();
            String clan = main.getChunkManager().getClaimer(chunk);
            if(!new ClanManager(main, clan).getMembers().contains(uuid)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(main.getChunkManager().isClaimed(p.getLocation().getChunk())){
                e.setCancelled(true);
                p.sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler
    public void event(EntityExplodeEvent e){
        if(main.getChunkManager().isClaimed(e.getLocation().getChunk())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void event(ExplosionPrimeEvent e){
        if(main.getChunkManager().isClaimed(e.getEntity().getLocation().getChunk())){
            e.setCancelled(true);
        }
    }
}
