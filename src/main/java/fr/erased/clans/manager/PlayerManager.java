package fr.erased.clans.manager;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.enums.PlayerRank;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final ErasedClans main;
    private final UUID uuid;

    private YamlConfiguration f;

    private static final List<UUID> createState = new ArrayList<>();
    private static final List<UUID> fly = new ArrayList<>();
    private static final List<UUID> bypassClaims = new ArrayList<>();

    public PlayerManager(ErasedClans main, Player player) {
        this.main = main;
        this.uuid = player.getUniqueId();

        if (main.getFileManager().getFile("userdata", uuid.toString()) != null) {
            this.f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid.toString()));
            return;
        }

        this.f = null;
    }

    @SneakyThrows
    public PlayerManager(ErasedClans main, UUID uuid) {
        this.main = main;
        this.uuid = uuid;

        if (main.getFileManager().getFile("userdata", uuid.toString()) != null) {
            this.f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid.toString()));
            return;
        }

        this.f = null;
    }

    public boolean notExists(){
        return f == null;
    }
    public UUID getUuid(){
        return uuid;
    }

    public void addFly() {
        fly.add(uuid);
    }

    public void removeFly() {
        fly.remove(uuid);
    }

    public boolean isFly() {
        return fly.contains(uuid);
    }

    public void addBypassClaims(){
        bypassClaims.add(uuid);
    }

    public void removeBypassClaims(){
        bypassClaims.remove(uuid);
    }

    public boolean isBypassClaims(){
        return bypassClaims.contains(uuid);
    }

    public void addCreateState() {
        createState.add(uuid);
    }

    public void removeCreateState() {
        createState.remove(uuid);
    }

    public boolean isInCreateState() {
        return createState.contains(uuid);
    }

    public void registerPlayer(String name) {
        File file = new File(main.getDataFolder() + File.separator + "userdata" + File.separator + uuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata", uuid.toString()));

            f.set("name", name);
            f.set("clan", "null");
            f.set("rank", "null");
            saveFile();
        }
    }

    public String getClan() {
        return f.getString("clan");
    }

    public String getName() {
        return f.getString("name");
    }

    public PlayerRank getPlayerRank() {
        String rank = f.getString("rank");
        return PlayerRank.valueOf(rank);
    }

    public String getStringRank() {
        return f.getString("rank");
    }

    public String getPlayerRankString() {
        return getPlayerRank().getFormattedName();
    }

    public void registerClan(String name) {
        registerClan(name, PlayerRank.RECRUE);
    }

    public void registerClan(String name, PlayerRank rank) {
        f.set("clan", name);
        f.set("rank", rank.toString());
        saveFile();
    }


    public void unregisterClan() {
        f.set("clan", "null");
        f.set("rank", "null");
        saveFile();
    }

    public void setPlayerRank(PlayerRank rank) {
        f.set("rank", rank.toString());
        saveFile();
    }

    public boolean inClan() {
        return !getClan().equals("null");
    }

    public void saveFile() {
        try {
            f.save(main.getFileManager().getFile("userdata", uuid.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
