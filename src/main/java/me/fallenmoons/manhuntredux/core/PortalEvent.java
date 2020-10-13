package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class PortalEvent implements Listener {
    private Main main;
    private PortalManager portals;

    public PortalEvent(Main main) {
        this.main = main;
        this.portals = main.getPortals();
    }

    @EventHandler
    public void onEnterEnd(PlayerPortalEvent e) {
        System.out.println("Portal Event");
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            Player player = e.getPlayer();
            Location loc = player.getLocation();
            portals.addPlayer(player);
        }
    }

    @EventHandler
    public void onChangedWorld(PlayerChangedWorldEvent e) {
        if (e.getFrom().getName().contains("_the_end")) {
            portals.removePlayer(e.getPlayer());
        }
    }


}
