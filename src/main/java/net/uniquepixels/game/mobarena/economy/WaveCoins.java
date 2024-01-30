package net.uniquepixels.game.mobarena.economy;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class WaveCoins {

  private final Map<Player, Integer> coinMap = new HashMap<>();

  public void addCoins(Player player, EntityType killedEntity) {

    switch (killedEntity) {
      case ZOMBIE, ENDERMITE -> this.addCoins(player, 1);
      case SKELETON, SLIME, MAGMA_CUBE -> this.addCoins(player, 2);
      case WITHER_SKELETON, ZOGLIN, SPIDER, STRAY -> this.addCoins(player, 3);
      case CAVE_SPIDER, WITCH, BLAZE -> this.addCoins(player, 4);
      case VINDICATOR, VEX, EVOKER, RAVAGER -> this.addCoins(player, 5);
      case ILLUSIONER -> this.addCoins(player, 6);
      case WARDEN, WITHER -> this.addCoins(player, 10);
    }

  }

  public int getCoinsFromPlayer(Player player) {

    return this.coinMap.getOrDefault(player, 0);

  }

  private void addCoins(Player player, int count) {

    int coins = 0;

    if (this.coinMap.containsKey(player))
      coins = this.coinMap.get(player);

    coins += count;

    player.sendMessage("Added coins " + count + " Total: " + coins);

    this.coinMap.put(player, coins);

  }

}
