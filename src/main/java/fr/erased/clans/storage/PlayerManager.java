package fr.erased.clans.storage;

import fr.erased.clans.Main;
import fr.erased.clans.storage.enums.PlayerRank;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final Main main;
    private final String uuid;

    private YamlConfiguration f;

    private static final List<String> createState = new ArrayList<>();
    private static final List<String> fly = new ArrayList<>();

    public PlayerManager(Main main, Player player) {
        this.main = main;
        this.uuid = player.getUniqueId().toString();

        if(main.getFileManager().getFile("userdata", uuid) != null){
            this.f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid));
            return;
        }

        this.f = null;
    }

    public PlayerManager(Main main, String uuid){
        this.main = main;
        this.uuid = uuid;

        if(main.getFileManager().getFile("userdata", uuid) != null){
            this.f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid));
            return;
        }

        this.f = null;
    }

    public void addFly(){
        fly.add(uuid);
    }
    public void removeFly(){
        fly.remove(uuid);
    }
    public boolean isFly(){
        return fly.contains(uuid);
    }

    public void addCreateState(){
        createState.add(uuid);
    }
    public void removeCreateState(){
        createState.remove(uuid);
    }
    public boolean isInCreateState(){
        return createState.contains(uuid);
    }

    public void registerPlayer(){
        File file = new File(main.getDataFolder() + File.separator + "userdata" + File.separator + uuid + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid));

            f.set("name", Bukkit.getPlayer(uuid).getName());
            f.set("clan", "null");
            f.set("rank", "null");
            saveFile();
        }
    }

    public String getClan(){
        return f.get("clan").toString();
    }

    public String getName(){
        return f.getString("name");
    }

    public PlayerRank getPlayerRank(){
        String rank = f.getString("rank");
        return PlayerRank.valueOf(rank);
    }

    public String getPlayerRankString(){
        return getPlayerRank().getFormattedName();
    }

    public void registerClan(String name){
        registerClan(name, PlayerRank.RECRUE);
    }

    public void registerClan(String name, PlayerRank rank){
        f.set("clan", name);
        f.set("rank", rank.toString());
        saveFile();
    }


    public void unregisterClan(){
        f.set("clan", "null");
        f.set("rank", "null");
        saveFile();
    }

    public void setPlayerRank(PlayerRank rank){
        f.set("rank", rank.toString());
        saveFile();
    }

    public boolean inClan(){
        if(getClan().equals("null")){
            return false;
        } else {
            return true;
        }
    }

    public void saveFile(){
        try {
            f.save(main.getFileManager().getFile("userdata", uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
