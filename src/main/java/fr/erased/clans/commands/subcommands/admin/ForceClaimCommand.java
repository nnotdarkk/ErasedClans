package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ForceClaimCommand {

    private final ErasedClans main;

    public ForceClaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceclaim", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forceclaim <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();

        if(main.getChunkManager().isClaimed(chunk)){
            player.sendMessage("§cCe chunk est déjà claim !");
            return;
        }

        if(main.getChunkManager().getClaimer(chunk).equals(clan)){
            player.sendMessage("§cCe chunk est déjà claim pas ce clan");
            return;
        }

        main.getChunkManager().claimChunk(player, clan);
        player.sendMessage("§aVous avez claim ce chunk pour le clan " + clan + " avec succès");
    }
}
