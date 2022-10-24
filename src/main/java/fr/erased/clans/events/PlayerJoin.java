package fr.erased.clans.events;

import fr.erased.clans.Main;
import fr.erased.clans.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private PlayerManager playerManager;
    private final Main main;

    public PlayerJoin(Main main) {
        this.main = main;
        playerManager = new PlayerManager(main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        playerManager.registerPlayer(e.getPlayer());

        playerManager.setState(e.getPlayer(), false);
    }
}
