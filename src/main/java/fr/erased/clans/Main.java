package fr.erased.clans;

import fr.erased.clans.commands.ClanCommand;
import fr.erased.clans.commands.ClanTabComplete;
import fr.erased.clans.listeners.*;
import fr.erased.clans.listeners.MoveListener;
import fr.erased.clans.storage.*;
import fr.erased.clans.utils.FileUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private FileUtils fileManager;

    @Getter
    private ChunkManager chunkManager;

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerConnection(this), this);
        pm.registerEvents(new InventoryClick(this), this);
        pm.registerEvents(new AsyncPlayerChat(this), this);
        pm.registerEvents(new ClaimsCancels(this), this);
        pm.registerEvents(new MoveListener(this), this);
        pm.registerEvents(new ClanChestInteract(this), this);

        PluginCommand clan = getCommand("clan");
        clan.setExecutor(new ClanCommand(this));
        clan.setTabCompleter(new ClanTabComplete());

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ClanPlaceholders(this).register();
        }

        fileManager = new FileUtils(this);
        fileManager.createFolder("clans");
        fileManager.createFolder("userdata");
        fileManager.createFolder("chunk");

        chunkManager = new ChunkManager(this);
    }
}
