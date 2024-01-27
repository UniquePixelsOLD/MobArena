package net.uniquepixels.game.mobarena;

import net.uniquepixels.game.GameEngine;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class MobArena extends JavaPlugin {

  private MobArenaGame game;

  @Override
  public void onEnable() {

    this.game = new MobArenaGame(this);
    getPlugin(GameEngine.class).setGame(game);

    Bukkit.getPluginManager().registerEvents(this.game, this);

    getCommand("wave").setExecutor(new WaveCommand(this.game.getMobWaves()));

    for (World world : Bukkit.getWorlds()) {

      world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, true);
      world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
      world.setGameRule(GameRule.DO_FIRE_TICK, false);
      world.setGameRule(GameRule.MOB_GRIEFING, false);
      world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
      world.setGameRule(GameRule.DO_ENTITY_DROPS, false);

    }

  }
}
