package fr.erased.clans.quests;

import fr.erased.clans.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class QuestsLiseners implements Listener {

    private final Main main;

    public QuestsLiseners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(BlockPlaceEvent e){
        Block block = e.getBlock();
        if(block.getType().equals(Material.SUGAR_CANE)){
            main.getBlocksManager().addBlock(block.getLocation());
        }
    }

    @EventHandler
    public void event1(BlockBreakEvent e){
        Block block = e.getBlock();
        if(block.getType().equals(Material.SUGAR_CANE)){
            if(main.getBlocksManager().containsBlock(block.getLocation())){
                main.getBlocksManager().removeBlock(block.getLocation());
            }
        }
    }
    /*

     Easy

     */

    @EventHandler
    public void event(SmithItemEvent e){
        if(e.getCurrentItem().getType() == Material.NETHERITE_HOE){
            e.getWhoClicked().sendMessage("test10");
        }
    }

    @EventHandler
    public void event2(BlockBreakEvent e){
        Block block = e.getBlock();
        if(block.getType() == Material.SUGAR_CANE){
            if(main.getBlocksManager().containsBlock(block.getLocation())){
                return;
            }
            e.getPlayer().sendMessage("test1");
        }
    }
    /*

     Medium

     */

    /*

     Hard

     */
}
