package fr.erased.clans;

import fr.erased.clans.commands.Clan;
import fr.erased.clans.listeners.*;
import fr.erased.clans.listeners.FlyListeners;
import fr.erased.clans.storage.*;
import fr.erased.clans.storage.user.PlayerManager;
import fr.erased.clans.utils.FileUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private FileUtils fileManager;

    @Getter
    private PlayerManager playerManager;

    @Getter
    private ClanManager clanManager;

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
        pm.registerEvents(new FlyListeners(this), this);
        pm.registerEvents(new ClanChestInteract(this), this);

        registerCommand("erasedclans", new Clan("clan",this));

        fileManager = new FileUtils(this);
        fileManager.createFolder("clans");
        fileManager.createFolder("userdata");
        fileManager.createFolder("chunk");

        playerManager = new PlayerManager(this);
        clanManager = new ClanManager(this);
        chunkManager = new ChunkManager(this);
    }

    public void registerCommand(String commandName, Command commandClass) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap)bukkitCommandMap.get(getServer());
            commandMap.register(commandName, commandClass);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }
}
