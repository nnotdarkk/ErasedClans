package fr.erased.libs.tools;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.entity.Player;

public class LuckPermsHandler {

    private Player player;
    public LuckPermsHandler(Player p) {
        this.player = p;
    }

    public String getPrefix() {
        return LuckPermsProvider.get().getUserManager().getUser(player.getName()).getCachedData().getMetaData().getPrefix().replaceAll("&", "§");
    }

    public String getPrimaryGroup(){
        return LuckPermsProvider.get().getGroupManager().getGroup(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup()).getName();
    }

    public int getWeight(){
        return LuckPermsProvider.get().getGroupManager().getGroup(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup()).getWeight().orElse(0);
    }
}