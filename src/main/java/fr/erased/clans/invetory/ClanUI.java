package fr.erased.clans.invetory;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.IntUtils;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class ClanUI {

    private final Player player;
    private final ErasedClans main;
    private final String name;

    private final ClanManager clanManager;

    public ClanUI(Player player, ErasedClans main, String name) {
        this.main = main;
        this.player = player;
        this.name = name;

        this.clanManager = new ClanManager(main, name);
    }

    public void openClanUI() {
        Inventory inv = Bukkit.createInventory(null, 45, "Clan: " + name + " [" + clanManager.getMembers().size() + "/" + clanManager.getClanMaxMembers() + "]");

        PlayerManager playerManager = new PlayerManager(main, player);
        String ownerId = clanManager.getOwner();
        String owner = new PlayerManager(main, UUID.fromString(ownerId)).getName();

        int exp = clanManager.getClanXp();
        ExpUtils expUtils = new ExpUtils(exp);

        int percent = expUtils.getPercent();
        String progressBar = IntUtils.getProgressBar(percent);

        String nick;
        if (Bukkit.getPlayer(owner) != null) {
            nick = "§a" + owner + " §a§l✓";
        } else {
            nick = "§c" + owner + " §c✘";
        }

        inv.setItem(13, new ItemBuilder(Material.NAME_TAG).setDisplayName("§e" + name).setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Statut: §3" + clanManager.getClanStatus().getFormattedName(),
                "§8▪ §7Chef: §3" + owner,
                "§8▪ §7Grade: §3" + playerManager.getPlayerRankString(),
                "§8▪ §7Membres: §b" + clanManager.getMembers().size(),
                "§8▪ §7Niveau: §e" + expUtils.getLevel() + " §7(" + expUtils.getActualExp() + "/" + expUtils.getActualExpToNextLevel() + ")",
                "  " + progressBar + " §8" + percent + "%"
        )).build(false));

        inv.setItem(21, new ItemBuilder(Material.BOOK).setDisplayName("§6Quêtes").setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Réalisez des quêtes pour gagner",
                "  §7de l'expérience et gagner des récompenses.",
                " ",
                "§6▸ §eClique pour t'y rendre"
        )).build(false));
        inv.setItem(22,  new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3)
                .setSkullOwner(owner)
                .setDisplayName("§7Chef: " + nick).build(true));
        inv.setItem(23, new ItemBuilder(Material.CHEST).setDisplayName("§eCoffre de clan").setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Stockez les objets que vous souhaitez",
                "  §7et partagez les avec votre clan dans",
                "  §7un endroit sûr et sécurisé.",
                " ",
                "§6▸ §eClique pour t'y rendre"
        )).build(false));

        inv.setItem(41, new ItemBuilder(Material.BARRIER).setDisplayName("§8» §cQuitter le clan").build(false));
        inv.setItem(39, new ItemBuilder(Material.ARROW).setDisplayName("§8» §cRetour").build(false));

        ItemStack vitre = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(" ").build(false);
        for (int i : IntUtils.getCorners(inv)){
            inv.setItem(i, vitre);
        }

        player.openInventory(inv);
    }

    public void quitClanUi() {
        Inventory inv = Bukkit.createInventory(null, 27, "Quitter le clan " + name + " ?");

        inv.setItem(12, new ItemBuilder(Material.LIME_TERRACOTTA).setDisplayName("§aQuitter le clan").build(false));
        inv.setItem(14, new ItemBuilder(Material.RED_TERRACOTTA).setDisplayName("§cAnnuler").build(false));

        player.openInventory(inv);
    }
}
