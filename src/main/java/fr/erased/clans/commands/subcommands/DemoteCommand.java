package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.storage.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DemoteCommand {

    private final Main main;

    public DemoteCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.demote")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(playerManager.getClan().equals("null")){
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        String ownerId = clanManager.getOwner();
        String playerId = player.getUniqueId().toString();

        if(!ownerId.equals(playerId)) {
            player.sendMessage("§cVous n'avez pas la permission de dé-promouvoir un membre");
            return;
        }

        if(args.getArgs().length != 2){
            player.sendMessage("§c/clan demote <joueur>");
            return;
        }

        if(player.getName().equals(args.getArgs(1))){
            player.sendMessage("§cVous ne pouvez pas vous dé-promouvoir");
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(1));
        if(target == null){
            player.sendMessage("§cCe joueur n'est pas connecté");
            return;
        }

        PlayerManager targetManager = new PlayerManager(main, target);

        if(!targetManager.getClan().equals(playerManager.getClan())){
            player.sendMessage("§cCe joueur n'est pas dans votre clan");
            return;
        }

        switch (targetManager.getPlayerRank()){
            case CHEF:
                break;
            case OFFICIER:
                targetManager.setPlayerRank(PlayerRank.MEMBRE);
                player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target.getName() + " §7en membre");
                target.sendMessage("§a§l» §7Vous avez été dé-promu membre par §e" + player.getName());
                break;
            case MEMBRE:
                targetManager.setPlayerRank(PlayerRank.RECRUE);
                player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target.getName() + " §7en recrue");
                target.sendMessage("§a§l» §7Vous avez été dé-promu recrue par §e" + player.getName());
                break;
            case RECRUE:
                player.sendMessage("§cCe joueur est déjà recrue");
                break;
        }
    }
}
