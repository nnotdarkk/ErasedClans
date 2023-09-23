package fr.erased.clans.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum ClanStatus {

    VILLAGE("§6Village", 50, 90),
    ROYAUME("§eRoyaume", 35, 70),
    CITADELLE("§2Citadelle", 20, 50),
    EMPIRE("§5Empire", 10, 20);

    private final String formattedName;
    private final int maxMembers;
    private final int maxClaims;
}
