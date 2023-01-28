package fr.erased.clans.ui;

import fr.erased.clans.Main;
import fr.erased.clans.utils.CustomHead;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ClassementUI {

    private final Main main;
    private final Player player;

    public ClassementUI(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    public void openClassementUI(){
        Inventory inv = Bukkit.createInventory(null, 54, "Classement des clans");

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

        inv.setItem(50, quit);
        inv.setItem(48, retour);

        int i = main.getClanManager().getClansCount();
        String clan1 = main.getClanManager().getAllXpClansToList().get(i-1);
        String clan2 = main.getClanManager().getAllXpClansToList().get(i-2);
        String clan3 = main.getClanManager().getAllXpClansToList().get(i-3);

        String uuid1 = main.getClanManager().getOwner(clan1);
        String chef1 = main.getPlayerManager().getNameByUUID(uuid1);

        ItemStack first = new CustomHead().create("§b§l#1 §7- §b" + clan1, Arrays.asList(
                "",
                "§8▪ §7Niveau: §e" + main.getClanManager().getClanLevel(clan1),
                "§8▪ §7XP: §3" + main.getClanManager().getClanXp(clan1) + "§7/§3" + main.getClanManager().getClanExpToNextLevel(clan1),
                "§8▪ §7Chef: §b" + chef1,
                "§8▪ §7Membres: §e",
                ""
        ), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJlYzMwZTM5Y2FkNzMxNjM5YTc4OGEzMjFhMThmMWY3ZjlmODcwMmQxMTA1MDMwN2RjZjNiZWNlNmQ4YjQ3ZCJ9fX0=");


        player.openInventory(inv);
    }
}
