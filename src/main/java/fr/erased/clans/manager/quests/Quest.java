package fr.erased.clans.manager.quests;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class Quest implements Listener {

    public abstract void registerListeners(Plugin plugin);

}
