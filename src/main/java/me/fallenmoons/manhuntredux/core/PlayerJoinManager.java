package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerJoinManager implements Listener {
    private Main main;
    private TeamManager teamManager;
    private Round round;

    public PlayerJoinManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.round = main.getRound();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        for (String name : teamManager.getHunters().getPlayerNames()) {
            if (player.getName().equals(name)) {
                teamManager.getHunters().addMember(player);
            }
        }

        for (String name : teamManager.getRunners().getPlayerNames()) {
            if (player.getName().equals(name)) {
                teamManager.getRunners().addMember(player);
            }
        }
    }
}
