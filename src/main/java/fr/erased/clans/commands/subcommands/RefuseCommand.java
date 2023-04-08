package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RefuseCommand {

    private final Main main;

    public RefuseCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.refuse")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(args.getArgs().length != 2) {
            player.sendMessage("§c/clan refuse <clan>");
            return;
        }

        if(playerManager.inClan()) {
            player.sendMessage("§cVous êtes déjà dans un clan");
            return;
        }

        String clan = args.getArgs(1);
        if(!clanManager.hasInvitation(player, clan)) {
            player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
            return;
        }

        ClanManager manager = new ClanManager(main, clan);
        manager.removeInvitation(player);
        player.sendMessage("§cVous avez refusé l'invitation du clan " + clan);
        Player player1 = Bukkit.getPlayer(manager.getOwner());
        if(player1 != null) {
            player1.sendMessage("§c" + player.getName() + " a refusé votre invitation");
        }
    }
}
