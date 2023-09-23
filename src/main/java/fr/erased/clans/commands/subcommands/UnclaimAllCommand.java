package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class UnclaimAllCommand {

    private final ErasedClans main;

    public UnclaimAllCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.unclaimall")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);


        if (!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        if (playerManager.getPlayerRank() != PlayerRank.CHEF) {
            player.sendMessage("§cVous n'avez pas la permission requise. (CHEF)");
            return;
        }

        ClanManager clanManager = new ClanManager(main, playerManager.getClan());
        main.getChunkManager().removeAllClaimsForClan(playerManager.getClan());
        clanManager.removeAllClaims();

        player.sendMessage("§c§l» §7Vous avez unclaim tous les chunks de votre clan");
    }
}
