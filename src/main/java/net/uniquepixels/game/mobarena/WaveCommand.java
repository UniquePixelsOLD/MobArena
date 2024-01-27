package net.uniquepixels.game.mobarena;

import net.uniquepixels.game.mobarena.running.MobWaves;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WaveCommand implements CommandExecutor {

  private final MobWaves waves;

  public WaveCommand(MobWaves waves) {
    this.waves = waves;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

    if (!(commandSender instanceof Player player))
      return true;

    this.waves.respawnWave();
    player.sendMessage("Skipped wave");

    return true;
  }
}
