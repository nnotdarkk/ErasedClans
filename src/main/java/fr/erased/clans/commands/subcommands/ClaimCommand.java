package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ClaimCommand {

    private final ErasedClans main;

    public ClaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.claim")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        if (playerManager.getPlayerRank() == PlayerRank.RECRUE || playerManager.getPlayerRank() == PlayerRank.MEMBRE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (OFFICIER)");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if (main.getChunkManager().isClaimed(chunk)) {
            player.sendMessage("§cCe chunk est déjà claim");
            return;
        }

        int claimsMax = clanManager.getClanMaxClaims();
        int claims = clanManager.getClaims().size();
        if (claims >= claimsMax) {
            player.sendMessage("§cVous avez atteint le nombre maximum de claims");
            return;
        }

        main.getChunkManager().claimChunk(player, playerManager.getClan());
        player.sendMessage("§a§l» §7Vous avez claim ce chunk avec succès");
    }
}
