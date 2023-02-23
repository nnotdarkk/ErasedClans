package fr.erased.clans.storage;

import fr.erased.clans.Main;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class ChunkManager {

    private final Main main;

    public ChunkManager(Main main) {
        this.main = main;

        if(!main.getFileManager().getFile("chunk", "chunks").exists()){
            main.getFileManager().createFile("chunk", "chunks");
        }
    }

    private Chunk getChunk(Player player){
        return player.getLocation().getChunk();
    }

    public void claimChunk(Player player, String clan){
        Chunk chunk = getChunk(player);

        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("chunk", "chunks"));
        f.set(chunk.toString(), clan);

        try {
            f.save(main.getFileManager().getFile("chunk", "chunks"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new ClanManager(main, clan).addClaim(chunk);
    }

    public void unClaimChunk(Player player){
        Chunk chunk = getChunk(player);
        String clan = getClaimer(chunk);

        new ClanManager(main, clan).removeClaim(chunk);

        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("chunk", "chunks"));
        f.set(chunk.toString(), null);

        try {
            f.save(main.getFileManager().getFile("chunk", "chunks"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean isClaimed(Chunk chunk){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("chunk", "chunks"));
        return f.contains(chunk.toString());
    }

    public String getClaimer(Chunk chunk){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("chunk", "chunks"));
        return f.getString(chunk.toString());
    }

    public void removeAllClaimsForClan(String clan){

        YamlConfiguration f1 = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        YamlConfiguration f2 = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("chunk", "chunks"));

        List<String> chunks = f1.getStringList("claims");

        for(String chunk : chunks){
            f2.set(chunk, null);
        }

        try {
            f2.save(main.getFileManager().getFile("chunk", "chunks"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
