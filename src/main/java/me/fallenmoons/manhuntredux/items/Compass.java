package me.fallenmoons.manhuntredux.items;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Compass implements Listener {
    private Main main;
    private TeamManager teamManager;

    public Compass(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @EventHandler
    public void onCompassUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        boolean isHunter = false;
        for (Player pp : teamManager.getTeamPlayers("hunters")) {
            if (pp.getName().equals(p.getName())) {
                isHunter = true;
                break;
            }
        }

        if (isHunter == false) {
            return;
        }

        if (p.getInventory().getItemInMainHand() != null) {
            ItemStack i = p.getInventory().getItemInMainHand();

            if (i.getType() == Material.COMPASS) {
                ArrayList<Player> players = teamManager.getTeamPlayers("runners");
                int num = 0;
                if (players.size() > 0) {
                    if (p.isSneaking()) {
                        ItemMeta trackerMeta = i.getItemMeta();
                        //Lore
                        if (!trackerMeta.hasLore()) {
                            List<String> trackerLore = new ArrayList<String>();
                            trackerLore.add("0");
                            trackerMeta.setLore(trackerLore);
                        } else {
                            num = Integer.parseInt(trackerMeta.getLore().get(0));
                        }

                        num += 1;

                        if (num >= players.size()) {
                            num = 0;
                        }

                        trackerMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + players.get(num).getDisplayName());
                        trackerMeta.setLore(Arrays.asList(String.valueOf(num)));

                        CompassMeta trackerCompassMeta = null;
                        if (trackerMeta instanceof CompassMeta) trackerCompassMeta = (CompassMeta) trackerMeta;
                        if(trackerCompassMeta != null) {
                            trackerCompassMeta.setLodestoneTracked(false);
                            trackerCompassMeta.setLodestone(players.get(num).getLocation());
                        }
                        i.setItemMeta(trackerCompassMeta);
                        p.playSound(p.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 1, 1);
                    } else {
                        ItemMeta trackerMeta = i.getItemMeta();
                        //Lore
                        if (!trackerMeta.hasLore()) {
                            List<String> trackerLore = new ArrayList<String>();
                            trackerLore.add("0");
                            trackerMeta.setLore(trackerLore);
                        } else {
                            num = Integer.parseInt(trackerMeta.getLore().get(0));
                        }

                        trackerMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + players.get(num).getDisplayName());
                        trackerMeta.setLore(Arrays.asList(String.valueOf(num)));

                        CompassMeta trackerCompassMeta = null;
                        if (trackerMeta instanceof CompassMeta) trackerCompassMeta = (CompassMeta) trackerMeta;
                        if(trackerCompassMeta != null) {
                            trackerCompassMeta.setLodestoneTracked(false);
                            trackerCompassMeta.setLodestone(players.get(num).getLocation());
                        }
                        i.setItemMeta(trackerCompassMeta);

                        if (players.get(num).getWorld() != p.getWorld()) {
                            p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
                        } else {
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }


                    }

                } else {
                    p.sendMessage(ChatColor.RED + "Not enough runners to track!");
                    return;
                }


//                ItemMeta meta = i.getItemMeta();
//
//                if (players.size() == 0) {
//                    meta.setDisplayName(ChatColor.RED + "NO PLAYERS FOUND");
//                    meta.setLore(Arrays.asList(String.valueOf(0)));
//                    i.setItemMeta(meta);
//                    return;
//                }
//
//                int currentNum = Integer.parseInt(meta.getLore().get(0));
//                currentNum++;
//
//                if (currentNum >= players.size()) {
//                    currentNum = 0;
//                }
//
//                CompassMeta compMeta = null;
//
//                if (meta instanceof CompassMeta) compMeta = (CompassMeta) meta;
//                if (compMeta != null) {
//                    compMeta.setLodestoneTracked(false);
//                    compMeta.setLodestone(players.get(currentNum).getLocation());
//                }
//
//                meta.setDisplayName(ChatColor.GOLD + ChatColor.stripColor(players.get(currentNum).getDisplayName()));
//                meta.setLore(Arrays.asList(String.valueOf(currentNum)));
//                i.setItemMeta(compMeta);



            }
        }
    }
}
