package fr.erased.clans.storage;

import fr.erased.clans.Main;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ClanManager {

    private final Main main;

    private static HashMap<String, Player> invitation = new HashMap<>();

    public ClanManager(Main main) {
        this.main = main;
    }

    @SneakyThrows
    public void createClan(Player owner, String name) {
        main.getFileManager().createFile("clans", name);

        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));

        List<String> members = new ArrayList<>();

        Map<Integer, ItemStack> chest = new HashMap<>();
        Inventory inv = Bukkit.createInventory(null, 54, "new-inv");
        for (int slot = 0; slot < inv.getSize(); slot++) {
            chest.put(slot, inv.getItem(slot));
        }

        members.add(owner.getUniqueId().toString());
        f.set("name", name);
        f.set("owner", owner.getUniqueId().toString());
        f.set("members", members);
        f.set("xp", 0);
        f.createSection("chest", chest);
        f.set("base", null);
        f.set("allies", new ArrayList<>());
        f.set("claims", null);

        f.save(main.getFileManager().getFile("clans", name));
        main.getPlayerManager().registerClan(owner, name);
    }

    public void addInvitation(Player player, String clan){
        invitation.put(clan, player);
    }

    public void removeInvitation(Player player, String clan){
        invitation.remove(clan, player);
    }

    public boolean hasInvitation(Player player, String clan){
        return invitation.containsKey(clan) && invitation.containsValue(player);
    }

    public Inventory getClanChest(String clan){
        Inventory inv = Bukkit.createInventory(null, 54, "Coffre du clan: " + clan);
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        ConfigurationSection chest = f.getConfigurationSection("chest");
        if (chest != null)
            for (String key : chest.getKeys(false)) {
                inv.setItem(Integer.parseInt(key), chest.getItemStack(key));
            }
        return inv;
    }

    @SneakyThrows
    public void setClanChest(String clan, Inventory inv){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        Map<Integer, ItemStack> chest = new HashMap<>();
        for (int slot = 0; slot < inv.getSize(); slot++) {
            chest.put(slot, inv.getItem(slot));
        }
        f.set("chest", chest);
        f.save(main.getFileManager().getFile("clans", clan));
    }

    public int getClanMaxClaims(String clan){
        return getMembers(clan).size() * 10;
    }

    public List<File> getClans(){
        File folder = main.getFileManager().getFolder("clans");
        return Arrays.asList(folder.listFiles());
    }
    
    public LinkedHashMap<String, Integer> getAllXpClans(){
        HashMap<String, Integer> map = new HashMap<>();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < getClans().size(); i++) {
            File file = getClans().get(i);
            YamlConfiguration f = YamlConfiguration.loadConfiguration(file);
            map.put(f.getString("name"), f.getInt("xp"));
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(entry.getValue());
        }

        Collections.sort(list);


        for (int num : list) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }

        return sortedMap;
    }

    public List<String> getAllXpClansToList(){
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : getAllXpClans().entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    public void setClanXp(String clan, int xp){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        f.set("xp", xp);
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getClanXp(String clan){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        return f.getInt("xp");
    }

    public void addClanXp(String clan, int xp){
        setClanXp(clan, getClanXp(clan) + xp);
    }

    public int getClanLevel(String clan){
        int a = 1000;
        int xp = getClanXp(clan);
        int level = 1;
        while (xp >= a){
            xp -= a;
            a += 500;
            level++;
        }
        if(level > 100){
            level = 100;
        }
        return level;
    }

    public int getClanExpToNextLevel(String clan){
        int a = 1000;
        int level = getClanLevel(clan);
        int b = 1;
        for(int i = 1; i < level; i++){
            b = b + 1;
            for (int j = 0; j < b; j++) {
                a += 500;
            }
        }
        return a;
    }

    public String getClanMembersString(String clan){
        if(getMembers(clan).isEmpty()){
            return "§cAucun";
        }
        List<String> members = getMembers(clan);
        members.remove(getOwner(clan));

        List<String> usernames = new ArrayList<>();

        for (int i = 0; i < members.size(); i++) {
            String string = main.getPlayerManager().getNameByUUID(members.get(i));
            usernames.add(string);
        }

        return usernames.toString().replace("[", "").replace("]", "");
    }

    public boolean clanExists(String clan){
        return main.getFileManager().getFile("clans", clan).exists();
    }

    public void removeClan(String name) {
        for(String member : getMembers(name)){
            main.getPlayerManager().unregisterClan(member);
        }

        main.getChunkManager().removeAllClaimsForClan(name);

        main.getFileManager().removeFile("clans", name);
    }

    public List<String> getAllies(String clan) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        return f.getStringList("allies");
    }

    public void addAllies(String clan, String ally) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));

        List<String> allies = f.getStringList("allies");
        allies.add(ally);
        f.set("allies", allies);
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAllies(String clan, String ally) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));

        List<String> allies = f.getStringList("allies");
        allies.remove(ally);
        f.set("allies", allies);
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getEnemies(String clan) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));

        return f.getStringList("enemies");
    }

    public void addEnemies(String clan, String enemy) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));

        List<String> enemies = f.getStringList("enemies");
        enemies.add(enemy);
        f.set("enemies", enemies);
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeEnemies(String clan, String enemy) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));

        List<String> enemies = f.getStringList("enemies");
        enemies.remove(enemy);
        f.set("enemies", enemies);
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOwner(String name) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));
        return f.get("owner").toString();
    }


    public List<String> getMembers(String name){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));
        return f.getStringList("members");
    }

    public List<String> getClaims(String name){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));
        return f.getStringList("claims");
    }

    public void removeAllClaims(String name){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));
        f.set("claims", new ArrayList<>());
        try {
            f.save(main.getFileManager().getFile("clans", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addClaim(String name, Chunk chunk){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));

        List<String> claims = getClaims(name);
        claims.add(String.valueOf(chunk));
        f.set("claims", claims);

        try {
            f.save(main.getFileManager().getFile("clans", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeClaim(String name, Chunk chunk){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));

        List<String> claims = getClaims(name);
        claims.remove(String.valueOf(chunk));
        f.set("claims", claims);

        try {
            f.save(main.getFileManager().getFile("clans", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMember(String name, Player player){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));

        List<String> members = f.getStringList("members");
        members.add(player.getUniqueId().toString());
        f.set("members", members);
        try {
            f.save(main.getFileManager().getFile("clans", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        main.getPlayerManager().registerClan(player, name);
    }

    public void removeMember(String name, Player player){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));

        if(getOwner(name).equals(player.getUniqueId().toString())){
            getMembers(name).forEach(member -> {
                main.getPlayerManager().unregisterClan(member);
            });
            removeClan(name);

            return;
        }

        List<String> members = f.getStringList("members");
        members.remove(player.getUniqueId().toString());
        f.set("members", members);
        try {
            f.save(main.getFileManager().getFile("clans", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        main.getPlayerManager().unregisterClan(player.getUniqueId().toString());
    }
}
