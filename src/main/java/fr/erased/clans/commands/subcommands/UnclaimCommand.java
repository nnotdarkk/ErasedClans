package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class UnclaimCommand {

    private final ErasedClans main;

    public UnclaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.unclaim")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if (!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        if (playerManager.getPlayerRank() == PlayerRank.RECRUE || playerManager.getPlayerRank() == PlayerRank.MEMBRE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (OFFICIER)");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if (!main.getChunkManager().isClaimed(chunk)) {
            player.sendMessage("§cCe chunk n'est pas encore claim");
            return;
        }

        String claimer = main.getChunkManager().getClaimer(chunk);
        String clan = playerManager.getClan();
        if (!claimer.equals(clan)) {
            player.sendMessage("§cCe chunk n'est pas claim par votre clan");
            return;
        }

        player.sendMessage("§c§l» §7Vous avez unclaim ce chunk avec succès");
        main.getChunkManager().unClaimChunk(player);
    }
}
