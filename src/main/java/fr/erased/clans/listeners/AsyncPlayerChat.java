package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.storage.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    private final Main main;

    public AsyncPlayerChat(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if(playerManager.isInCreateState()){
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("annuler")){
                playerManager.removeCreateState();
                player.sendMessage("§cVous avez annulé la création de votre clan");
                return;
            }

            if(e.getMessage().length() < 3){
                player.sendMessage("§cLe nom de votre clan doit faire au moins 3 caractères");
                playerManager.removeCreateState();
                return;
            }

            if(e.getMessage().length() > 16){
                player.sendMessage("§cLe nom de votre clan ne doit pas dépasser 16 caractères");
                playerManager.removeCreateState();
                return;
            }


            if(new FileUtils(main).fileExists("clans", e.getMessage())){
                player.sendMessage("§cCe nom est déjà utilisé, création annulée");
                playerManager.removeCreateState();
                return;
            }

            if(e.getMessage().equalsIgnoreCase("null")){
                player.sendMessage("§cNom de clan invalide, création annulée");
                playerManager.removeCreateState();
                return;
            }

            playerManager.removeCreateState();
            player.sendMessage("§aVotre clan a bien été créé ! /clan pour le consulter");

            new ClanManager(main, e.getMessage()).createClan(player);
        }

    }
}
