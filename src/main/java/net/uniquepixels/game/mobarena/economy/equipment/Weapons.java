package net.uniquepixels.game.mobarena.economy.equipment;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public enum Weapons {

  LEVEL_1(new DefaultItemStackBuilder<>(Material.STONE_SWORD)
    .displayName(Component.text("Stein Schwert")
      .color(NamedTextColor.GRAY))
    .addFlags(ItemFlag.HIDE_UNBREAKABLE)
    .setUnbreakable(true)
    .applyItemMeta().buildItem()),

  LEVEL_2(new DefaultItemStackBuilder<>(Material.IRON_SWORD)
    .displayName(Component.text("Eisen Schwert")
      .color(NamedTextColor.WHITE))
    .addFlags(ItemFlag.HIDE_UNBREAKABLE)
    .setUnbreakable(true)
    .applyItemMeta().buildItem()),
  LEVEL_3(new DefaultItemStackBuilder<>(Material.DIAMOND_SWORD)
    .displayName(Component.text("Diamant Schwert")
      .color(NamedTextColor.AQUA))
    .addFlags(ItemFlag.HIDE_UNBREAKABLE)
    .setUnbreakable(true)
    .applyItemMeta().buildItem()),
  LEVEL_4(new DefaultItemStackBuilder<>(Material.NETHERITE_SWORD)
    .displayName(Component.text("Netherite Schwert")
      .color(NamedTextColor.BLACK))
    .addFlags(ItemFlag.HIDE_UNBREAKABLE)
    .setUnbreakable(true)
    .applyItemMeta().buildItem());

  private final ItemStack itemStack;

  Weapons(ItemStack itemStack) {

    this.itemStack = itemStack;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }
}
