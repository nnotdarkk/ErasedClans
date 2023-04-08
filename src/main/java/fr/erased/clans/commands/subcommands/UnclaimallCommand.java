package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class UnclaimallCommand {

    private final Main main;

    public UnclaimallCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.unclaimall")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        String ownerId = clanManager.getOwner();
        String playerId = player.getUniqueId().toString();

        if(!ownerId.equals(playerId)) {
            player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
            return;
        }

        main.getChunkManager().removeAllClaimsForClan(playerManager.getClan());
        clanManager.removeAllClaims();

        player.sendMessage("§c§l» §7Vous avez unclaim tous les chunks de votre clan");
    }
}
