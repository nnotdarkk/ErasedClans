package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class SetBaseCommand {

    private final ErasedClans main;

    public SetBaseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.setbase")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (playerManager.getClan().equals("null")) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (playerManager.getPlayerRank() == PlayerRank.RECRUE || playerManager.getPlayerRank() == PlayerRank.MEMBRE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (OFFICIER)");
            return;
        }

        if (clanManager.getClanLevel() < 20) {
            player.sendMessage("§cVous devez être niveau 20 pour définir une base de clan");
            return;
        }

        clanManager.setClanBase(player.getLocation());
        player.sendMessage("§a§l» §7Vous avez défini la base de votre clan avec succès");
    }
}
