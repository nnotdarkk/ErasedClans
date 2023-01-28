package fr.erased.clans.events;

import fr.erased.clans.Main;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerClaims implements Listener {

    private final Main main;

    public PlayerClaims(Main main) {
        this.main = main;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(BlockBreakEvent e){
        if(main.getChunkManager().isClaimed(e.getBlock().getLocation().getChunk())){
            String uuid = e.getPlayer().getUniqueId().toString();
            Chunk chunk = e.getPlayer().getLocation().getChunk();
            String clan = main.getChunkManager().getClaimer(chunk);
            if(!main.getClanManager().getMembers(clan).contains(uuid)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cVous ne pouvez pas faire ça dans une zone claim.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(BlockPlaceEvent e){
        if(main.getChunkManager().isClaimed(e.getBlock().getLocation().getChunk())){
            String uuid = e.getPlayer().getUniqueId().toString();
            Chunk chunk = e.getPlayer().getLocation().getChunk();
            String clan = main.getChunkManager().getClaimer(chunk);
            if(!main.getClanManager().getMembers(clan).contains(uuid)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cVous ne pouvez pas faire ça dans une zone claim.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void event(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getType() == null) return;

        if(main.getChunkManager().isClaimed(e.getClickedBlock().getLocation().getChunk())){
            String uuid = e.getPlayer().getUniqueId().toString();
            Chunk chunk = e.getPlayer().getLocation().getChunk();
            String clan = main.getChunkManager().getClaimer(chunk);
            if(!main.getClanManager().getMembers(clan).contains(uuid)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cVous ne pouvez pas faire ça dans une zone claim.");
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
