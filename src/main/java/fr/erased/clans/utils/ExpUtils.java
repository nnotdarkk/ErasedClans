package fr.erased.clans.utils;

import lombok.Getter;

public class ExpUtils {

    @Getter
    private final int exp;

    public ExpUtils(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return getLevel(getExp());
    }

    public int getLevel(int xp) {
        int a = 100;
        int level = 1;

        while (xp >= a) {
            xp -= a;
            a += 100;
            level++;
        }

        if (level > 100) {
            level = 100;
        }

        return level;
    }

    public int getExpToNextLevel() {
        return getExpToNextLevel(getLevel());
    }

    public int getExpToNextLevel(int level) {
        int a = 100;
        int b = 1;

        for (int i = 1; i < level; i++) {
            b = b + 1;
            for (int j = 0; j < b; j++) {
                a += 100;
            }
        }

        return a;
    }

    public int getPercent() {
        if (getLevel() == 1) {
            return getPercent(getExp(), getExpToNextLevel());
        }

        int level = getLevel() - 1;
        int maxExp = getExpToNextLevel(level);

        int a = getExp() - maxExp;
        int b = getExpToNextLevel() - maxExp;

        return getPercent(a, b);
    }

    private int getPercent(double currentValue, double maxValue) {
        return (int) ((currentValue / maxValue) * 100);
    }

    public int getActualExp() {
        if (getLevel() == 1) {
            return getExp();
        }

        int level = getLevel() - 1;
        int maxExp = getExpToNextLevel(level);

        return getExp() - maxExp;
    }

    public int getActualExpToNextLevel() {
        if (getLevel() == 1) {
            return getExpToNextLevel();
        }

        int level = getLevel() - 1;
        int maxExp = getExpToNextLevel(level);

        return getExpToNextLevel() - maxExp;
    }
}
