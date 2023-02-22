package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.user.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

    private final PlayerManager playerManager;

    public PlayerConnection(Main main) {
        playerManager = new PlayerManager(main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        playerManager.registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        playerManager.removeFly(e.getPlayer());
        playerManager.removeFly(e.getPlayer());
    }
}
