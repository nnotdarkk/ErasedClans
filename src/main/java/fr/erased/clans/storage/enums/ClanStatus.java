package fr.erased.clans.storage.enums;

import lombok.Getter;

public enum ClanStatus {

    VILLAGE("§6Village", 50, 90),
    ROYAUME("§eRoyaume", 35, 70),
    CITADELLE("§2Citadelle", 20, 50),
    EMPIRE("§5Empire", 10, 20);

    @Getter
    private final String formattedName;
    @Getter
    private final int maxMembers;
    @Getter
    private final int maxClaims;

    ClanStatus(String formattedName, int maxMembers, int maxClaims) {
        this.formattedName = formattedName;
        this.maxMembers = maxMembers;
        this.maxClaims = maxClaims;
    }
}
