package fr.erased.clans.quests.blocks;

import fr.erased.clans.Main;
import fr.erased.clans.manager.FileManager;
import org.bukkit.event.Listener;

public class BlocksManager implements Listener {

    private final FileManager fileManager;
    private final Main main;

    public BlocksManager(Main main) {
        this.main = main;
        fileManager = new FileManager(main);
    }


}
