package fr.erased.clans;

import fr.erased.clans.commands.Clan;
import fr.erased.clans.commands.TabClan;
import fr.erased.clans.discord.TaskDiscord;
import fr.erased.clans.events.*;
import fr.erased.clans.fly.FlyListeners;
import fr.erased.clans.manager.*;
import fr.erased.clans.quests.QuestsLiseners;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private FileManager fileManager;

    @Getter
    private PlayerManager playerManager;

    @Getter
    private ClanManager clanManager;

    @Getter
    private ChunkManager chunkManager;

    @Getter
    private BlocksManager blocksManager;

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new InventoryClick(this), this);
        pm.registerEvents(new AsyncPlayerChat(this), this);
        pm.registerEvents(new PlayerClaims(this), this);
        pm.registerEvents(new FlyListeners(this), this);
        pm.registerEvents(new QuestsLiseners(this), this);
        pm.registerEvents(new ClanChestInteract(this), this);

        PluginCommand command = this.getCommand("clan");
        command.setExecutor(new Clan(instance));
        command.setTabCompleter(new TabClan());

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        fileManager = new FileManager(this);
        fileManager.createFolder("clans");
        fileManager.createFolder("userdata");
        fileManager.createFolder("chunk");

        blocksManager = new BlocksManager(this);
        playerManager = new PlayerManager(this);
        clanManager = new ClanManager(this);
        chunkManager = new ChunkManager(this);

        TaskDiscord taskDiscord = new TaskDiscord(this);
        taskDiscord.runTaskTimerAsynchronously(this, 0, 20L * 60L * 15L);
    }

    @Override
    public void onDisable() {
        blocksManager.clearFile();
    }
}
