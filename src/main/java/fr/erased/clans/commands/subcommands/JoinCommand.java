package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JoinCommand {

    private final ErasedClans main;

    public JoinCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.join")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan join <clan>");
            return;
        }

        if (playerManager.inClan()) {
            player.sendMessage("§cVous êtes déjà dans un clan");
            return;
        }

        String clan = args.getArgs(0);
        if (!clanManager.hasInvitation(player, clan)) {
            player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
            return;
        }

        clanManager.removeInvitation(player);

        ClanManager manager = new ClanManager(main, clan);
        manager.addMember(playerManager);

        player.sendMessage("§aVous avez rejoint le clan " + clan);
        Player player1 = Bukkit.getPlayer(manager.getOwner());
        if (player1 != null) {
            player1.sendMessage("§a" + player.getName() + " a accepté votre invitation");
        }
    }
}
