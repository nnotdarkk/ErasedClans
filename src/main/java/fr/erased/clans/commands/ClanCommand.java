package fr.erased.clans.commands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ChestManager;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.enums.PlayerRank;
import fr.erased.clans.storage.PlayerManager;
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
                new ClanUI(player, main, playerManager.getClan()).openClanUI();
                return false;
            }

            new CreateUI(player, main).openCreateUI();
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

            PlayerManager targetManager = new PlayerManager(main, target);

            if(targetManager.inClan()){
                player.sendMessage("§cCe joueur est déjà dans un clan");
                return false;
            }

            int maxMembers = clanManager.getClanMaxMembers();
            int members = clanManager.getMembers().size();
            if(members >= maxMembers) {
                player.sendMessage("§cVotre clan est plein");
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
            if(!clanManager.hasInvitation(player, clan)) {
                player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
                return false;
            }

            clanManager.removeInvitation(player);

            ClanManager manager = new ClanManager(main, clan);
            manager.addMember(player);

            player.sendMessage("§aVous avez rejoint le clan " + clan);
            Player player1 = Bukkit.getPlayer(manager.getOwner());
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
            if(!clanManager.hasInvitation(player, clan)) {
                player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
                return false;
            }

            ClanManager manager = new ClanManager(main, clan);
            manager.removeInvitation(player);
            player.sendMessage("§cVous avez refusé l'invitation du clan " + clan);
            Player player1 = Bukkit.getPlayer(manager.getOwner());
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
            if(playerManager.getClan().equals("null")){
                sender.sendMessage("§cVous n'êtes pas dans un clan !");
                return true;
            }

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

            if(playerManager.getPlayerRank().equals(PlayerRank.RECRUE)){
                sender.sendMessage("§cGrade membre nécessaire dans le clan.");
                return true;
            }

            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.getOpenInventory().getTitle().equals("Coffre du clan: " + playerManager.getClan())){
                    sender.sendMessage("§cVotre coffre de clan est déjà ouvert par un autre membre du clan.");
                    return true;
                }
            }
            ChestManager chestManager = new ChestManager(main, player);
            chestManager.openChest();
            return false;
        }

        /*
        * Base
         */

        if(args[0].equalsIgnoreCase("base")){
            if(playerManager.getClan().equals("null")){
                sender.sendMessage("§cVous n'êtes pas dans un clan !");
                return true;
            }

            if(clanManager.getClanBase() == null){
                sender.sendMessage("§cVotre clan n'a pas encore de base !");
                return true;
            }

            player.teleport(clanManager.getClanBase());
            return false;
        }

        /*
        * Setbase
         */

        if(args[0].equalsIgnoreCase("setbase")){
            if(playerManager.getClan().equals("null")){
                sender.sendMessage("§cVous n'êtes pas dans un clan !");
                return true;
            }

            String ownerId = clanManager.getOwner();
            String playerId = player.getUniqueId().toString();

            if(!ownerId.equals(playerId)) {
                player.sendMessage("§cVous n'avez pas la permission de changer de base de clan");
                return false;
            }

            if(clanManager.getClanLevel() < 20){
                player.sendMessage("§cVous devez être niveau 20 pour définir une base de clan");
                return false;
            }

            clanManager.setClanBase(player.getLocation());
            player.sendMessage("§a§l» §7Vous avez défini la base de votre clan avec succès");
            return false;
        }

        /*
        * Promote
         */

        if(args[0].equalsIgnoreCase("promote")){
            if(playerManager.getClan().equals("null")){
                sender.sendMessage("§cVous n'êtes pas dans un clan !");
                return true;
            }

            String ownerId = clanManager.getOwner();
            String playerId = player.getUniqueId().toString();

            if(!ownerId.equals(playerId)) {
                player.sendMessage("§cVous n'avez pas la permission de promouvoir un membre");
                return false;
            }

            if(args.length != 2){
                player.sendMessage("§c/clan promote <joueur>");
                return false;
            }

            if(player.getName().equals(args[1])){
                player.sendMessage("§cVous ne pouvez pas vous promouvoir");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                player.sendMessage("§cCe joueur n'est pas connecté");
                return false;
            }

            PlayerManager targetManager = new PlayerManager(main, target);

            if(!targetManager.getClan().equals(playerManager.getClan())){
                player.sendMessage("§cCe joueur n'est pas dans votre clan");
                return false;
            }

            switch (targetManager.getPlayerRank()){
                case CHEF:
                    return false;
                case OFFICIER:
                    player.sendMessage("§cCe joueur est déjà officier");
                    return false;
                case MEMBRE:
                    targetManager.setPlayerRank(PlayerRank.OFFICIER);
                    player.sendMessage("§a§l» §7Vous avez promu §e" + target.getName() + " §7en officier");
                    target.sendMessage("§a§l» §7Vous avez été promu officier par §e" + player.getName());
                    return false;
                case RECRUE:
                    targetManager.setPlayerRank(PlayerRank.MEMBRE);
                    player.sendMessage("§a§l» §7Vous avez promu §e" + target.getName() + " §7en membre");
                    target.sendMessage("§a§l» §7Vous avez été promu membre par §e" + player.getName());
                    return false;
            }
        }

        /*
        * Demote
         */

        if(args[0].equalsIgnoreCase("demote")){
            if(playerManager.getClan().equals("null")){
                sender.sendMessage("§cVous n'êtes pas dans un clan !");
                return true;
            }

            String ownerId = clanManager.getOwner();
            String playerId = player.getUniqueId().toString();

            if(!ownerId.equals(playerId)) {
                player.sendMessage("§cVous n'avez pas la permission de dé-promouvoir un membre");
                return false;
            }

            if(args.length != 2){
                player.sendMessage("§c/clan demote <joueur>");
                return false;
            }

            if(player.getName().equals(args[1])){
                player.sendMessage("§cVous ne pouvez pas vous dé-promouvoir");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                player.sendMessage("§cCe joueur n'est pas connecté");
                return false;
            }

            PlayerManager targetManager = new PlayerManager(main, target);

            if(!targetManager.getClan().equals(playerManager.getClan())){
                player.sendMessage("§cCe joueur n'est pas dans votre clan");
                return false;
            }

            switch (targetManager.getPlayerRank()){
                case CHEF:
                    return false;
                case OFFICIER:
                    targetManager.setPlayerRank(PlayerRank.MEMBRE);
                    player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target.getName() + " §7en membre");
                    target.sendMessage("§a§l» §7Vous avez été dé-promu membre par §e" + player.getName());
                    return false;
                case MEMBRE:
                    targetManager.setPlayerRank(PlayerRank.RECRUE);
                    player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target.getName() + " §7en recrue");
                    target.sendMessage("§a§l» §7Vous avez été dé-promu recrue par §e" + player.getName());
                    return false;
                case RECRUE:
                    player.sendMessage("§cCe joueur est déjà recrue");
                    return false;
            }
        }

        return false;
    }

}
