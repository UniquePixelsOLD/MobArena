package net.uniquepixels.game.mobarena.running;

import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

public class WaveEntities {

  public static Map<Integer, List<EntityType>> WAVES = Map.of(
    1, List.of(EntityType.ZOMBIE, EntityType.ENDERMITE),
    2, List.of(EntityType.SKELETON),
    3, List.of(EntityType.SLIME),
    4, List.of(EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.MAGMA_CUBE),
    5, List.of(EntityType.SPIDER, EntityType.STRAY),
    6, List.of(EntityType.CAVE_SPIDER, EntityType.WITCH),
    7, List.of(EntityType.VINDICATOR),
    8, List.of(EntityType.VEX, EntityType.BLAZE),
    9, List.of(EntityType.EVOKER, EntityType.RAVAGER),
    10, List.of(EntityType.ILLUSIONER, EntityType.WITHER, EntityType.WARDEN)
  );

}
