package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TeleportBaseCommand {

    private final ErasedClans main;

    public TeleportBaseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.teleportbase", permission = "clan.admin")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin teleportbase <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        ClanManager clanManager = new ClanManager(main, clan);
        if (clanManager.getClanBase() == null) {
            player.sendMessage("§cCe clan n'a pas de base !");
            return;
        }

        player.teleport(clanManager.getClanBase());
        player.sendMessage("§aTéléporté à la base de " + clan);
    }
}
