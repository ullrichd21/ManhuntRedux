package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class RespawnManager implements Listener {
    private Main main;
    private TeamManager teamManager;

    public RespawnManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        if (teamManager.getTeamFromPlayer(player) == teamManager.getHunters()) {
            ItemStack comp = new ItemStack(Material.COMPASS, 1);
            player.getInventory().addItem(comp);
        }
    }
}
