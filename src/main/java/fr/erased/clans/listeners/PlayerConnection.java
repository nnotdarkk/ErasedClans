package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.storage.user.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

    private final Main main;

    public PlayerConnection(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        new PlayerManager(main, e.getPlayer()).registerPlayer();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerManager playerManager = new PlayerManager(main, e.getPlayer());
        playerManager.removeFly();
        playerManager.removeCreateState();
    }
}
