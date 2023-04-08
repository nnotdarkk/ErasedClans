package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ClaimCommand {

    private final Main main;

    public ClaimCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.claim")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if(main.getChunkManager().isClaimed(chunk)) {
            player.sendMessage("§cCe chunk est déjà claim");
            return;
        }

        int claimsMax = clanManager.getClanMaxClaims();
        int claims = clanManager.getClaims().size();
        if(claims >= claimsMax) {
            player.sendMessage("§cVous avez atteint le nombre maximum de claims");
            return;
        }

        main.getChunkManager().claimChunk(player, playerManager.getClan());
        player.sendMessage("§a§l» §7Vous avez claim ce chunk avec succès");
    }
}
