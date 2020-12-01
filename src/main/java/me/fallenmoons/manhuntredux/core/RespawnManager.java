package me.fallenmoons.manhuntredux.core;

import com.oracle.jrockit.jfr.EventDefinition;
import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class RespawnManager implements Listener {
    private Main main;
    private TeamManager teamManager;

    private HashMap<String, ItemStack> noDropItems;
    private HashMap<Player, Location> deadPlayers;

    public RespawnManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.noDropItems = new HashMap<String, ItemStack>();
        this.deadPlayers = new HashMap<>();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (!e.isBedSpawn() && e.getRespawnLocation().getWorld() != main.getCurrentWorld()) {
            e.setRespawnLocation(main.getCurrentWorld().getSpawnLocation());
        }

        if (!e.isBedSpawn() && deadPlayers.containsKey(player)) {
            if (deadPlayers.get(player).getWorld().getEnvironment() != World.Environment.NORMAL) {
                deadPlayers.remove(player);
                e.setRespawnLocation(main.getCurrentWorld().getSpawnLocation());
            } else {
//                player.sendMessage("Tried to spawn halfway");
                Location spawn = main.getCurrentWorld().getSpawnLocation();
                int x1 = spawn.getBlockX();
                int z1 = spawn.getBlockZ();
                Location death = deadPlayers.get(player);
                int x2 = death.getBlockX();
                int z2 = death.getBlockZ();

                int xx = (x1+x2)/2;
                int zz = (z1+z2)/2;
                Location nLoc = new Location(main.getCurrentWorld(), xx, main.getCurrentWorld().getHighestBlockYAt(xx, zz), zz);

                e.setRespawnLocation(nLoc);
                deadPlayers.remove(player);
            }
        }


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
            if (player.getBedSpawnLocation() == null) {
                deadPlayers.put(player, player.getLocation());
//                player.sendMessage("Added death");
            }
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

//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent e) {
//        e.getPlayer().teleport(main.getCurrentWorld().getSpawnLocation());
//    }
}
