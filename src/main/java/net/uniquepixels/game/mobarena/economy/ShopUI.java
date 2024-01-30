package net.uniquepixels.game.mobarena.economy;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.uniquepixels.core.paper.gui.UIRow;
import net.uniquepixels.core.paper.gui.exception.OutOfInventoryException;
import net.uniquepixels.core.paper.gui.types.chest.ChestUI;
import org.bukkit.entity.Player;

public class ShopUI extends ChestUI {
  public ShopUI(Player opener, WaveCoins waveCoins) {
    super(Component.text("Villager Shop UI").color(NamedTextColor.GRAY), UIRow.CHEST_ROW_3, opener);
  }

  @Override
  protected void initItems(Player player) throws OutOfInventoryException {

  }

  @Override
  public boolean allowItemMovementInOtherInventories() {
    return false;
  }
}
