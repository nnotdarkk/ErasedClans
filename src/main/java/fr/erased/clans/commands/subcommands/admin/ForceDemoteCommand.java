package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceDemoteCommand {

    private final ErasedClans main;

    public ForceDemoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcedemote", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcedemote <player>");
            return;
        }

        String promoted = args.getArgs(0);


        PlayerManager demotedManager = null;
        try {
            //TODO demotedManager = new PlayerManager(main, promoted);
        } catch (Exception e){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        switch (demotedManager.getPlayerRank()) {
            case CHEF:
                player.sendMessage("§cIl est impossible de demote un chef, pour le changer /forcesetlead <joueur>");
                break;
            case OFFICIER:
                demotedManager.setPlayerRank(PlayerRank.MEMBRE);
                player.sendMessage("§cVous avez dé-promu §e" + player.getName() + " en membre");
                break;
            case MEMBRE:
                demotedManager.setPlayerRank(PlayerRank.RECRUE);
                player.sendMessage("§cVous avez dé-promu " + player.getName() + " en recrue");
                break;
            case RECRUE:
                player.sendMessage("§cCe joueur ne peut plus aller plus bas, il est recrue");
                break;
        }
    }
}
