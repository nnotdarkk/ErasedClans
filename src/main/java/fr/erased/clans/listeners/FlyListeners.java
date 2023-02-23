package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.user.PlayerManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class FlyListeners implements Listener {

    private final Main main;

    public FlyListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(PlayerMoveEvent e){
        Chunk from = e.getFrom().getChunk();
        Chunk to = Objects.requireNonNull(e.getTo()).getChunk();

        Player player = e.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if(from != to){
            if(!main.getChunkManager().isClaimed(to)){
                if(main.getChunkManager().isClaimed(from)){
                    player.sendTitle("", "§7Vous sortez du clan §6" + main.getChunkManager().getClaimer(from), 10, 20, 10);
                }

                if(playerManager.isFly()){
                    player.setAllowFlight(false);
                    playerManager.removeFly();
                    player.sendMessage("§cVotre fly à été désactivé car vous avez quitté votre claim.");
                }

                return;
            }

            if(main.getChunkManager().isClaimed(to)){
                String claimer = main.getChunkManager().getClaimer(to);
                String playerClan = playerManager.getClan();

                if(!claimer.equals(playerClan)){
                    if(playerManager.isFly()){
                        player.setAllowFlight(false);
                        playerManager.removeFly();
                        player.sendMessage("§cVotre fly à été désactivé car vous avez quitté votre claim.");
                    }
                }

                String claimTo = main.getChunkManager().getClaimer(to);
                String claimFrom = main.getChunkManager().getClaimer(from);

                if(!claimTo.equals(claimFrom)){
                    player.sendTitle("", "§7Vous entrez dans le clan §6" + claimTo, 10, 20, 10);
                }
            }

        }
    }
}
