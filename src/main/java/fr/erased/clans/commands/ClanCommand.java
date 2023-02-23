package fr.erased.clans.commands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ChestManager;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.user.PlayerManager;
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
import org.jetbrains.annotations.NotNull;

public class ClanCommand implements CommandExecutor {

    private final Main main;

    public ClanCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        PlayerManager playerManager = new PlayerManager(main, player);

        if(args.length == 0) {
            if(playerManager.inClan()) {
                ClanUI clanUI = new ClanUI(player, main, playerManager.getClan());
                clanUI.openClanUI();
                return false;
            }

            CreateUI createUI = new CreateUI(player, main);
            createUI.openCreateUI();
            return false;
        }

        /*
        * Invite
         */

        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(args[0].equalsIgnoreCase("invite")){
            if(args.length != 2) {
                player.sendMessage("§c/clan invite <joueur>");
                return false;
            }

            if(!playerManager.inClan()) {
                player.sendMessage("§cVous n'avez pas de clan");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage("§cCe joueur n'est pas connecté");
                return false;
            }

            String ownerId = clanManager.getOwner();
            String playerId = player.getUniqueId().toString();

            if(!ownerId.equals(playerId)) {
                player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
                return false;
            }

            if(playerManager.inClan()){
                player.sendMessage("§cCe joueur est déjà dans un clan");
                return false;
            }

            String clan = playerManager.getClan();

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
            clanManager.addInvitation(target);
            return false;
        }

        /*
        * Join
         */

        if(args[0].equalsIgnoreCase("join")){
            if(args.length != 2) {
                player.sendMessage("§c/clan join <clan>");
                return false;
            }

            if(playerManager.inClan()) {
                player.sendMessage("§cVous êtes déjà dans un clan");
                return false;
            }

            String clan = args[1];
            if(!clanManager.hasInvitation(player)) {
                player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
                return false;
            }

            clanManager.removeInvitation(player);
            clanManager.addMember(player);

            player.sendMessage("§aVous avez rejoint le clan " + clan);
            Player player1 = Bukkit.getPlayer(clanManager.getOwner());
            if(player1 != null) {
                player1.sendMessage("§a" + player.getName() + " a accepté votre invitation");
            }
            return false;
        }

        /*
        * Refuse
         */

        if(args[0].equalsIgnoreCase("refuse")){
            if(args.length != 2) {
                player.sendMessage("§c/clan refuse <clan>");
                return false;
            }

            if(playerManager.inClan()) {
                player.sendMessage("§cVous êtes déjà dans un clan");
                return false;
            }

            String clan = args[1];
            if(!clanManager.hasInvitation(player)) {
                player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
                return false;
            }

            clanManager.removeInvitation(player);
            player.sendMessage("§cVous avez refusé l'invitation du clan " + clan);
            Player player1 = Bukkit.getPlayer(clanManager.getOwner());
            if(player1 != null) {
                player1.sendMessage("§c" + player.getName() + " a refusé votre invitation");
            }
            return false;
        }

        /*
        * Quit
         */

        if(args[0].equalsIgnoreCase("quit")){
            if(!playerManager.inClan()) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            ClanUI clanUI = new ClanUI(player, main, playerManager.getClan());
            clanUI.quitClanUi();
            return false;
        }

        /*
        * Claim
         */

        if(args[0].equalsIgnoreCase("claim")){
            if(!playerManager.inClan()) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            Chunk chunk = player.getLocation().getChunk();
            if(main.getChunkManager().isClaimed(chunk)) {
                player.sendMessage("§cCe chunk est déjà claim");
                return false;
            }

            int claimsMax = clanManager.getClanMaxClaims();
            int claims = clanManager.getClaims().size();
            if(claims >= claimsMax) {
                player.sendMessage("§cVous avez atteint le nombre maximum de claims");
                return false;
            }
            main.getChunkManager().claimChunk(player, playerManager.getClan());
            player.sendMessage("§a§l» §7Vous avez claim ce chunk avec succès");
            return false;
        }

        /*
        * Unclaim
         */

