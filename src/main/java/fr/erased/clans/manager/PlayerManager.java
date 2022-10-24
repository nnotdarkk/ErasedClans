package fr.erased.clans.manager;

import fr.erased.clans.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlayerManager {

    private final Main main;

    public PlayerManager(Main main) {
        this.main = main;
    }

    private static HashMap<Player, Boolean> createState = new HashMap<>();

    public void setState(Player player, boolean state){
        createState.put(player, state);
    }

    public boolean isState(Player player){
        return createState.get(player);
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
        try {
            f.save(main.getFileManager().getFile("userdata", player.getUniqueId().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void unregisterClan(String uuid){
        FileConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid));

        f.set("clan", "null");
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
}
