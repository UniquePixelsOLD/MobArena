package net.uniquepixels.game.mobarena;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import net.uniquepixels.game.Game;
import net.uniquepixels.game.GameCountdown;
import net.uniquepixels.game.GameState;
import net.uniquepixels.game.config.BlockState;
import net.uniquepixels.game.config.GameType;
import net.uniquepixels.game.config.StateConfig;
import net.uniquepixels.game.mobarena.running.MobWaves;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class MobArenaGame extends Game {
  private final GameCountdown startCountdown = new GameCountdown();
  private final MobWaves mobWaves = new MobWaves();

  public MobArenaGame(Plugin plugin) {
    super(20, 2, 2, Material.ZOMBIE_HEAD, GameType.MOB_ARENA, plugin);

    Bukkit.getPluginManager().registerEvents(mobWaves, plugin);

  }

  @Override
  public StateConfig onWaitingState() {
    return StateConfig.all(GameState.WAITING);
  }

  @Override
  public StateConfig onRunningState() {
    return new StateConfig(GameState.RUNNING, BlockState.BLOCK_BREAK, BlockState.BLOCK_PLACE, BlockState.FOOD);
  }

  public MobWaves getMobWaves() {
    return mobWaves;
  }

  @Override
  public void onJoin(PlayerJoinEvent event) {

    Player player1 = event.getPlayer();

    player1.teleport(new Location(player1.getWorld(), -52, 34, 9, -39.8f, 14.5f));
    player1.setGameMode(GameMode.ADVENTURE);

    if (this.getRequiredPlayers() == Bukkit.getOnlinePlayers().size())
      this.startCountdown().runCountdown(player -> {

        switch (this.startCountdown.getStartCountdown()) {
          case 60, 50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 -> {
            player.sendTitlePart(TitlePart.TITLE, Component.text("Game starts in"));
            player.sendTitlePart(TitlePart.SUBTITLE, Component.text(this.startCountdown.getStartCountdown()));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.AMBIENT, 30f, 1f);
            player.sendMessage("Game starts in " + this.startCountdown.getStartCountdown());

            if (this.startCountdown.getStartCountdown() == 0) {
              setCurrentState(GameState.RUNNING);
              mobWaves.start();
            }
          }
        }

      });

  }

  @Override
  public GameCountdown startCountdown() {
    return this.startCountdown;
  }

  @Override
  public void onQuit(PlayerQuitEvent playerQuitEvent) {

  }

  @Override
  public StateConfig onEndingState() {
    return StateConfig.all(GameState.ENDING);
  }
}
