package fr.erased.clans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ClanTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> list = new ArrayList<>();

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
            list.add("fly");
            list.add("chest");
            return list;
        }

        return null;
    }
}
