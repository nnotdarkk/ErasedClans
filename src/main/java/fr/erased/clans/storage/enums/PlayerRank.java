package fr.erased.clans.storage.enums;

import lombok.Getter;

public enum PlayerRank {

    CHEF("§cChef"),
    OFFICIER("§6Officier"),
    MEMBRE("§bMembre"),
    RECRUE("§fRecrue");

    @Getter
    private final String formattedName;
    PlayerRank(String formattedName) {
        this.formattedName = formattedName;
    }
}
