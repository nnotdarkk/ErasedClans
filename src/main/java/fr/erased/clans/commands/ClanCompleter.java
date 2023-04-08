package fr.erased.clans.commands;

import fr.erased.clans.utils.commands.CommandArgs;
import fr.erased.clans.utils.commands.Completer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ClanCompleter {

    @Completer(name = "clan")
    public List<String> onTabComplete(CommandArgs args) {

        List<String> list = new ArrayList<>();

        if(args.getArgs().length == 1) {
            list.add("base");
            list.add("chest");
            list.add("claim");
            list.add("create");
            list.add("demote");
            list.add("fly");
            list.add("invite");
            list.add("join");
            list.add("promote");
            list.add("quit");
            list.add("refuse");
            list.add("setbase");
            list.add("unclaim");
            list.add("unclaimall");
            list.add("kick");
            return list;
        }

        return null;
    }
}
