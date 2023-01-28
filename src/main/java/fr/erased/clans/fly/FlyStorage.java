package fr.erased.clans.fly;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class FlyStorage {

    private static HashMap<Player, Boolean> fly = new HashMap<>();

    public void setFly(Player player, boolean b){
        fly.put(player, b);
    }

    public boolean isFly(Player player){
        return fly.get(player);
    }
}
