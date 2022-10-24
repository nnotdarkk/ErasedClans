package fr.erased.clans.commands;

import fr.erased.clans.Main;
import fr.erased.clans.manager.ChunkManager;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.ui.ClanUI;
import fr.erased.clans.ui.CreateUI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Clan implements CommandExecutor {

    private final Main main;
    private ClanManager clanManager;
    private PlayerManager playerManager;

    public Clan(Main main) {
        this.main = main;
        clanManager = new ClanManager(main);
        playerManager = new PlayerManager(main);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            ClanUI clanUI = new ClanUI(player, main);
            CreateUI createUI = new CreateUI(player, main);
            if(playerManager.inClan(player)) {
                clanUI.openClanUI(playerManager.getClan(player));
                return false;
            }
            createUI.openCreateUI();
            return false;
        }

        if(args[0].equalsIgnoreCase("invite")){
            if(args.length != 2) {
                player.sendMessage("§c/clan invite <joueur>");
                return false;
            }

            if(!playerManager.inClan(player)) {
                player.sendMessage("§cVous n'avez pas de clan");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage("§cCe joueur n'est pas connecté");
                return false;
            }

            String ownerid = clanManager.getOwner(playerManager.getClan(player));
            String playerid = player.getUniqueId().toString();

            if(!ownerid.equals(playerid)) {
                player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
                return false;
            }

            if(playerManager.inClan(target)){
                player.sendMessage("§cCe joueur est déjà dans un clan");
                return false;
            }

            String clan = playerManager.getClan(player);

            TextComponent accepter = new TextComponent("ACCEPTER");
            accepter.setColor(ChatColor.GREEN);
            accepter.setBold(true);
            accepter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan join " + clan));
            accepter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("(/clan join " + clan+ ")").color(ChatColor.GRAY).create()));

            TextComponent refuser = new TextComponent("REFUSER");
            refuser.setColor(ChatColor.RED);
            refuser.setBold(true);
            refuser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan refuse " + clan));
            refuser.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("(/clan refuse " + clan+ ")").color(ChatColor.GRAY).create()));

            target.sendMessage("§7Vous avez été invité dans le clan §e" + clan + "§7 par §e" + player.getName());
            target.spigot().sendMessage(accepter, new TextComponent(" §8▏ "), refuser);
            sender.sendMessage("§aVous avez invité " + target.getName() + " dans votre clan");
            clanManager.addInvitation(target, clan);
            return false;
        }

        if(args[0].equalsIgnoreCase("join")){
            if(args.length != 2) {
                player.sendMessage("§c/clan join <clan>");
                return false;
            }

            if(playerManager.inClan(player)) {
                player.sendMessage("§cVous êtes déjà dans un clan");
                return false;
            }

            String clan = args[1];
            if(!clanManager.hasInvitation(player, clan)) {
                player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
                return false;
            }

            clanManager.removeInvitation(player, clan);
            clanManager.addMember(clan, player);

            player.sendMessage("§aVous avez rejoint le clan " + clan);
            Player player1 = Bukkit.getPlayer(clanManager.getOwner(clan));
            if(player1 != null) {
                player1.sendMessage("§a" + player.getName() + " a accepté votre invitation");
            }
            return false;
        }

        if(args[0].equalsIgnoreCase("refuse")){
            if(args.length != 2) {
                player.sendMessage("§c/clan refuse <clan>");
                return false;
            }

            if(playerManager.inClan(player)) {
                player.sendMessage("§cVous êtes déjà dans un clan");
                return false;
            }

            String clan = args[1];
            if(!clanManager.hasInvitation(player, clan)) {
                player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
                return false;
            }

            clanManager.removeInvitation(player, clan);
            player.sendMessage("§cVous avez refusé l'invitation du clan " + clan);
            Player player1 = Bukkit.getPlayer(clanManager.getOwner(clan));
            if(player1 != null) {
                player1.sendMessage("§c" + player.getName() + " a refusé votre invitation");
            }
            return false;
        }

        if(args[0].equalsIgnoreCase("quit")){
            if(!(args.length == 1 || args.length == 2)) {
                player.sendMessage("§c/clan quit <confirm>");
                return false;
            }

            if(args[1].equalsIgnoreCase("confirm")){
                if(!playerManager.inClan(player)) {
                    player.sendMessage("§cVous n'êtes pas dans un clan");
                    return false;
                }

                String clan = playerManager.getClan(player);
                player.sendMessage("§cVous avez quitté le clan " + clan);
                clanManager.removeMember(clan, player);
                playerManager.unregisterClan(player.getUniqueId().toString());
                for (int i = 0; i < clanManager.getMembers(clan).size(); i++) {
                    Player member = Bukkit.getPlayer(clanManager.getMembers(clan).get(i));
                    if (member != null) {
                        member.sendMessage("§c§l» §7" + player.getName() + " a quitté le clan");
                    }
                }
            }

            if(!playerManager.inClan(player)) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            String clan = playerManager.getClan(player);
            ClanUI clanUI = new ClanUI(player, main);
            clanUI.quitClanUi(clan);
            return false;
        }

        if(args[0].equalsIgnoreCase("claim")){
            if(!playerManager.inClan(player)) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            Chunk chunk = player.getLocation().getChunk();
            if(main.getChunkManager().isClaimed(chunk)) {
                player.sendMessage("§cCe chunk est déjà claim");
                return false;
            }

            int claimsmax = main.getClanManager().getClanMaxClaims(playerManager.getClan(player));
            int claims = main.getClanManager().getClaims(playerManager.getClan(player)).size();
            if(claims >= claimsmax) {
                player.sendMessage("§cVous avez atteint le nombre maximum de claims");
                return false;
            }
            main.getChunkManager().claimChunk(player, playerManager.getClan(player));
            player.sendMessage("§a§l» §7Vous avez claim ce chunk avec succès");
            return false;
        }

        if(args[0].equalsIgnoreCase("unclaim")){
            if(!playerManager.inClan(player)) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            Chunk chunk = player.getLocation().getChunk();
            if(!main.getChunkManager().isClaimed(chunk)) {
                player.sendMessage("§cCe chunk n'est pas encore claim");
                return false;
            }

            String claimer = main.getChunkManager().getClaimer(chunk);
            String clan = playerManager.getClan(player);
            if(!claimer.equals(clan)) {
                player.sendMessage("§cCe chunk n'est pas claim par votre clan");
                return false;
            }

            player.sendMessage("§c§l» §7Vous avez unclaim ce chunk avec succès");
            main.getChunkManager().unClaimChunk(player);
        }

        if(args[0].equalsIgnoreCase("unclaimall")){
            if(!playerManager.inClan(player)) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            String ownerid = clanManager.getOwner(playerManager.getClan(player));
            String playerid = player.getUniqueId().toString();

            if(!ownerid.equals(playerid)) {
                player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
                return false;
            }

            main.getChunkManager().removeAllClaimsForClan(playerManager.getClan(player));
            main.getClanManager().removeAllChunks(playerManager.getClan(player));

            player.sendMessage("§c§l» §7Vous avez unclaim tous les chunks de votre clan");
        }


        if(args[0].equalsIgnoreCase("ally")){
            if(args.length != 2) {
                player.sendMessage("§c/clan ally <clan>");
                return false;
            }

            if(!playerManager.inClan(player)) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            String ownerid = clanManager.getOwner(playerManager.getClan(player));
            String playerid = player.getUniqueId().toString();

            if(!ownerid.equals(playerid)) {
                player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
                return false;
            }

            if(!clanManager.clanExists(args[1])) {
                player.sendMessage("§cCe clan n'existe pas");
                return false;
            }

            if(clanManager.getAllies(playerManager.getClan(player)).contains(args[1])) {
                player.sendMessage("§cVous n'êtes plus alliés avec: " + args[1]);
                clanManager.removeAllies(playerManager.getClan(player), args[1]);
                return false;
            }

            player.sendMessage("§aVous êtes désormais alliés avec: " + args[1]);
            clanManager.addAllies(playerManager.getClan(player), args[1]);
            return false;
        }

        if(args[0].equalsIgnoreCase("create")){
            if(args.length != 1){
                player.sendMessage("§c/clan create");
                return false;
            }

            if(playerManager.inClan(player)) {
                player.sendMessage("§cVous êtes déjà dans un clan");
                return false;
            }

            CreateUI createUI = new CreateUI(player, main);
            createUI.openCreateUI();
        }
        return false;
    }

}
