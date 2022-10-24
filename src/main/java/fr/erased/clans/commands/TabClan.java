package fr.erased.clans.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabClan implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> list = new ArrayList<>();
        List<String> playersName = new ArrayList<>();

        if(args.length == 1) {
            list.add("invite");
            list.add("kick");
            list.add("quit");
            list.add("join");
            list.add("refuse");
            list.add("claim");
            list.add("unclaim");
            list.add("unclaimall");
            list.add("create");
            return list;
        }

        if(args.length == 2){
            for(Player player : Bukkit.getOnlinePlayers()){
                playersName.add(player.getName());
            }
            return playersName;
        }
        return null;
    }

}
