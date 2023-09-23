package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ChestManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChestCommand {

    private final ErasedClans main;

    public ChestCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.chest")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if (playerManager.getClan().equals("null")) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (playerManager.getPlayerRank() == PlayerRank.RECRUE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (MEMBRE)");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().equals("Coffre du clan: " + playerManager.getClan())) {
                player.sendMessage("§cVotre coffre de clan est déjà ouvert par un autre membre du clan.");
                return;
            }
        }

        ChestManager chestManager = new ChestManager(main, player);
        chestManager.openChest();
    }
}
