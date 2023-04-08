package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.ui.CreateUI;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class CreateCommand {

    private final Main main;

    public CreateCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.create")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if(playerManager.inClan()) {
            player.sendMessage("§cVous êtes déjà dans un clan");
            return;
        }

        CreateUI createUI = new CreateUI(player, main);
        createUI.openCreateUI();
    }
}
