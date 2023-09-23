package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.invetory.ClanUI;
import fr.erased.clans.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private final ErasedClans main;

    public InventoryClick(ErasedClans main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) e.getWhoClicked();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (e.getView().getTitle().equals("Créer un clan")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage(ChatUtils.getCenteredText("§7 "));
                    player.sendMessage(ChatUtils.getCenteredText("§6§lCRÉATION DE CLAN"));
                    player.sendMessage(ChatUtils.getCenteredText("§7Inscrivez le nom de votre clan dans le chat"));
                    player.sendMessage(ChatUtils.getCenteredText("§7('annuler' pour annuler)"));
                    player.sendMessage(ChatUtils.getCenteredText("§7 "));
                    playerManager.addCreateState();
                    break;

                case RED_TERRACOTTA:
                    player.closeInventory();
                    break;
            }
        }

        if (e.getView().getTitle().startsWith("Clan:")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case ARROW:
                    player.closeInventory();
                    break;

                case BARRIER:
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main, playerManager.getClan());
                    clanUI.quitClanUi();
                    break;

                case BOOK:
                    player.closeInventory();
                    player.sendMessage("§cCette fonctionnalité arrive bientôt !");
                    break;

                case CHEST:
                    player.closeInventory();
                    player.performCommand("clan chest");
                    break;
            }
        }

        if (e.getView().getTitle().startsWith("Quitter le clan")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage("§cVous avez quitté le clan " + playerManager.getClan());
                    clanManager.removeMember(player);
                    break;
                case RED_TERRACOTTA:
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main, playerManager.getClan());
                    clanUI.openClanUI();
                    break;

            }
        }

        if (e.getView().getTitle().startsWith("Quêtes:")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.ARROW) {
                e.setCancelled(true);
                player.closeInventory();
                ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main, playerManager.getClan());
                clanUI.openClanUI();
            }
        }
    }
}
