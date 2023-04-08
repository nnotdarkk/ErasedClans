package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BaseCommand {

    private final Main main;

    public BaseCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.base")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(playerManager.getClan().equals("null")){
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if(clanManager.getClanBase() == null){
            player.sendMessage("§cVotre clan n'a pas encore de base !");
            return;
        }

        player.teleport(clanManager.getClanBase());
    }
}
