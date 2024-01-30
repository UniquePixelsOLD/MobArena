package net.uniquepixels.game.mobarena.running;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.TitlePart;
import net.uniquepixels.game.mobarena.MobArena;
import net.uniquepixels.game.mobarena.economy.WaveCoins;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobWaves implements Listener {

  private final World world = Bukkit.getWorld("world");
  private final WaveCoins waveCoins = new WaveCoins();
  private final List<EntityType> entities = new ArrayList<>();

  public WaveCoins getWaveCoins() {
    return waveCoins;
  }

  private final List<Location> spawners = List.of(
    new Location(world, -17, 34, 43),
    new Location(world, -50, 34, 50),
    new Location(world, -32, 34, 18)
  );
  private final int maxWaveCount = 10;
  private int waveCount = 0;
  private MobWave activeWave;

  private int getMaxEntitiesPerSpawner() {
    return waveCount + 2;
  }

  private EntityType chooseRandom() {
    int random = new Random().nextInt(entities.size() - 1);
    return this.entities.get(random);
  }

  private void spawnWave() {
    waveCount++;

    this.entities.addAll(WaveEntities.WAVES.get(waveCount));

    this.activeWave = new MobWave(this.spawners.size() * this.getMaxEntitiesPerSpawner(), this.waveCount);

    Bukkit.getOnlinePlayers().forEach(player -> {
      player.sendTitlePart(TitlePart.TITLE, Component.text("Wave"));
      player.sendTitlePart(TitlePart.SUBTITLE, Component.text(waveCount));
      player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 30f, 1f);
    });

    this.spawners.forEach(location -> {

      World world1 = location.getWorld();

      for (int i = 0; i < this.getMaxEntitiesPerSpawner(); i++) {
        Monster entity = (Monster) world1.spawnEntity(location.clone().add(0.0, 2.0, 0.0), chooseRandom());

        entity.setCustomNameVisible(true);

        entity.customName(Component.text("Wave " + waveCount).color(NamedTextColor.RED));

        this.activeWave.getEntities().add(entity);
      }
    });

    this.activeWave.updateBar();
  }

  @EventHandler
  public void onEntityTarget(EntityTargetLivingEntityEvent event) {

    if (event.getTarget() == null)
      return;

    if (event.getTarget().getType() == EntityType.VILLAGER)
      event.setCancelled(true);

  }

  @EventHandler (priority = EventPriority.HIGHEST)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

    if (event.getEntity() instanceof Player) {
      event.setCancelled(true);
      return;
    }

    if (!(event.getEntity() instanceof LivingEntity living))
      return;

    if (living.getHealth() > event.getDamage())
      return;


    if (!this.activeWave.getEntities().contains(event.getEntity()))
      return;

    this.activeWave.getEntities().remove(event.getEntity());

    this.activeWave.updateBar();

    if (this.activeWave.getEntities().isEmpty() && this.waveCount < this.maxWaveCount) {
      this.activeWave.removeBossBar();
      this.respawnWave();
    }

    if (!(event.getDamager() instanceof Player player))
      return;

    this.waveCoins.addCoins(player, event.getEntityType());
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {

    event.setDroppedExp(0);
    event.getDrops().clear();

  }

  public void respawnWave() {
    this.activeWave.removeBossBar();
    Bukkit.getScheduler().runTaskLater(MobArena.getPlugin(MobArena.class), this::spawnWave, 20 * 3L);
  }

  public void start() {
    if (this.waveCount != 0)
      return;
    this.spawnWave();
  }
}
