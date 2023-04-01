package fr.erased.clans.ui;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ClanUI {

    private final Player player;
    private final Main main;
    private final String name;

    private final ClanManager clanManager;

    public ClanUI(Player player, Main main, String name) {
        this.main = main;
        this.player = player;
        this.name = name;

        this.clanManager = new ClanManager(main, name);
    }

    public void openClanUI(){
        Inventory inv = Bukkit.createInventory(null, 54, "Clan: " + name + " [" + clanManager.getMembers().size() + "/"+ clanManager.getClanMaxMembers() +"]");

        PlayerManager playerManager = new PlayerManager(main, player);
        String ownerid = clanManager.getOwner();
        String owner = new PlayerManager(main, ownerid).getName();

        int exp = clanManager.getClanXp();
        ExpUtils expUtils = new ExpUtils(exp);

        int percent = expUtils.getPercent();

        StringBuilder p = new StringBuilder();
        p.append("§8[");
        for (int i = 0; i < 19; i++) {
            if (i < (percent / 5)) {
                p.append("§a▮");
            } else {
                p.append("§7▮");
            }
        }
        p.append("§8]");

        ItemStack clan = new ItemBuilder(Material.NAME_TAG).setDisplayName("§e" + name).setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Statut: §3" + clanManager.getClanStatus().getFormattedName(),
                "§8▪ §7Chef: §3" + owner,
                "§8▪ §7Grade: §3" + playerManager.getPlayerRankString(),
                "§8▪ §7Membres: §b" + clanManager.getMembers().size(),
                "§8▪ §7Niveau: §e" + expUtils.getLevel() + " §7(" + expUtils.getActualExp() + "/" + expUtils.getActualExpToNextLevel() + ")",
                "  " + p + " §8" + percent + "%"
        )).build(false);

        String nick;
        if(Bukkit.getPlayer(owner) != null){
            nick = "§a" + owner + " §a§l✓";
        } else {
            nick = "§c" + owner + " §c✘";
        }

        ItemStack chef = new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner(owner).setDisplayName("§7Chef: " + nick).build(true);

        ItemStack quests = new ItemBuilder(Material.BOOK).setDisplayName("§6Quêtes").setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Réalisez des quêtes pour gagner",
                "  §7de l'expérience et gagner des récompenses.",
                " ",
                "§6▸ §eClique pour t'y rendre"
        )).build(false);

        ItemStack chest = new ItemBuilder(Material.CHEST).setDisplayName("§eCoffre de clan").setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Stockez les objets que vous souhaitez",
                "  §7et partagez les avec votre clan dans",
                "  §7un endroit sûr et sécurisé.",
                " ",
                "§6▸ §eClique pour t'y rendre"
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
        inv.setItem(22, clan);
        inv.setItem(31, chef);
        inv.setItem(32, chest);

        inv.setItem(50, quit);
        inv.setItem(48, retour);
        player.openInventory(inv);
    }

    public void quitClanUi(){
        Inventory inv = Bukkit.createInventory(null, 27, "Quitter le clan " + name + " ?");

        ItemStack create = new ItemBuilder(Material.LIME_TERRACOTTA).setDisplayName("§aQuitter le clan").build(false);
        ItemStack cancel = new ItemBuilder(Material.RED_TERRACOTTA).setDisplayName("§cAnnuler").build(false);

        inv.setItem(12, create);
        inv.setItem(14, cancel);

        player.openInventory(inv);
    }
}
