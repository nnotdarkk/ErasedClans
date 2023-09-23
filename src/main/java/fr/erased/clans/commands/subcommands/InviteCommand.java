package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.manager.enums.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand {

    private final ErasedClans main;

    public InviteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.invite")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan invite <joueur>");
            return;
        }

        if (!playerManager.inClan()) {
            player.sendMessage("§cVous n'avez pas de clan");
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));
        if (target == null) {
            player.sendMessage("§cCe joueur n'est pas connecté");
            return;
        }

        if (playerManager.getPlayerRank() != PlayerRank.CHEF) {
            player.sendMessage("§cVous n'avez pas la permission requise. (CHEF)");
            return;
        }

        PlayerManager targetManager = new PlayerManager(main, target);

        if (targetManager.inClan()) {
            player.sendMessage("§cCe joueur est déjà dans un clan");
            return;
        }

        int maxMembers = clanManager.getClanMaxMembers();
        int members = clanManager.getMembers().size();
        if (members >= maxMembers) {
            player.sendMessage("§cVotre clan est plein");
            return;
        }

        String clan = playerManager.getClan();

        TextComponent accepter = new TextComponent("ACCEPTER");
        accepter.setColor(ChatColor.GREEN);
        accepter.setBold(true);
        accepter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan join " + clan));
        accepter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("(/clan join " + clan + ")").color(ChatColor.GRAY).create()));

        TextComponent refuser = new TextComponent("REFUSER");
        refuser.setColor(ChatColor.RED);
        refuser.setBold(true);
        refuser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan refuse " + clan));
        refuser.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("(/clan refuse " + clan + ")").color(ChatColor.GRAY).create()));

        target.sendMessage("§7Vous avez été invité dans le clan §e" + clan + "§7 par §e" + player.getName());
        target.spigot().sendMessage(accepter, new TextComponent(" §8▏ "), refuser);
        player.sendMessage("§aVous avez invité " + target.getName() + " dans votre clan");
        clanManager.addInvitation(target);
    }
}
