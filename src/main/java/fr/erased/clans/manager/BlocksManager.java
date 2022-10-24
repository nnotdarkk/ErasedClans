package fr.erased.clans.manager;

import fr.erased.clans.Main;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class BlocksManager implements Listener {

    private final Main main;

    @SneakyThrows
    public BlocksManager(Main main) {
        this.main = main;

        if(!main.getFileManager().getFile("blocks").exists()){
            main.getFileManager().createFile("blocks");
            YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("blocks"));
            List<Location> blocks = new ArrayList<>();
            f.set("blocks", blocks);
            f.save(main.getFileManager().getFile("blocks"));
        }
    }

    public List<Location> getBlocks(){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("blocks"));
        List<Location> blocks = (List<Location>) f.getList("blocks");
        return blocks;
    }

    @SneakyThrows
    public void addBlock(Location block){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("blocks"));
        List<Location> blocks = getBlocks();
        blocks.add(block);
        f.set("blocks", blocks);
        f.save(main.getFileManager().getFile("blocks"));
    }

    @SneakyThrows
    public void removeBlock(Location block){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("blocks"));
        List<Location> blocks = getBlocks();
        blocks.remove(block);
        f.set("blocks", blocks);
        f.save(main.getFileManager().getFile("blocks"));
    }

    public void clearFile(){
        main.getFileManager().removeFile("blocks");
    }

    public boolean containsBlock(Location block){
        return getBlocks().contains(block);
    }
}
