package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class FlyCommand {

    private final ErasedClans main;

    public FlyCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.fly", permission = "clans.flyclaims")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if (playerManager.getClan().equals("null")) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (!main.getChunkManager().isClaimed(player.getLocation().getChunk())) {
            player.sendMessage("§cVous ne pouvez pas activer le fly dans une zone non claim !");
            return;
        }

        String claimer = main.getChunkManager().getClaimer(player.getLocation().getChunk());
        String playerClan = playerManager.getClan();

        if (!claimer.equals(playerClan)) {
            player.sendMessage("§cVous ne pouvez pas activer le fly dans une zone claim par un autre clan !");
            return;
        }

        if (playerManager.isFly()) {
            player.setAllowFlight(false);
            playerManager.removeFly();
            player.sendMessage("§e§lErased§6§lClans §7» §eVous ne pouvez plus voler dans vos claims");
        } else {
            player.setAllowFlight(true);
            playerManager.addFly();
            player.sendMessage("§e§lErased§6§lClans §7» §eVous pouvez désormais voler dans vos claims");
        }
    }
}
