package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    //TODO

    private final ErasedClans main;

    public HelpCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.help")
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        player.sendMessage("§cNon fait");
    }

}
