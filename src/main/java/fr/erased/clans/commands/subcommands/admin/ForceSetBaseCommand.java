package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ForceSetBaseCommand {

    private final ErasedClans main;

    public ForceSetBaseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcesetbase", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcesetbase <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Location location = player.getLocation();
        ClanManager clanManager = new ClanManager(main, clan);
        clanManager.setClanBase(location);
        player.sendMessage("§aVous avez défini la nouvelle base du clan " + clan);
    }
}
