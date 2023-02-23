package fr.erased.clans.listeners;

import fr.erased.clans.Main;
import fr.erased.clans.ui.QuestsUI;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.user.PlayerManager;
import fr.erased.clans.ui.ClanUI;
import fr.erased.clans.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private final Main main;

    public InventoryClick(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) e.getWhoClicked();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(e.getView().getTitle().equals("Créer un clan")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case LIME_TERRACOTTA:
                    if(player.hasPermission("erasedclans.create")){
                        player.sendMessage("§cErreur: Grade vip requis pour créer un clan, vous pouvez tout de même en rejoindre un.");
                        return;
                    }
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

        if(e.getView().getTitle().startsWith("Clan:")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case ARROW:
                    player.closeInventory();
                    break;

                case BARRIER:
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main, playerManager.getClan());
                    clanUI.quitClanUi();
                    break;

                case BOOK:
                    QuestsUI questsUI= new QuestsUI(main, player);
                    //questsUI.openEasyQuestsUI(playerManager.getClan(player));
                    break;
            }
        }

        if(e.getView().getTitle().startsWith("Quitter le clan")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage("§cVous avez quitté le clan " + playerManager.getClan());
                    clanManager.removeMember(player);
                    playerManager.unregisterClan();
                    break;
                case RED_TERRACOTTA:
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main, playerManager.getClan());
                    clanUI.openClanUI();
                    break;

            }
        }

        if(e.getView().getTitle().startsWith("Quêtes:")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case ARROW:
                    e.setCancelled(true);
                    player.closeInventory();
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main, playerManager.getClan());
                    clanUI.openClanUI();
                    break;
            }
        }
    }
}
