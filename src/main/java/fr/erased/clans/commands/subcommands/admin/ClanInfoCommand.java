package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ClanInfoCommand {

    private final ErasedClans main;

    public ClanInfoCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.claninfo", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin claninfo <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        ClanManager clanManager = new ClanManager(main, clan);

        player.sendMessage("§7Informations sur le clan: §e" + clan);

        player.sendMessage(" §8▪ §7Chef du clan: §e" + new PlayerManager(main, UUID.fromString(clanManager.getOwner())).getName());
        player.sendMessage(" §8▪ §7XP du clan: §e" + clanManager.getClanXp());
        ExpUtils expUtils = new ExpUtils(clanManager.getClanXp());
        player.sendMessage(" §8▪ §7Niveau du clan: §e" + clanManager.getClanLevel()
                + " §7(" + expUtils.getActualExp() + "/" + expUtils.getActualExpToNextLevel() + ")");
        player.sendMessage(" §8▪ §7Membres du clan: §e");

        for (String member : clanManager.getMembers()) {
            player.sendMessage(" §7- §e" + new PlayerManager(main, UUID.fromString(member)).getName());
        }

        if(clanManager.getClanBase() != null){
            Location location = clanManager.getClanBase();
            player.sendMessage(" §8▪ §7Base du clan: §ex:"
                    + (int) location.getX() + "/y:"
                    + (int) location.getY() + "/z:"
                    + (int) location.getZ() + " ("
                    + location.getWorld().getName() + ")");
            player.sendMessage(" §8▪ §7Claims du clan: §e");
        } else {
            player.sendMessage("  §8▪ §7Base du clan: §cAucune");
        }


        for (String chunk : clanManager.getClaims()) {
            player.sendMessage(" §7- §e" + chunk);
        }

        player.sendMessage(" §8▪ §7Statut du clan: §e" + clanManager.getClanStatus());

    }
}