        if(args[0].equalsIgnoreCase("unclaim")){
            if(!playerManager.inClan()) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            Chunk chunk = player.getLocation().getChunk();
            if(!main.getChunkManager().isClaimed(chunk)) {
                player.sendMessage("§cCe chunk n'est pas encore claim");
                return false;
            }

            String claimer = main.getChunkManager().getClaimer(chunk);
            String clan = playerManager.getClan();
            if(!claimer.equals(clan)) {
                player.sendMessage("§cCe chunk n'est pas claim par votre clan");
                return false;
            }

            player.sendMessage("§c§l» §7Vous avez unclaim ce chunk avec succès");
            main.getChunkManager().unClaimChunk(player);
        }

        /*
        * Unclaimall
         */

        if(args[0].equalsIgnoreCase("unclaimall")){
            if(!playerManager.inClan()) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            String ownerId = clanManager.getOwner();
            String playerId = player.getUniqueId().toString();

            if(!ownerId.equals(playerId)) {
                player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
                return false;
            }

            main.getChunkManager().removeAllClaimsForClan(playerManager.getClan());
            clanManager.removeAllClaims();

            player.sendMessage("§c§l» §7Vous avez unclaim tous les chunks de votre clan");
        }

        /*
        * Ally
         */

        if(args[0].equalsIgnoreCase("ally")){
            if(args.length != 2) {
                player.sendMessage("§c/clan ally <clan>");
                return false;
            }

            if(!playerManager.inClan()) {
                player.sendMessage("§cVous n'êtes pas dans un clan");
                return false;
            }

            String ownerId = clanManager.getOwner();
            String playerId = player.getUniqueId().toString();

            if(!ownerId.equals(playerId)) {
                player.sendMessage("§cVous n'êtes pas le propriétaire de ce clan");
                return false;
            }

            if(!clanManager.clanExists(args[1])) {
                player.sendMessage("§cCe clan n'existe pas");
                return false;
            }

            if(clanManager.getAllies().contains(args[1])) {
                player.sendMessage("§cVous n'êtes plus alliés avec: " + args[1]);
                clanManager.removeAlly(args[1]);
                return false;
            }

            player.sendMessage("§aVous êtes désormais alliés avec: " + args[1]);
            clanManager.addAlly(args[1]);
            return false;
        }

        /*
        * Create
         */

        if(args[0].equalsIgnoreCase("create")){
            if(args.length != 1){
                player.sendMessage("§c/clan create");
                return false;
            }

            if(playerManager.inClan()) {
                player.sendMessage("§cVous êtes déjà dans un clan");
                return false;
            }

            CreateUI createUI = new CreateUI(player, main);
            createUI.openCreateUI();
        }

        /*
        * Fly
         */

        if(args[0].equalsIgnoreCase("fly")){

            if(!sender.hasPermission("clans.flyclaims")){
                sender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande !");
                return true;
            }

            if(!main.getChunkManager().isClaimed(player.getLocation().getChunk())){
                sender.sendMessage("§cVous ne pouvez pas activer le fly dans une zone non claim !");
                return true;
            }

            String claimer = main.getChunkManager().getClaimer(player.getLocation().getChunk());
            String playerClan = playerManager.getClan();

            if(!claimer.equals(playerClan)){
                sender.sendMessage("§cVous ne pouvez pas activer le fly dans une zone claim par un autre clan !");
                return true;
            }

            if(playerManager.isFly()) {
                player.setAllowFlight(false);
                playerManager.removeFly();
                player.sendMessage("§e§lErased§6§lClans §7» §eVous ne pouvez plus voler dans vos claims");
            } else {
                player.setAllowFlight(true);
                playerManager.addFly();
                player.sendMessage("§e§lErased§6§lClans §7» §eVous pouvez désormais voler dans vos claims");
            }

            return false;

        }

        /*
        * Chest
         */

        if(args[0].equalsIgnoreCase("chest")){
            if(playerManager.getClan().equals("null")){
                sender.sendMessage("§cVous n'êtes pas dans un clan !");
                return true;
            }

            ChestManager chestManager = new ChestManager(main, player);
            chestManager.openChest();
            return false;
        }

        return false;
    }

}
