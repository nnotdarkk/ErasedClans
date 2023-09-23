package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PromoteCommand {

    private final ErasedClans main;

    public PromoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.promote")
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
            player.sendMessage("§c/clan promote <joueur>");
            return;
        }

        String target = args.getArgs(0);


        if (player.getName().equals(target)) {
            player.sendMessage("§cVous ne pouvez pas vous promouvoir");
            return;
        }

        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(target);
        Bukkit.broadcastMessage(Objects.requireNonNull(offlineTarget.getName()));
        Bukkit.broadcastMessage(String.valueOf(offlineTarget.getUniqueId()));
        PlayerManager targetManager = new PlayerManager(main, offlineTarget.getUniqueId());
        Player targetPlayer = Bukkit.getPlayer(target);

        if(targetManager.notExists()){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }


        if (!targetManager.getClan().equals(playerManager.getClan())) {
            player.sendMessage("§cCe joueur n'est pas dans votre clan");
            return;
        }

        switch (targetManager.getPlayerRank()) {
            case CHEF:
                break;
            case OFFICIER:
                player.sendMessage("§cCe joueur est déjà officier");
                break;
            case MEMBRE:
                targetManager.setPlayerRank(PlayerRank.OFFICIER);
                player.sendMessage("§a§l» §7Vous avez promu §e" + target + " §7en officier");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été promu officier par §e" + player.getName());
                }
                break;
            case RECRUE:
                targetManager.setPlayerRank(PlayerRank.MEMBRE);
                player.sendMessage("§a§l» §7Vous avez promu §e" + target + " §7en membre");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été promu membre par §e" + player.getName());
                }
                break;
        }
    }
}
