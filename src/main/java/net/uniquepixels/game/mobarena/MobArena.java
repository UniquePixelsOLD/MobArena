package net.uniquepixels.game.mobarena;

import net.kyori.adventure.text.Component;
import net.uniquepixels.core.paper.gui.backend.UIHolder;
import net.uniquepixels.game.GameEngine;
import net.uniquepixels.game.GameState;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MobArena extends JavaPlugin implements Listener {

  private MobArenaGame game;
  private UIHolder uiHolder;
  private List<TextDisplay> texts = new ArrayList<>();


  public UIHolder getUiHolder() {
    return uiHolder;
  }


  @Override
  public void onEnable() {

    this.game = new MobArenaGame(this);
    getPlugin(GameEngine.class).setGame(game);

    RegisteredServiceProvider<UIHolder> registeredUIHolder = Bukkit.getServicesManager().getRegistration(UIHolder.class);
    if (registeredUIHolder == null)
      return;

    this.uiHolder = registeredUIHolder.getProvider();

    Bukkit.getPluginManager().registerEvents(this.game, this);
    Bukkit.getPluginManager().registerEvents(this, this);

    getCommand("wave").setExecutor(new WaveCommand(this.game.getMobWaves()));

    World world = Bukkit.getWorld("world");

    if (world == null)
      return;

    world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, true);
    world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
    world.setGameRule(GameRule.DO_FIRE_TICK, false);
    world.setGameRule(GameRule.MOB_GRIEFING, false);
    world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
    world.setGameRule(GameRule.DO_ENTITY_DROPS, false);

    world.getEntities().forEach(Entity::remove);

    this.spawnText(world, new Location(world, -49, 34, 6), Component.text("INFO TEXT USW."));
    this.spawnText(world, new Location(world, -53, 34,13), Component.text("INFO TEXT USW."));

    Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MobArena.class), () -> {

      Bukkit.getOnlinePlayers().forEach(player -> {

        player.sendActionBar(Component.text("Coins » " + game.getMobWaves().getWaveCoins().getCoinsFromPlayer(player)));

      });

    }, 0, 20 * 2L);

    this.spawnShop(world);
  }

  private void spawnShop(World world) {
    Location location = new Location(world, -53, 33, 25, -107f, 2.8f);

    Villager shopEntity = (Villager) world.spawnEntity(location, EntityType.VILLAGER);

    shopEntity.setAI(false);
    shopEntity.setGravity(false);
    shopEntity.setInvulnerable(false);
    shopEntity.setProfession(Villager.Profession.ARMORER);
    shopEntity.setVillagerType(Villager.Type.SAVANNA);
    shopEntity.setAware(false);
  }

  private void spawnText(World world, Location location, Component text) {

    TextDisplay display = (TextDisplay) world.spawnEntity(location, EntityType.TEXT_DISPLAY);
    display.text(text);
    display.setSeeThrough(false);
    display.setBackgroundColor(Color.GRAY);
    display.setAlignment(TextDisplay.TextAlignment.CENTER);
    display.setBillboard(Display.Billboard.CENTER);

    this.texts.add(display);

  }

  @EventHandler
  public void onWaterBucketEmpty(PlayerBucketEmptyEvent event) {
    event.setCancelled(this.game.getCurrentState() != GameState.RUNNING);
    event.setCancelled(event.getBucket() != Material.WATER_BUCKET);
  }
  @EventHandler
  public void onWaterBucketFill(PlayerBucketFillEvent event) {
    event.setCancelled(this.game.getCurrentState() != GameState.RUNNING);
    event.setCancelled(event.getBucket() != Material.WATER_BUCKET);
  }

  @EventHandler
  public void onMove(PlayerMoveEvent event) {

    Player player = event.getPlayer();
    Location eventTo = event.getTo();

    for (TextDisplay text : this.texts) {

      if (eventTo.distanceSquared(text.getLocation()) > 10.0)
        player.hideEntity(this, text);
      else
        player.showEntity(this, text);

    }

  }

}
