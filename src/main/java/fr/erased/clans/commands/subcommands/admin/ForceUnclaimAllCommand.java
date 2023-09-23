package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceUnclaimAllCommand {

    private final ErasedClans main;

    public ForceUnclaimAllCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceunclaimall", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forceunclaimall <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        ClanManager clanManager = new ClanManager(main, clan);
        main.getChunkManager().removeAllClaimsForClan(clan);
        clanManager.removeAllClaims();
        player.sendMessage("§aVous avez unclaim touts les chunks du clan " + clan);
    }
}
