package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class UnclaimCommand {

    private final Main main;

    public UnclaimCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.unclaim")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if(!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if(!main.getChunkManager().isClaimed(chunk)) {
            player.sendMessage("§cCe chunk n'est pas encore claim");
            return;
        }

        String claimer = main.getChunkManager().getClaimer(chunk);
        String clan = playerManager.getClan();
        if(!claimer.equals(clan)) {
            player.sendMessage("§cCe chunk n'est pas claim par votre clan");
            return;
        }

        player.sendMessage("§c§l» §7Vous avez unclaim ce chunk avec succès");
        main.getChunkManager().unClaimChunk(player);
    }
}
