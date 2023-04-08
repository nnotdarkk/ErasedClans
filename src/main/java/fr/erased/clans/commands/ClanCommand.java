package fr.erased.clans.commands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.ui.ClanUI;
import fr.erased.clans.ui.CreateUI;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ClanCommand {

    private final Main main;

    public ClanCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if(args.getArgs().length == 0) {
            if(playerManager.inClan()) {
                new ClanUI(player, main, playerManager.getClan()).openClanUI();
                return;
            }

            new CreateUI(player, main).openCreateUI();
        }
    }

}
