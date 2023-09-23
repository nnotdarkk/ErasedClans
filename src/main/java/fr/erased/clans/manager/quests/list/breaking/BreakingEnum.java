package fr.erased.clans.manager.quests.list.breaking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter @AllArgsConstructor
public enum BreakingEnum {

    STONE(Material.STONE, 0, true);

    private final Material material;
    private final int blockData;
    private final boolean storePlace;

}
