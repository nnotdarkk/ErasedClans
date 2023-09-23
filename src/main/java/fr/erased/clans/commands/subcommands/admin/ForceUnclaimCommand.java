package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ForceUnclaimCommand {

    private final ErasedClans main;

    public ForceUnclaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceunclaim", permission = "clan.admin")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        Chunk chunk = player.getLocation().getChunk();

        if(!main.getChunkManager().isClaimed(chunk)){
            player.sendMessage("§cCe chunk c'est pas claim.");
            return;
        }

        String clan = main.getChunkManager().getClaimer(chunk);
        main.getChunkManager().unClaimChunk(player);
        player.sendMessage("§aVous avez unclaim ce chunk du clan " + clan);
    }
}