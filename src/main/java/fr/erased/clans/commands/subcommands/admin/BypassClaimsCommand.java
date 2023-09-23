package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BypassClaimsCommand {

    private final ErasedClans main;

    public BypassClaimsCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.bypassclaims", permission = "clan.admin")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 0) {
            player.sendMessage("§c/clan admin bypassclaims");
            return;
        }

        PlayerManager playerManager = new PlayerManager(main, player);

        if(playerManager.isBypassClaims()){
            playerManager.removeBypassClaims();
            player.sendMessage("§cVous ne possédez plus le bypass des claims");
        } else {
            playerManager.addBypassClaims();
            player.sendMessage("§aVous possédez maintenant le bypass des claims");
        }
    }

}
