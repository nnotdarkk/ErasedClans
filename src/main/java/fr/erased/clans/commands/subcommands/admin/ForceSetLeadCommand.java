package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceSetLeadCommand {

    private final ErasedClans main;

    public ForceSetLeadCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcesetlead", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcesetlead <joueur>");
            return;
        }

        String lead = args.getArgs(0);

        PlayerManager leadManager = null;
        try {
            //TODO leadManager = new PlayerManager(main, lead);
        } catch (Exception e){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        if(leadManager.getPlayerRank() == PlayerRank.CHEF){
            player.sendMessage("§cCe joueur est déjà le chef du clan !");
            return;
        }

        //TODO: for every player, remove the chef

        leadManager.setPlayerRank(PlayerRank.CHEF);
        player.sendMessage("§aVous avez mis " + lead + " en tant que chef de ce clan. Attention, l'ancien chef de clan est passé officier");
    }
}