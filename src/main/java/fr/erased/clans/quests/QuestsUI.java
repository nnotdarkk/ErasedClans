package fr.erased.clans.quests;

import fr.erased.clans.Main;
import fr.erased.clans.enums.QuestDifficulty;
import fr.erased.clans.ui.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class QuestsUI {

    private final Main main;

    private Player player;

    public QuestsUI(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    public void openEasyQuestsUI(String clan){
        Inventory inv = Bukkit.createInventory(null, 54, "Quêtes: §afaciles");

        ItemStack vitre = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build(false);
        ItemStack retour = new ItemBuilder(Material.ARROW).setDisplayName("§8» §cRetour").build(false);

        ItemStack quest1 = new ItemBuilder(Material.ZOMBIE_HEAD).setDisplayName("§eQuête 1").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3300 XP",
                "§8▪ §7Objectif: §eTuer 10 zombies",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 1 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 1 - 1) + "§8/§610"
        )).build(false);

        ItemStack quest2 = new ItemBuilder(Material.SUGAR_CANE).setDisplayName("§eQuête 2").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3400 XP",
                "§8▪ §7Objectif: §eRécuperer 250 cannes à sucre",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 2 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 2 - 1) + "§8/§6250"
        )).build(false);

        ItemStack quest3 = new ItemBuilder(Material.PUMPKIN).setDisplayName("§eQuête 3").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3450 XP",
                "§8▪ §7Objectif: §eRécuperer 50 citrouilles",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 3 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 3 - 1) + "§8/§650"
        )).build(false);

        ItemStack quest4 = new ItemBuilder(Material.ACACIA_LOG).setDisplayName("§eQuête 4").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3500 XP",
                "§8▪ §7Objectif: §eRécuperer 25 buches d'acacia",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 4 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 4 - 1) + "§8/§625"
        )).build(false);

        ItemStack quest5 = new ItemBuilder(Material.COAL_ORE).setDisplayName("§eQuête 5").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3600 XP",
                "§8▪ §7Objectif: §eRécuperer 15 minerais de charbon",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 5 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 5 - 1) + "§8/§615"
        )).build(false);

        ItemStack quest6 = new ItemBuilder(Material.CLAY).setDisplayName("§eQuête 6").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3650 XP",
                "§8▪ §7Objectif: §eRécuperer 20 argile",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 6 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 6 - 1) + "§8/§620"
        )).build(false);

        ItemStack quest7 = new ItemBuilder(Material.SAND).setDisplayName("§eQuête 6").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3800 XP",
                "§8▪ §7Objectif: §eRécuperer 300 sable",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 7 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 7 - 1) + "§8/§6300"
        )).build(false);

        ItemStack quest8 = new ItemBuilder(Material.SKELETON_SKULL).setDisplayName("§eQuête 8").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §3950 XP",
                "§8▪ §7Objectif: §eTuer 20 squelettes",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 8 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 8 - 1) + "§8/§620"
        )).build(false);

        ItemStack quest9 = new ItemBuilder(Material.JUKEBOX).setDisplayName("§eQuête 9").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §31050 XP",
                "§8▪ §7Objectif: §eCraftez une jukebox",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 9 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 9 - 1) + "§8/§61"
        )).build(false);

        ItemStack quest10 = new ItemBuilder(Material.NETHERITE_HOE).setDisplayName("§eQuête 9").setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Difficulté: §aFacile",
                "§8▪ §7Récompense: §31200 XP",
                "§8▪ §7Objectif: §eCraftez une houe en netherite",
                "§8▪ §7Statut: " + main.getClanManager().getStringQuestCompleted(clan, QuestDifficulty.Facile, 10 - 1),
                "§8▪ §7Progression: §6" + main.getClanManager().getQuestInt(clan, QuestDifficulty.Facile, 10 - 1) + "§8/§61"
        )).build(false);

        ItemStack easy = new ItemBuilder(Material.LIME_DYE).setDisplayName("§7Quêtes: §afaciles").setLoreWithList(Arrays.asList("", "§7Quêtes les plus simples", "§7rapportant peu d'xp", "", "§6» §eClique pour y aller")).build(false);
        ItemStack medium = new ItemBuilder(Material.ORANGE_DYE).setDisplayName("§7Quêtes: §6moyennes").setLoreWithList(Arrays.asList("", "§7Quêtes les intermédiaires", "§7rapportant de l'xp", "", "§6» §eClique pour y aller")).build(false);
        ItemStack hard = new ItemBuilder(Material.RED_DYE).setDisplayName("§7Quêtes: §cdifficiles").setLoreWithList(Arrays.asList("", "§7Quêtes les plus difficiles", "§7rapportant beaucoup d'xp", "", "§6» §eClique pour y aller")).build(false);


        inv.setItem(3, easy);
        inv.setItem(4, medium);
        inv.setItem(5, hard);
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

        inv.setItem(49, retour);

        inv.setItem(20, quest1);
        inv.setItem(21, quest2);
        inv.setItem(22, quest3);
        inv.setItem(23, quest4);
        inv.setItem(24, quest5);
        inv.setItem(29, quest6);
        inv.setItem(30, quest7);
        inv.setItem(31, quest8);
        inv.setItem(32, quest9);
        inv.setItem(33, quest10);

        player.openInventory(inv);
    }

    public void openMediumQuestsUI(){

    }

    public void openHardQuestsUI(){

    }
}
