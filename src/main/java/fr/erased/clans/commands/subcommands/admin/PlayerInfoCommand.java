package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class PlayerInfoCommand {

    private final ErasedClans main;

    public PlayerInfoCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.playerinfo", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin playerinfo <player>");
            return;
        }

        String target = args.getArgs(0);

        PlayerManager playerManager = null;
        try {
            //TODO playerManager = new PlayerManager(main, target);
        } catch (Exception e){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }


        player.sendMessage("§7Informations sur §e" + playerManager.getName());
        player.sendMessage(" §8▪ §7Clan: §e" + playerManager.getClan());
        player.sendMessage(" §8▪ §7Grade: §e" + playerManager.getStringRank());
    }
}
