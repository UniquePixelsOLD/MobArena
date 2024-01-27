package net.uniquepixels.game.mobarena.running;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class MobWave {

  private final List<Entity> entities = new ArrayList<>();
  private final int wave;
  private final int maxEntities;
  private final BossBar bossBar = BossBar.bossBar(Component.empty(), 1f, BossBar.Color.RED, BossBar.Overlay.PROGRESS);

  public MobWave(int maxEntities, int wave) {
    this.maxEntities = maxEntities;
    this.wave = wave;

    this.addPlayers();
  }

  private void addPlayers() {
    Bukkit.getOnlinePlayers().forEach(bossBar::addViewer);
  }

  public void removeBossBar() {
    this.getEntities().forEach(Entity::remove);
    Bukkit.getOnlinePlayers().forEach(bossBar::removeViewer);
  }

  public List<Entity> getEntities() {
    return entities;
  }

  public void updateBar() {
    bossBar.name(Component.text("(" + this.wave + "/10) Remaining monsters » " + entities.size()));
    float progress = this.getProgress();

    Bukkit.broadcast(Component.text(progress));
    bossBar.progress(progress);
  }

  private float getProgress() {
    return (float) this.entities.size() / this.maxEntities;
  }
}
