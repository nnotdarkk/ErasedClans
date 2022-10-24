package fr.erased.clans.events;

import fr.erased.clans.Main;
import fr.erased.clans.manager.ClanManager;
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

    boolean t = false;
    String name = null;
    String tag;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        PlayerManager playerManager = new PlayerManager(main);
        Player player = e.getPlayer();

        if(playerManager.isState(player)){
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")){
                playerManager.setState(player, false);
                player.sendMessage("§cVous avez annulé la création de votre clan");
                return;
            }

            if(!t){
                name = e.getMessage();
                player.sendMessage("§aEcrivez du tag §7(Entre 3 et 4 caractères) §ade votre clan dans le chat §7('cancel' pour annuler)");
                t = true;
                return;
            }

            t = false;
            tag = e.getMessage();
            if(tag.length() < 3 || tag.length() > 4){
                player.sendMessage("§cLe tag doit contenir entre 3 et 4 caractères");
                playerManager.setState(player, false);
                return;
            }

            playerManager.setState(player, false);

            player.sendMessage("§aVotre clan a bien été créé !");
            player.sendMessage("§7Nom : " + name + " §8| §7Tag : " + tag.toUpperCase());

            clanManager.createClan(player, name, tag.toUpperCase()  );

            name = null;
            tag = null;
        }

    }
}
