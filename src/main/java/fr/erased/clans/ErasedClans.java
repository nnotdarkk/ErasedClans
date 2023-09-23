package fr.erased.clans;

import fr.erased.clans.commands.ClanCommand;
import fr.erased.clans.commands.ClanCompleter;
import fr.erased.clans.commands.subcommands.*;
import fr.erased.clans.commands.subcommands.admin.*;
import fr.erased.clans.listeners.*;
import fr.erased.clans.manager.ChunkManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.CommandFramework;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ErasedClans extends JavaPlugin {

    private static ErasedClans instance;
    private FileUtils fileManager;
    private ChunkManager chunkManager;

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
        framework.registerCommands(new SetBaseCommand(this));
        framework.registerCommands(new UnclaimAllCommand(this));
        framework.registerCommands(new UnclaimCommand(this));
        framework.registerCommands(new HelpCommand(this));

        framework.registerCommands(new TeleportBaseCommand(this));
        framework.registerCommands(new PlayerInfoCommand(this));
        framework.registerCommands(new ClanInfoCommand(this));

        framework.registerCommands(new ForceClaimCommand(this));
        framework.registerCommands(new ForceDemoteCommand(this));
        framework.registerCommands(new ForceJoinCommand(this));
        framework.registerCommands(new ForceOpenChestCommand(this));
        framework.registerCommands(new ForcePromoteCommand(this));
        framework.registerCommands(new ForceSetBaseCommand(this));
        framework.registerCommands(new ForceSetBaseCommand(this));
        framework.registerCommands(new ForceUnclaimAllCommand(this));
        framework.registerCommands(new ForceUnclaimCommand(this));
        framework.registerCommands(new BypassClaimsCommand(this));
        framework.registerCommands(new SetClanXpCommand(this));
        framework.registerCommands(new AddClanXpCommand(this));
        framework.registerCommands(new ForceSetLeadCommand(this));

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ClanPlaceholders(this).register();
        }

        fileManager = new FileUtils(this);
        fileManager.createFolder("clans");
        fileManager.createFolder("userdata");
        fileManager.createFolder("chunk");

        chunkManager = new ChunkManager(this);
    }
}
