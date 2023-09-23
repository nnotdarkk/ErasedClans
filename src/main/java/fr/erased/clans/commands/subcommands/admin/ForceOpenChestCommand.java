package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForceOpenChestCommand {

    private final ErasedClans main;

    public ForceOpenChestCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceopenchest", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forceopenchest <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().equals("Coffre du clan: " + clan)) {
                p.closeInventory();
                p.sendMessage("§cCe coffre est entrain de se faire éditer par un administrateur.");
                break;
            }
        }

        ClanManager clanManager = new ClanManager(main, clan);
        player.openInventory(clanManager.getClanChest());
        player.sendMessage("§aVous avez ouvert le coffre du clan " + clan);
    }
}
