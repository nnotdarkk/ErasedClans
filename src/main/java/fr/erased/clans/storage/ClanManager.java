package fr.erased.clans.storage;

import fr.erased.clans.Main;
import fr.erased.clans.storage.user.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class ClanManager {

    private final Main main;
    private final String clan;
    private final YamlConfiguration f;

    private static final HashMap<String, Player> invitation = new HashMap<>();

    public ClanManager(Main main, String clan) {
        this.main = main;
        this.clan = clan;
        this.f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
    }

    public void createClan(Player owner) {
        main.getFileManager().createFile("clans", clan);


        List<String> members = new ArrayList<>();
        members.add(owner.getUniqueId().toString());

        Map<Integer, ItemStack> chest = new HashMap<>();
        Inventory inv = Bukkit.createInventory(null, 54, "new-inv");
        for (int slot = 0; slot < inv.getSize(); slot++) {
            chest.put(slot, inv.getItem(slot));
        }

        f.set("name", clan);
        f.set("description", null);
        f.set("owner", owner.getUniqueId().toString());
        f.set("members", members);
        f.set("xp", 0);
        f.createSection("chest", chest);
        f.set("base", null);
        f.set("allies", new ArrayList<>());
        f.set("claims", null);

        saveFile();
        new PlayerManager(main, owner).registerClan(clan);
    }

    public void addInvitation(Player player){
        invitation.put(clan, player);
    }

    public void removeInvitation(Player player){
        invitation.remove(clan, player);
    }

    public boolean hasInvitation(Player player){
        return invitation.containsKey(clan) && invitation.containsValue(player);
    }

    public Inventory getClanChest(){
        Inventory inv = Bukkit.createInventory(null, 54, "Coffre du clan: " + clan);
        ConfigurationSection chest = f.getConfigurationSection("chest");
        if (chest != null)
            for (String key : chest.getKeys(false)) {
                inv.setItem(Integer.parseInt(key), chest.getItemStack(key));
            }
        return inv;
    }

    public void setClanChest(Inventory inv){
        Map<Integer, ItemStack> chest = new HashMap<>();
        for (int slot = 0; slot < inv.getSize(); slot++) {
            chest.put(slot, inv.getItem(slot));
        }
        f.set("chest", chest);
        saveFile();
    }

    public Location getClanBase(){
        return f.getLocation("base");
    }

    public void setClanBase(Location location){
        f.set("base", location);
        saveFile();
    }

    public int getClanMaxClaims(){
        return getMembers().size() * 10;
    }

    public void setClanXp(int xp){
        f.set("xp", xp);
        saveFile();
    }

    public int getClanXp(){
        return f.getInt("xp");
    }

    public int getClanLevel(){
        int a = 1000;
        int xp = getClanXp();
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

    public int getClanExpToNextLevel(){
        int a = 1000;
        int level = getClanLevel();
        int b = 1;
        for(int i = 1; i < level; i++){
            b = b + 1;
            for (int j = 0; j < b; j++) {
                a += 500;
            }
        }
        return a;
    }

    public boolean clanExists(){
        return clanExists(clan);
    }
    public boolean clanExists(String clan){
        return main.getFileManager().getFile("clans", clan).exists();
    }

    public void removeClan() {
        for(String member : getMembers()){
            new PlayerManager(main, member).unregisterClan();
        }

        main.getChunkManager().removeAllClaimsForClan(clan);
        main.getFileManager().removeFile("clans", clan);
    }

    public List<String> getAllies() {
        return f.getStringList("allies");
    }

    public void addAlly(String ally) {
        List<String> allies = f.getStringList("allies");
        allies.add(ally);
        f.set("allies", allies);
        saveFile();
    }

    public void removeAlly(String ally) {

        List<String> allies = f.getStringList("allies");
        allies.remove(ally);
        f.set("allies", allies);
        saveFile();
    }

    public List<String> getEnemies() {
        return f.getStringList("enemies");
    }

    public void addEnemies(String enemy) {
        List<String> enemies = f.getStringList("enemies");
        enemies.add(enemy);
        f.set("enemies", enemies);
        saveFile();
    }

    public void removeEnemies(String enemy) {
        List<String> enemies = f.getStringList("enemies");
        enemies.remove(enemy);
        f.set("enemies", enemies);
        saveFile();
    }

    public String getOwner() {
        return f.get("owner").toString();
    }


    public List<String> getMembers(){
        return f.getStringList("members");
    }

    public List<String> getClaims(){
        return f.getStringList("claims");
    }

    public void removeAllClaims(){
        f.set("claims", new ArrayList<>());
        saveFile();
    }

    public void addClaim(Chunk chunk){
        List<String> claims = getClaims();
        claims.add(String.valueOf(chunk));
        f.set("claims", claims);
        saveFile();
    }

    public void removeClaim(Chunk chunk){
        List<String> claims = getClaims();
        claims.remove(String.valueOf(chunk));
        f.set("claims", claims);
        saveFile();
    }

    public void addMember(Player player){
        List<String> members = f.getStringList("members");
        members.add(player.getUniqueId().toString());
        f.set("members", members);
        saveFile();

        new PlayerManager(main, player).registerClan(clan);
    }

    public void removeMember(Player player){
        if(getOwner().equals(player.getUniqueId().toString())){
            removeClan();
            return;
        }

        List<String> members = f.getStringList("members");
        members.remove(player.getUniqueId().toString());
        f.set("members", members);
        saveFile();

        new PlayerManager(main, player).unregisterClan();
    }

    private void saveFile(){
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
