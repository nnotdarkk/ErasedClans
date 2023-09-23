package fr.erased.clans.manager.quests.list.kill;

import fr.erased.clans.manager.quests.Quest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class KillMobsQuestType extends Quest {


    public void registerListeners(Plugin pm) {

    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e){
        Player player = e.getEntity().getKiller();

        if(player == null){
            return;
        }

        EntityType entityType = e.getEntity().getType();

    }
}
