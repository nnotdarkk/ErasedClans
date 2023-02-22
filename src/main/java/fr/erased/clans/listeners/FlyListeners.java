package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.user.PlayerManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyListeners implements Listener {

    private final Main main;

    public FlyListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(PlayerMoveEvent e){
        Chunk from = e.getFrom().getChunk();
        Chunk to = e.getTo().getChunk();

        PlayerManager playerManager = new PlayerManager(main);

        Player player = e.getPlayer();

        if(from != to){
            if(!main.getChunkManager().isClaimed(to)){

                if(main.getChunkManager().isClaimed(from)){
                    player.sendTitle("", "§7Vous sortez du clan §6" + main.getChunkManager().getClaimer(from), 10, 20, 10);
                }

                if(playerManager.isFly(player)){
                    player.setAllowFlight(false);
                    playerManager.setFly(e.getPlayer(), false);
                    player.sendMessage("§cVotre fly à été désactivé car vous avez quitté votre claim.");
                }

                return;
            }

            if(main.getChunkManager().isClaimed(to)){
                String claimer = main.getChunkManager().getClaimer(to);
                String playerclan = playerManager.getClan(e.getPlayer());

                if(!claimer.equals(playerclan)){
                    if(playerManager.isFly(player)){
                        player.setAllowFlight(false);
                        playerManager.setFly(e.getPlayer(), false);
                        player.sendMessage("§cVotre fly à été désactivé car vous avez quitté votre claim.");
                    }
                }

                String claimto = main.getChunkManager().getClaimer(to);
                String claimfrom = main.getChunkManager().getClaimer(from);

                if(!claimto.equals(claimfrom)){
                    player.sendTitle("", "§7Vous entrez dans le clan §6" + claimto, 10, 20, 10);
                }
            }

        }
    }
}
