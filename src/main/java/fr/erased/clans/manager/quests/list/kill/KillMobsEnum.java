package fr.erased.clans.manager.quests.list.kill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

@Getter @AllArgsConstructor
public enum KillMobsEnum {

    //ELDER_GUARDIAN: TROP DUR
    //WARDEN: TROP DUR
    //EVOKER: TROP DUR
    //VEX: TROP DUR
    //VINDICATOR: TROP DUR
    //SILVERFISH: TROP DUR
    //ENDER_DRAGON: TROP DUR
    //WITHER: TROP DUR
    //GUARDIAN: TROP DUR
    //SHULKER: TROP DUR
    //RAVAGER: TROP DUR

    //ILLUSIONER: NE SPAWN PAS NATURELLEMENT (NE PAS METTRE)
    //GIANT: NE SPAWN PAS NATURELLEMENT (NE PAS METTRE)

    WITHER_SKELETON(EntityType.WITHER_SKELETON, 3, Material.WITHER_SKELETON_SPAWN_EGG, "Squelette du wither"),
    STRAY(EntityType.STRAY, 5, Material.STRAY_SPAWN_EGG, "Vagabond"),
    HUSK(EntityType.HUSK, 5, Material.HUSK_SPAWN_EGG, "Zombie momifié"),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, 10, Material.ZOMBIE_VILLAGER_SPAWN_EGG, "Zombie Villageois"),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, 1, Material.SKELETON_HORSE_SPAWN_EGG, "Cheval squelette"),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, 1, Material.ZOMBIE_HORSE_SPAWN_EGG, "Cheval zombie"),
    CREEPER(EntityType.CREEPER, 20, Material.CREEPER_SPAWN_EGG, "Creeper"),
    SKELETON(EntityType.SKELETON, 20, Material.SKELETON_SPAWN_EGG, "Squelette"),
    SPIDER(EntityType.SPIDER, 20, Material.SPIDER_SPAWN_EGG, "Araignée"),
    ZOMBIE(EntityType.ZOMBIE, 20, Material.ZOMBIE_SPAWN_EGG, "Zombie"),
    SLIME(EntityType.SLIME, 5, Material.SLIME_SPAWN_EGG, "Slime"),
    GHAST(EntityType.GHAST, 1, Material.GHAST_SPAWN_EGG, "Ghast"),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, 5, Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, "Piglin zombifié"),
    ENDERMAN(EntityType.ENDERMAN, 5, Material.ENDERMAN_SPAWN_EGG, "Enderman"),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, 5, Material.CAVE_SPIDER_SPAWN_EGG, "Araignée venimeuse"),
    BLAZE(EntityType.BLAZE, 5, Material.BLAZE_SPAWN_EGG, "Blaze"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE,  5, Material.MAGMA_CUBE_SPAWN_EGG, "Bloc de magma"),
    BAT(EntityType.BAT, 2, Material.BAT_SPAWN_EGG, "Chauve-souris"),
    WITCH(EntityType.WITCH, 1, Material.WITCH_SPAWN_EGG, "Sorcière"),
    PHANTOM(EntityType.PHANTOM, 3, Material.PHANTOM_SPAWN_EGG, "Phantome"),
    DROWNED(EntityType.DROWNED, 5, Material.DROWNED_SPAWN_EGG, "Noyé"),
    PILLAGER(EntityType.PILLAGER, 2, Material.PILLAGER_SPAWN_EGG, "Pillard"),
    HOGLIN(EntityType.HOGLIN, 5, Material.HOGLIN_SPAWN_EGG, "Hoglin"),
    PIGLIN(EntityType.PIGLIN, 5, Material.PIGLIN_SPAWN_EGG, "Piglin"),
    STRIDER(EntityType.STRIDER, 5, Material.STRIDER_SPAWN_EGG, "Arpenteur"),
    ZOGLIN(EntityType.ZOGLIN, 5, Material.ZOGLIN_SPAWN_EGG, "Zoglin"),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, 3, Material.PIGLIN_BRUTE_SPAWN_EGG, "Piglin barbare"),
    ENDERMITE(EntityType.ENDERMITE, 5, Material.ENDERMITE_SPAWN_EGG, "Endermite"),
    PLAYER(EntityType.PLAYER, 1, Material.PLAYER_HEAD, "Joueur");

    private final EntityType type;
    private final int kills;
    private final Material material;
    private final String name;

}
