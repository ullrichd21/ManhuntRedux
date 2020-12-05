package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementManager implements Listener {
    private Main main;
    private TeamManager teamManager;
    private Round round;

    public MovementManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.round = main.getRound();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (teamManager.getTeamFromPlayer(player) != null && teamManager.getTeamFromPlayer(player) == teamManager.getHunters() && round.isHuntersCanMove() == false) {
            e.setCancelled(true);
        }

        if (teamManager.getTeamFromPlayer(player) != null && teamManager.getTeamFromPlayer(player) == teamManager.getRunners() && round.isRunnersCanMove() == false) {
            e.setCancelled(true);
        }
    }
}
