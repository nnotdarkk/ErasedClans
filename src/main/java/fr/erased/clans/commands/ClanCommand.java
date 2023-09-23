package fr.erased.clans.commands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.invetory.ClanUI;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ClanCommand {

    private final ErasedClans main;

    public ClanCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if (args.getArgs().length == 0) {
            if (playerManager.inClan()) {
                new ClanUI(player, main, playerManager.getClan()).openClanUI();
                return;
            }

            player.sendMessage("§cVous ne possédez pas de clan, utilisez /clan create pour en créer un");
            //new CreateUI(player, main).openCreateUI();
        }
    }

}
