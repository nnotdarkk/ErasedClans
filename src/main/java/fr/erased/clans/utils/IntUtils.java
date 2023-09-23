package fr.erased.clans.utils;

import org.bukkit.inventory.Inventory;

import java.util.stream.IntStream;

public final class IntUtils {

    public static int[] getCorners(Inventory inventory) {
        int size = inventory.getSize();
        return IntStream.range(0, size).filter(i -> i < 2 || (i > 6 && i < 10) || i == 17 || i == size - 18 || (i > size - 11 && i < size - 7) || i > size - 3).toArray();
    }

    public static String getProgressBar(int percent){
        StringBuilder p = new StringBuilder();
        p.append("§8[");
        for (int i = 0; i < 19; i++) {
            if (i < (percent / 5)) {
                p.append("§a▮");
            } else {
                p.append("§7▮");
            }
        }
        p.append("§8]");

        return p.toString();
    }
}
