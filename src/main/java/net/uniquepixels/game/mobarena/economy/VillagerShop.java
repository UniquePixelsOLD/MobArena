package net.uniquepixels.game.mobarena.economy;

import net.uniquepixels.core.paper.gui.backend.UIHolder;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class VillagerShop implements Listener {

  private final WaveCoins waveCoins;
  private final UIHolder uiHolder;

  public VillagerShop(WaveCoins waveCoins, UIHolder uiHolder) {
    this.waveCoins = waveCoins;
      this.uiHolder = uiHolder;
  }

  @EventHandler
  public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {

    if (event.getRightClicked().getType() != EntityType.VILLAGER)
      return;

    event.setCancelled(true);

    this.uiHolder.open(new ShopUI(event.getPlayer(), this.waveCoins), event.getPlayer());

  }
}
