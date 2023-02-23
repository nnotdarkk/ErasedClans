package fr.erased.clans.storage.user;

import fr.erased.clans.Main;
import fr.erased.clans.storage.enums.PlayerRank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private final Main main;
    private final Player player;

    private static final List<Player> createState = new ArrayList<>();
    private static final List<Player> fly = new ArrayList<>();

    public PlayerManager(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    public PlayerManager(Main main, String uuid){
        this.main = main;
        this.player = main.getServer().getPlayer(uuid);
    }

    public void registerPlayer(){
        boolean exist = false;

        File file = new File(main.getDataFolder() + File.separator + "userdata" + File.separator + player.getUniqueId() + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
                exist = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(exist){
            FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));

            f.set("name", player.getName());
            f.set("clan", "null");
            f.set("rank", "null");
            try {
                f.save(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerClan(String name){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));

        f.set("clan", name);
        f.set("rank", PlayerRank.RECRUE.toString());
        try {
            f.save(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void unregisterClan(){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));

        f.set("clan", "null");
        f.set("rank", "null");
        try {
            f.save(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClan(){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        String clan = f.get("clan").toString();

        return clan;
    }

    public PlayerRank getPlayerRank(){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        String rank = f.getString("rank");

        return PlayerRank.valueOf(rank);
    }

    public void setPlayerRank(PlayerRank rank){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));

        f.set("rank", rank.toString());
        try {
            f.save(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName(){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        return f.get("name").toString();
    }

    public boolean inClan(){
        if(getClan().equals("null")){
            return false;
        } else {
            return true;
        }
    }

    public void addCreateState(){
        createState.add(player);
    }

    public void removeCreateState(){
        createState.remove(player);
    }

    public boolean isInCreateState(){
        return createState.contains(player);
    }

    public void addFly(){
        fly.add(player);
    }

    public void removeFly(){
        fly.remove(player);
    }

    public boolean isFly(){
        return fly.contains(player);
    }
}
