package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.invetory.ClanUI;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class QuitCommand {

    private final ErasedClans main;

    public QuitCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.quit")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);

        if (!playerManager.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        ClanUI clanUI = new ClanUI(player, main, playerManager.getClan());
        clanUI.quitClanUi();
    }
}
