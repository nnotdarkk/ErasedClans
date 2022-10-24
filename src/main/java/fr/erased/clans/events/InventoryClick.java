package fr.erased.clans.events;

import fr.erased.clans.Main;
import fr.erased.clans.quests.QuestsUI;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import fr.erased.clans.ui.ClanUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private final Main main;
    private ClanManager clanManager;

    public InventoryClick(Main main) {
        this.main = main;
        clanManager = new ClanManager(main);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) e.getWhoClicked();
        PlayerManager playerManager = new PlayerManager(main);

        if(e.getView().getTitle() == "Créer un clan"){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage("§aEcrivez le nom de votre clan dans le chat §7('cancel' pour annuler)");
                    new PlayerManager(main).setState(player, true);
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
                    player.closeInventory();
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main);
                    clanUI.quitClanUi(playerManager.getClan(player));
                    break;

                case BOOK:
                    player.closeInventory();
                    QuestsUI questsUI= new QuestsUI(main, player);
                    questsUI.openEasyQuestsUI(playerManager.getClan(player));
                    break;
            }
        }

        if(e.getView().getTitle().startsWith("Quitter le clan")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage("§cVous avez quitté le clan " + playerManager.getClan(player));
                    clanManager.removeMember(playerManager.getClan(player), player);
                    playerManager.unregisterClan(player.getUniqueId().toString());
                    break;
                case RED_TERRACOTTA:
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main);
                    clanUI.openClanUI(playerManager.getClan(player));
                    break;

            }
        }

        if(e.getView().getTitle().startsWith("Quêtes:")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case ARROW:
                    e.setCancelled(true);
                    player.closeInventory();
                    ClanUI clanUI = new ClanUI((Player) e.getWhoClicked(), main);
                    clanUI.openClanUI(playerManager.getClan(player));
                    break;
            }
        }
    }
}
