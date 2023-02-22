package fr.erased.clans.ui;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.user.PlayerManager;
import fr.erased.clans.utils.ItemBuilder;
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
    private final ClanManager clanManager;
    private final PlayerManager playerManager;
    private final Player player;

    public ClanUI(Player player, Main main) {
        this.main = main;
        clanManager = new ClanManager(main);
        playerManager = new PlayerManager(main);
        this.player = player;
    }

    public int percent(double currentValue, double maxValue){
        return (int) ((currentValue/maxValue) *100);
    }

    public void openClanUI(String name){
        Inventory inv = Bukkit.createInventory(null, 54, "Clan: " + name + " [" + clanManager.getMembers(name).size() + "/15]");

        String ownerid = clanManager.getOwner(name);
        String owner = main.getPlayerManager().getNameByUUID(ownerid);

        int percent = percent(clanManager.getClanXp(name), clanManager.getClanExpToNextLevel(name));

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

        List<String> allies = new ArrayList<>();

        if(clanManager.getAllies(name).size() == 0){
            allies.add("§cAucun");
        } else {
            for(String ally : clanManager.getAllies(name)){
                allies.add("§a" + ally);
            }
        }

        ItemStack clan = new ItemBuilder(Material.NAME_TAG).setDisplayName("§e" + name).setLoreWithList(Arrays.asList(
                " ",
                "§8▪ §7Statut: §3" + owner,
                "§8▪ §7Chef: §3" + owner,
                "§8▪ §7Membres: §b" + clanManager.getMembers(name).size(),
                "§8▪ §7Niveau: §e" + clanManager.getClanLevel(name),
                "  " + p + " §8" + percent + "%",
                "§8▪ §7Alliés:" + allies.toString().replaceAll("\\[", "").replaceAll("]", "")
        )).build(false);

        String nick;
        if(Bukkit.getPlayer(owner) != null){
            nick = "§a" + owner + " §a§l✓";
        } else {
            nick = "§c" + owner + " §c✘";
        }

        //String rank = new LuckPermsHandler(player_owner).getPrefix();
        /*String classe = "%mmocore_class%";
        classe = PlaceholderAPI.setPlaceholders(player, classe);*/
        ItemStack chef = new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner(owner).setDisplayName("§7Chef: " + nick).setLoreWithList(Arrays.asList(
                "",
                "§8▪ §7Grade: "/* +rank */,
                "§8▪ §7Classe: "/* + classe*/
        )).build(true);

        ItemStack quests = new ItemBuilder(Material.BOOK).setDisplayName("§6Quêtes").setLoreWithList(Arrays.asList(
                "",
                " ",
                "§6» §eClique pour y aller"
        )).build(false);

        ItemStack chest = new ItemBuilder(Material.CHEST).setDisplayName("§eCoffre de clan").setLoreWithList(Arrays.asList(
                "",
                "§8▪ Stockez les objets que vous souhaitez",
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
