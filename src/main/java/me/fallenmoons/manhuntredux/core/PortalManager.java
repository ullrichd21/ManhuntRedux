package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class PortalManager {
    private Main main;
    private TeamManager teamManager;

    private HashMap<String, Location> endPlayers;

    public PortalManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.endPlayers = new HashMap<String, Location>();
    }

    public void addPlayer(Player p) {
        endPlayers.put(p.getName(), p.getLocation());
    }

    public void removePlayer(Player p) {
        String name = p.getName();
        if (endPlayers.containsKey(name)) {
            endPlayers.remove(name);
        }
    }

    public Location getPortalCoords(String name) {
        System.out.println("Map: " + endPlayers);
        if (endPlayers.containsKey(name)) {
            return endPlayers.get(name);
        } else {
            System.out.println(name + " not found in endPlayers");
            return null;
        }
    }

    public void clearPortalCoords() {
        endPlayers.clear();
    }
}
