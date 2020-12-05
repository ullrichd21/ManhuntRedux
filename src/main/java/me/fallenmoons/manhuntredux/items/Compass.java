package me.fallenmoons.manhuntredux.items;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.PortalManager;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Compass implements Listener {
    private Main main;
    private TeamManager teamManager;
    private PortalManager portals;

    public Compass(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.portals = main.getPortals();
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

        if (e.getHand() == EquipmentSlot.HAND) {
            if (p.getInventory().getItemInMainHand() != null) {
                ItemStack i = p.getInventory().getItemInMainHand();

                if (i.getType() == Material.COMPASS) {
                    ArrayList<Player> players = teamManager.getTeamPlayers("runners");
                    int num = 0;
                    if (players.size() > 0) {

                        ItemMeta trackerMeta = i.getItemMeta();
                        //Lore
                        if (!trackerMeta.hasLore()) {
                            List<String> trackerLore = new ArrayList<String>();
                            trackerLore.add("0");
                            trackerLore.add("Player Tracker");
                            trackerMeta.setLore(trackerLore);
                        } else {
                            num = Integer.parseInt(trackerMeta.getLore().get(0));
                        }

                        if (p.isSneaking() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                            num += 1;

                            if (num >= players.size()) {
                                num = 0;
                            }

                            if (players.get(num) == null || players.get(num).getLocation() == null) {
                                cantTrackError(p);
                                return;
                            }

                            trackerMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + players.get(num).getName());
                            trackerMeta.setLore(Arrays.asList(String.valueOf(num), "Player Tracker"));

                            CompassMeta trackerCompassMeta = null;
                            if (trackerMeta instanceof CompassMeta) trackerCompassMeta = (CompassMeta) trackerMeta;
                            if(trackerCompassMeta != null) {
                                trackerCompassMeta.setLodestoneTracked(false);
                                trackerCompassMeta.setLodestone(players.get(num).getLocation());
                            }
                            i.setItemMeta(trackerCompassMeta);
                            p.playSound(p.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 1, 1);
                        } else {
                            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                if (players.get(num) == null || players.get(num).getLocation() == null) {
                                    cantTrackError(p);
                                    return;
                                }

                                trackerMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + players.get(num).getName());
                                trackerMeta.setLore(Arrays.asList(String.valueOf(num), "Player Tracker"));

                                CompassMeta trackerCompassMeta = null;
                                if (trackerMeta instanceof CompassMeta) trackerCompassMeta = (CompassMeta) trackerMeta;
                                if(trackerCompassMeta != null) {
                                    trackerCompassMeta.setLodestoneTracked(false);

                                    if(!p.getWorld().getName().contains("_the_end") && players.get(num).getWorld().getName().contains("_the_end")) {
                                        trackerCompassMeta.setLodestone(portals.getPortalCoords(players.get(num).getName()));
                                    } else {
                                        trackerCompassMeta.setLodestone(players.get(num).getLocation());
                                    }
                                }
                                i.setItemMeta(trackerCompassMeta);

                                if (players.get(num).getWorld() != p.getWorld()) {
                                    p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
                                } else {
                                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                }
                            }
                        }
                    } else {
                        cantTrackError(p);
                        return;
                    }
                }
            }
        }
    }

    private void cantTrackError(Player p) {
        p.sendMessage(ChatColor.RED + "Can't track!");
    }
}
