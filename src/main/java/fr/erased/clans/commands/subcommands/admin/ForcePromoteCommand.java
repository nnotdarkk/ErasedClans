package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForcePromoteCommand {

    private final ErasedClans main;

    public ForcePromoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcepromote", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcepromote <player>");
            return;
        }

        String promoted = args.getArgs(0);

        PlayerManager promotedManager = null;
        try {
            //TODO promotedManager = new PlayerManager(main, promoted);
        } catch (Exception e){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        switch (promotedManager.getPlayerRank()) {
            case CHEF:
                player.sendMessage("§cCe joueur est déjà chef");
                break;
            case OFFICIER:
                player.sendMessage("§cImpossible de mettre quelqu'un admin avec cette commande, utilisez /clan admin forcesetlead <player>");
                break;
            case MEMBRE:
                promotedManager.setPlayerRank(PlayerRank.OFFICIER);
                player.sendMessage("§aVous avez promu " + player.getName() + " en officier");
                break;
            case RECRUE:
                promotedManager.setPlayerRank(PlayerRank.MEMBRE);
                player.sendMessage("§aVous avez promu " + player.getName() + " en membre");
                break;
        }
    }
}
