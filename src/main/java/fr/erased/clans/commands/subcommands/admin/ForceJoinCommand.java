package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceJoinCommand {

    private final ErasedClans main;

    public ForceJoinCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcejoin", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 2) {
            player.sendMessage("§c/clan admin forcejoin <clan> <player>");
            return;
        }

        String clan = args.getArgs(0);
        String target = args.getArgs(1);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        PlayerManager targetManager = null;
        try {
            //TODO targetManager = new PlayerManager(main, target);
        } catch (Exception e){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        if(targetManager.getClan().equals("null")){
            player.sendMessage("§cCe joueur est déjà dans un clan !");
            return;
        }

        ClanManager clanManager = new ClanManager(main, clan);
        clanManager.addMember(targetManager);
    }
}
