package fr.erased.clans.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum PlayerRank {

    CHEF("§cChef"),
    OFFICIER("§6Officier"),
    MEMBRE("§bMembre"),
    RECRUE("§fRecrue");


    private final String formattedName;

}
