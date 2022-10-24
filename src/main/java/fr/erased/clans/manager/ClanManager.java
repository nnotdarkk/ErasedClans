package fr.erased.clans.manager;

import fr.erased.clans.Main;
import fr.erased.clans.enums.QuestDifficulty;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ClanManager {

    private final Main main;

    private static HashMap<String, Player> invitation = new HashMap<>();

    public ClanManager(Main main) {
        this.main = main;
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
    public void createClan(Player owner, String name, String tag) {
        main.getFileManager().createFile("clans", name);

        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));

        List<String> members = new ArrayList<>();
        boolean[] setter = new boolean[10];
        int[] setter2 = new int[10];
        members.add(owner.getUniqueId().toString());
        f.set("name", name);
        f.set("tag", tag);
        f.set("owner", owner.getUniqueId().toString());
        f.set("members", members);
        f.set("xp", 0);
        f.set("quests.stats.easy", setter);
        f.set("quests.stats.medium", setter);
        f.set("quests.stats.hard", setter);
        f.set("quests.easy", setter2);
        f.set("quests.medium", setter2);
        f.set("quests.hard", setter2);
        f.set("allies", new ArrayList<>());
        f.set("enemies", new ArrayList<>());
        f.set("claims", null);
        try {
            f.save(main.getFileManager().getFile("clans", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        main.getPlayerManager().registerClan(owner, name);
    }

    public int getClanMaxClaims(String clan){
        return getMembers(clan).size() * 10;
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
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        f.set("xp", f.getInt("xp") + xp);
        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getClanLevel(String clan){
        int a = 100;
        int xp = getClanXp(clan);
        int level = 1;
        while (xp >= a){
            xp -= a;
            a += 100;
            level++;
        }
        if(level > 100){
            level = 100;
        }
        return level;
    }

    public int getClanExpToNextLevel(String clan){
        int a = 100;
        int level = getClanLevel(clan);
        int b = 1;
        for(int i = 1; i < level; i++){
            b = b + 1;
            for (int j = 0; j < b; j++) {
                a += 100;
            }
        }
        return a;
    }

    /*public HashMap<String, Integer> getAllXp(){
        HashMap<String, Integer> levels = new HashMap<>();
        for (int i = 0; i < getClans().size(); i++) {
            String clan = String.valueOf(getClans().get(i));
            levels.put(clan, getClanXp(clan));
        }
        return null;
    }*/

    public boolean clanExists(String clan){
        return main.getFileManager().getFile("clans", clan).exists();
    }

    public boolean getQuestList(String clan, QuestDifficulty questDifficulty, int quest){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        if(questDifficulty == QuestDifficulty.Facile){
            List<Boolean> stats = f.getBooleanList("quests.stats.easy");
            return stats.get(quest);
        } else if(questDifficulty == QuestDifficulty.Moyen){
            List<Boolean> stats = f.getBooleanList("quests.stats.medium");
            return stats.get(quest);
        } else if(questDifficulty == QuestDifficulty.Difficile){
            List<Boolean> stats = f.getBooleanList("quests.stats.hard");
            return stats.get(quest);
        }
        return false;
    }

    public String getStringQuestCompleted(String clan, QuestDifficulty questDifficulty, int quest){
        if(getQuestList(clan, questDifficulty, quest)){
            return "§aTerminée";
        } else {
            return "§cNon terminée";
        }
    }

    public void setQuestList(String clan, QuestDifficulty questDifficulty, int quest, boolean bool){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        if(questDifficulty == QuestDifficulty.Facile){
            List<Boolean> stats = f.getBooleanList("quests.stats.easy");
            stats.set(quest, bool);
            f.set("quests.stats.easy", stats);
        } else if(questDifficulty == QuestDifficulty.Moyen){
            List<Boolean> stats = f.getBooleanList("quests.stats.medium");
            stats.set(quest, bool);
            f.set("quests.stats.easy", stats);
        } else if(questDifficulty == QuestDifficulty.Difficile){
            List<Boolean> stats = f.getBooleanList("quests.stats.hard");
            stats.set(quest, bool);
            f.set("quests.stats.easy", stats);
        }

        try {
            f.save(main.getFileManager().getFile("clans", clan));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getQuestListCompleted(String clan, QuestDifficulty difficulty){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        int i = 0;

        if(difficulty == QuestDifficulty.Facile){
            for(boolean b : f.getBooleanList("quests.stats.easy")){
                if(b) i++;
            }
        } else if(difficulty == QuestDifficulty.Moyen){
            for(boolean b : f.getBooleanList("quests.stats.medium")){
                if(b) i++;
            }
        } else if(difficulty == QuestDifficulty.Difficile){
            for(boolean b : f.getBooleanList("quests.stats.hard")){
                if(b) i++;
            }
        }
        return i;
    }

    public int getQuestInt(String clan, QuestDifficulty questDifficulty, int quest){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", clan));
        if(questDifficulty == QuestDifficulty.Facile){
            List<Integer> stats = f.getIntegerList("quests.easy");
            return stats.get(quest);
        } else if(questDifficulty == QuestDifficulty.Moyen){
            List<Integer> stats = f.getIntegerList("quests.medium");
            return stats.get(quest);
        } else if(questDifficulty == QuestDifficulty.Difficile){
            List<Integer> stats = f.getIntegerList("quests.hard");
            return stats.get(quest);
        }
        return 0;
    }

    public Integer getLimitQuest(QuestDifficulty difficulty, int quest){
        switch (difficulty){
            case Facile:
                switch (quest){
                    case 1:
                        return 10;

                    case 2:
                        return 250;

                    case 3:
                        return 50;

                    case 4:
                        return 25;

                    case 5:
                        return 15;

                    case 6:
                        return 20;

                    case 7:
                        return 300;

                    case 8:
                        return 20;

                    case 9: case 10:
                        return 1;
                }
                break;

            case Moyen:
                break;

            case Difficile:
                break;
        }
        if(difficulty.equals(QuestDifficulty.Facile)){

        }
        return null;
    }
    public List<File> getClans(){
        return new ArrayList<>(main.getFileManager().getFiles("clans"));
    }

    public void removeClan(String name) {


        for(String member : getMembers(name)){
            String playername = main.getPlayerManager().getNameByUUID(member);
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

    public String getTag(String name){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("clans", name));
        return f.get("tag").toString();
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
                String playername = main.getPlayerManager().getNameByUUID(member);
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
