package fr.erased.clans.storage;

import fr.erased.clans.Main;
import fr.erased.clans.storage.enums.ClanStatus;
import fr.erased.clans.storage.enums.PlayerRank;
import fr.erased.clans.utils.ExpUtils;
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
        f.set("claims", null);

        saveFile();
        new PlayerManager(main, owner).registerClan(clan, PlayerRank.CHEF);
    }

    public void addInvitation(Player player){
        invitation.put(clan, player);
    }
    public void removeInvitation(Player player){
        invitation.remove(clan, player);
    }
    public boolean hasInvitation(Player player, String name){
        for(Map.Entry<String, Player> entry : invitation.entrySet()){
            if(entry.getValue().equals(player) && entry.getKey().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Location getClanBase(){
        return f.getLocation("base");
    }
    public int getClanXp(){
        return f.getInt("xp");
    }
    public int getClanLevel(){
        ExpUtils expUtils = new ExpUtils(getClanXp());
        return expUtils.getLevel();
    }
    public String getOwner() {
        return f.getString("owner");
    }
    public List<String> getMembers(){
        return f.getStringList("members");
    }
    public List<String> getClaims(){
        return f.getStringList("claims");
    }
    public int getClanMaxMembers(){
        return getClanStatus().getMaxMembers();
    }
    public int getClanMaxClaims(){
        return getClanStatus().getMaxClaims();
    }

    public ClanStatus getClanStatus(){
        if(getClanLevel() >= 100){
            return ClanStatus.EMPIRE;
        }

        if(getClanLevel() >= 80){
            return ClanStatus.CITADELLE;
        }

        if(getClanLevel() >= 50){
            return ClanStatus.ROYAUME;
        }

        return ClanStatus.VILLAGE;
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
        set("chest", chest);
    }


    public void setClanBase(Location location){
        set("base", location);
    }

    public void setClanXp(int xp){
        set("xp", xp);
    }

    public void addClanXp(int xp){
        setClanXp(getClanXp() + xp);
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

    public void removeAllClaims(){
        set("claims", new ArrayList<>());
    }

    public void addClaim(Chunk chunk){
        List<String> claims = getClaims();
        claims.add(String.valueOf(chunk));
        set("claims", claims);
    }

    public void removeClaim(Chunk chunk){
        List<String> claims = getClaims();
        claims.remove(String.valueOf(chunk));
        set("claims", claims);
    }

    public void addMember(Player player){
        List<String> members = getMembers();
        members.add(player.getUniqueId().toString());
        set("members", members);

        new PlayerManager(main, player).registerClan(clan);
    }

    public void removeMember(Player player){
        if(getOwner().equals(player.getUniqueId().toString())){
            removeClan();
            return;
        }

        List<String> members = getMembers();
        members.remove(player.getUniqueId().toString());
        set("members", members);

        new PlayerManager(main, player).unregisterClan();
    }

    public void set(String path, Object value){
        f.set(path, value);
        saveFile();
    }

    private void saveFile(){
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
