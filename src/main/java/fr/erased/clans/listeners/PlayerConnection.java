package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

    private final ErasedClans main;

    public PlayerConnection(ErasedClans main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("§a+ " + e.getPlayer().getName());
        new PlayerManager(main, e.getPlayer()).registerPlayer(e.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§c- " + e.getPlayer().getName());
        PlayerManager playerManager = new PlayerManager(main, e.getPlayer());
        playerManager.removeFly();
        playerManager.removeCreateState();
        playerManager.removeBypassClaims();
    }

}
