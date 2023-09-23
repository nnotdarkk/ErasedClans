package fr.erased.clans.manager;

import fr.erased.clans.ErasedClans;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class ChunkManager {

    private final ErasedClans main;
    private final YamlConfiguration f;

    public ChunkManager(ErasedClans main) {
        this.main = main;

        if (!main.getFileManager().getFile("chunk", "chunks").exists()) {
            main.getFileManager().createFile("chunk", "chunks");
        }

        f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("chunk", "chunks"));
    }

    private Chunk getChunk(Player player) {
        return player.getLocation().getChunk();
    }

    public String getClaimer(Chunk chunk) {
        return f.getString(chunk.toString());
    }

    public boolean isClaimed(Chunk chunk) {
        return f.contains(chunk.toString());
    }

    public void claimChunk(Player player, String clan) {
        Chunk chunk = getChunk(player);

        f.set(chunk.toString(), clan);

        saveFile();

        new ClanManager(main, clan).addClaim(chunk);
    }

    public void unClaimChunk(Player player) {
        Chunk chunk = getChunk(player);
        String clan = getClaimer(chunk);

        new ClanManager(main, clan).removeClaim(chunk);

        f.set(chunk.toString(), null);

        saveFile();
    }

    public void removeAllClaimsForClan(String clan) {

        YamlConfiguration f1 = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));

        List<String> chunks = f1.getStringList("claims");

        for (String chunk : chunks) {
            f.set(chunk, null);
        }

        saveFile();
    }

    public void saveFile() {
        try {
            f.save(main.getFileManager().getFile("chunk", "chunks"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
