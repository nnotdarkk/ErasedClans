package fr.erased.clans.ui;

import fr.erased.clans.Main;
import fr.erased.clans.enums.QuestDifficulty;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClanUI {

    private final Main main;
    private ClanManager clanManager;
    private PlayerManager playerManager;
    private final Player player;

    public ClanUI(Player player, Main main) {
        this.main = main;
        clanManager = new ClanManager(main);
        playerManager = new PlayerManager(main);
        this.player = player;
    }

    public void openClanUI(String name){
        Inventory inv = Bukkit.createInventory(null, 54, "Clan: " + name + " [" + clanManager.getMembers(name).size() + "/15]");

        String ownerid = clanManager.getOwner(name);
        String owner = main.getPlayerManager().getNameByUUID(ownerid);
        ItemStack clan = new ItemBuilder(Material.NAME_TAG).setDisplayName("§e" + name).setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Tag: §6" + clanManager.getTag(name),
                "§8▪ §7Membres: §b" + clanManager.getMembers(name).size() + "§8/§b15",
                "§8▪ §7Chef: §3" + owner,
                "§8▪ §7Niveau: §a" + clanManager.getClanLevel(name),
                "§8▪ §7Expérience: §a" + clanManager.getClanXp(name) + "§8/§a" + clanManager.getClanExpToNextLevel(name)
        )).build(false);

        String nick;
        if(Bukkit.getPlayer(owner) != null){
            nick = "§a" + owner + " §a§l✓";
        } else {
            nick = "§c" + owner + " §c✘";
        }

        /*String rank = new LuckPermsHandler(player).getPrefix();*/
        /*String classe = "%mmocore_class%";
        classe = PlaceholderAPI.setPlaceholders(player, classe);*/
        ItemStack chef = new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner(owner).setDisplayName("§7Chef: " + nick).setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Grade: "/* + rank*/,
                "§8▪ §7Classe: "/* + classe*/
        )).build(true);

        List<String> allies = new ArrayList<>();

        if(clanManager.getAllies(name).size() == 0){
            allies.add("§cAucun");
        } else {
            for(String ally : clanManager.getAllies(name)){
                allies.add("§a" + ally);
            }
        }

        List<String> ennemy = new ArrayList<>();

        if(clanManager.getEnemies(name).size() == 0){
            ennemy.add("§cAucun");
        } else {
            for(String enemy : clanManager.getEnemies(name)){
                ennemy.add("§c" + enemy);
            }
        }

        ItemStack situation = new ItemBuilder(Material.SHIELD).setDisplayName("§7Relation:").setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Alliés:",
                " " + allies.toString().replaceAll("\\[", "").replaceAll("]", ""),
                " ",
                "§8▪ §7Ennemis:",
                " " + ennemy.toString().replaceAll("\\[", "").replaceAll("]", ""),
                " "
        )).build(false);

        ItemStack quests = new ItemBuilder(Material.BOOK).setDisplayName("§7Quêtes:").setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Quêtes faciles: §a" + clanManager.getQuestListCompleted(name, QuestDifficulty.Facile) +"§7/§a10",
                "§8▪ §7Quêtes normales: §e" + clanManager.getQuestListCompleted(name, QuestDifficulty.Moyen )+ "§7/§e10",
                "§8▪ §7Quêtes difficiles: §c" + clanManager.getQuestListCompleted(name, QuestDifficulty.Moyen) + "§7/§c10",
                " ",
                "§6» §eClique pour y aller"
        )).build(false);

        ItemStack vitre = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build(false);
        ItemStack quit = new ItemBuilder(Material.BARRIER).setDisplayName("§8» §cQuitter le clan").build(false);
        ItemStack retour = new ItemBuilder(Material.ARROW).setDisplayName("§8» §cRetour").build(false);

        inv.setItem(0, vitre);
        inv.setItem(1, vitre);
        inv.setItem(7, vitre);
        inv.setItem(8, vitre);
        inv.setItem(9, vitre);
        inv.setItem(17, vitre);
        inv.setItem(36, vitre);
        inv.setItem(44, vitre);
        inv.setItem(45, vitre);
        inv.setItem(46, vitre);
        inv.setItem(52, vitre);
        inv.setItem(53, vitre);

        inv.setItem(30, quests);
        inv.setItem(22, situation);
        inv.setItem(21, clan);
        inv.setItem(23, chef);

        inv.setItem(50, quit);
        inv.setItem(48, retour);
        player.openInventory(inv);
    }

    public void quitClanUi(String name){
        Inventory inv = Bukkit.createInventory(null, 27, "Quitter le clan " + name + " ?");

        ItemStack create = new ItemBuilder(Material.LIME_TERRACOTTA).setDisplayName("§aQuitter le clan").build(false);
        ItemStack cancel = new ItemBuilder(Material.RED_TERRACOTTA).setDisplayName("§cAnnuler").build(false);

        inv.setItem(12, create);
        inv.setItem(14, cancel);

        player.openInventory(inv);
    }
}
