package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class RespawnManager implements Listener {
    private Main main;
    private TeamManager teamManager;

    private HashMap<String, ItemStack> noDropItems;

    public RespawnManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.noDropItems = new HashMap<String, ItemStack>();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        if (teamManager.getTeamFromPlayer(player) == teamManager.getHunters()) {
            if (noDropItems.containsKey(player.getName())) {
                player.getInventory().addItem(noDropItems.get(player.getName()));
                noDropItems.remove(player.getName());
            } else {
                ItemStack comp = new ItemStack(Material.COMPASS, 1);
                player.getInventory().addItem(comp);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        List<ItemStack> toDrop = e.getDrops();
        if (teamManager.getTeamFromPlayer(player) == teamManager.getHunters()) {
            for (ItemStack item : toDrop) {
                if (item.getType() == Material.COMPASS) {
                    if (item.hasItemMeta()) {
                        if(item.getItemMeta().getLore().contains("Player Tracker")) {
                            noDropItems.put(player.getName(), item);
                            toDrop.remove(item);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void noDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.getType() == Material.COMPASS) {
            if (item.hasItemMeta()) {
                if(item.getItemMeta().getLore().contains("Player Tracker")) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
