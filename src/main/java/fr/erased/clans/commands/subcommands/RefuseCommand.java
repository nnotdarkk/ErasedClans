package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RefuseCommand {

    private final ErasedClans main;

    public RefuseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.refuse")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan refuse <clan>");
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

        ClanManager manager = new ClanManager(main, clan);
        manager.removeInvitation(player);
        player.sendMessage("§cVous avez refusé l'invitation du clan " + clan);
        Player owner = Bukkit.getPlayer(manager.getOwner());
        if (owner != null) {
            owner.sendMessage("§c" + player.getName() + " a refusé votre invitation");
        }
    }
}
