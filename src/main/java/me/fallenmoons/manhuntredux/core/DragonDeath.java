package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DragonDeath implements Listener {
    private Main main;
    private TeamManager teamManager;
    private Round round;

    public DragonDeath(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.round = main.getRound();
    }

    @EventHandler
    public void onDragonBeat(EntityDeathEvent e) {
        Entity dragon = e.getEntity();
        Player player = e.getEntity().getKiller();

        if (dragon.getType() == EntityType.ENDER_DRAGON && teamManager.getTeamFromPlayer(player) == teamManager.getRunners()) {
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.BLUE + "The RUNNERS have defeated the DRAGON and won!");
            round.stopRound();
        }
    }
}
