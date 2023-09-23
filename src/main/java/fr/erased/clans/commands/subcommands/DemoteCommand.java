package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class DemoteCommand {

    private final ErasedClans main;

    public DemoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.demote")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if (playerManager.getClan().equals("null")) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (playerManager.getPlayerRank() != PlayerRank.CHEF) {
            player.sendMessage("§cVous n'avez pas la permission requise. (CHEF)");
            return;
        }

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan demote <joueur>");
            return;
        }

        String target = args.getArgs(0);


        if (player.getName().equals(target)) {
            player.sendMessage("§cVous ne pouvez pas vous dé-promouvoir");
            return;
        }

        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(target);

        PlayerManager targetManager = new PlayerManager(main, offlineTarget.getUniqueId());

        Player targetPlayer = Bukkit.getPlayer(target);

        if (!targetManager.getClan().equals(playerManager.getClan())) {
            player.sendMessage("§cCe joueur n'est pas dans votre clan");
            return;
        }

        switch (targetManager.getPlayerRank()) {
            case CHEF:
                break;
            case OFFICIER:
                targetManager.setPlayerRank(PlayerRank.MEMBRE);
                player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target + " §7en membre");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été dé-promu membre par §e" + player.getName());
                }
                break;
            case MEMBRE:
                targetManager.setPlayerRank(PlayerRank.RECRUE);
                player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target + " §7en recrue");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été dé-promu recrue par §e" + player.getName());
                }
                break;
            case RECRUE:
                player.sendMessage("§cCe joueur est déjà recrue");
                break;
        }
    }
}
