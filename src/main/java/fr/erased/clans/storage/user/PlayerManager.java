package fr.erased.clans.storage.user;

import fr.erased.clans.Main;
import fr.erased.clans.storage.enums.PlayerRank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerManager {

    private final Main main;

    private static List<Player> createState = new ArrayList<>();
    private static List<Player> fly = new ArrayList<>();

    public PlayerManager(Main main) {
        this.main = main;
    }

    public void registerPlayer(Player player){
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

    public void registerClan(Player player, String name){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));

        f.set("clan", name);
        f.set("rank", PlayerRank.MEMBRE.toString());
        try {
            f.save(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void unregisterClan(String uuid){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid));

        f.set("clan", "null");
        f.set("rank", "null");
        try {
            f.save(main.getFileManager().getFile("userdata", uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClan(Player player){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        String clan = f.get("clan").toString();

        return clan;
    }

    public String getNameByUUID(String uuid){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid));
        String name = f.get("name").toString();

        return name;
    }

    public boolean inClan(Player player){
        if(getClan(player).equals("null")){
            return false;
        } else {
            return true;
        }
    }

    public void addCreateState(Player player){
        createState.add(player);
    }

    public void removeCreateState(Player player){
        createState.remove(player);
    }

    public boolean isInCreateState(Player player){
        return createState.contains(player);
    }

    public void addFly(Player player){
        fly.add(player);
    }

    public void removeFly(Player player){
        fly.remove(player);
    }

    public boolean isFly(Player player){
        return fly.contains(player);
    }
}