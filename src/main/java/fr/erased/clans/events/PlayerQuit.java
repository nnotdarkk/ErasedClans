package fr.erased.clans.events;

import fr.erased.clans.Main;
import fr.erased.clans.fly.FlyStorage;
import fr.erased.clans.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final Main main;

    public PlayerQuit(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerManager playerManager = new PlayerManager(main);

        new FlyStorage().setFly(e.getPlayer(), false);
        playerManager.setState(e.getPlayer(), false);
    }
}
