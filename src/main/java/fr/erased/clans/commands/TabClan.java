package fr.erased.clans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabClan implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.add("invite");
            list.add("kick");
            list.add("quit");
            list.add("join");
            list.add("refuse");
            list.add("claim");
        }

        return list;
    }

}
