package fr.erased.clans;

import fr.erased.clans.commands.ClanCommand;
import fr.erased.clans.commands.ClanCompleter;
import fr.erased.clans.commands.subcommands.*;
import fr.erased.clans.listeners.*;
import fr.erased.clans.listeners.MoveListener;
import fr.erased.clans.storage.*;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.CommandFramework;
import lombok.Getter;
import org.bukkit.Bukkit;
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

        CommandFramework framework = new CommandFramework(this);

        framework.registerCommands(new ClanCommand(this));
        framework.registerCommands(new ClanCompleter());

        framework.registerCommands(new BaseCommand(this));
        framework.registerCommands(new ChestCommand(this));
        framework.registerCommands(new ClaimCommand(this));
        framework.registerCommands(new CreateCommand(this));
        framework.registerCommands(new DemoteCommand(this));
        framework.registerCommands(new FlyCommand(this));
        framework.registerCommands(new InviteCommand(this));
        framework.registerCommands(new JoinCommand(this));
        framework.registerCommands(new PromoteCommand(this));
        framework.registerCommands(new QuitCommand(this));
        framework.registerCommands(new RefuseCommand(this));
        framework.registerCommands(new SetbaseCommand(this));
        framework.registerCommands(new UnclaimallCommand(this));
        framework.registerCommands(new UnclaimCommand(this));

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
