package fr.erased.clans.events;

import fr.erased.clans.Main;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.FileManager;
import fr.erased.clans.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    private final Main main;
    private ClanManager clanManager;

    public AsyncPlayerChat(Main main) {
        this.main = main;
        clanManager = new ClanManager(main);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        PlayerManager playerManager = new PlayerManager(main);
        Player player = e.getPlayer();

        if(playerManager.isState(player)){
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("annuler")){
                playerManager.setState(player, false);
                player.sendMessage("§cVous avez annulé la création de votre clan");
                return;
            }

            if(e.getMessage().length() < 3){
                player.sendMessage("§cLe nom de votre clan doit faire au moins 3 caractères");
                playerManager.setState(player, false);
                return;
            }

            if(e.getMessage().length() > 16){
                player.sendMessage("§cLe nom de votre clan ne doit pas dépasser 16 caractères");
                playerManager.setState(player, false);
                return;
            }


            if(new FileManager(main).fileExists("clans", e.getMessage())){
                player.sendMessage("§cCe nom est déjà utilisé");
                playerManager.setState(player, false);
                return;
            }

            playerManager.setState(player, false);
            player.sendMessage("§aVotre clan a bien été créé ! /clan pour le consulter");

            clanManager.createClan(player, e.getMessage());
        }

    }
}
