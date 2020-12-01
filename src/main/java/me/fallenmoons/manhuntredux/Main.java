package me.fallenmoons.manhuntredux;

import me.fallenmoons.manhuntredux.core.*;
import me.fallenmoons.manhuntredux.items.Compass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    private TeamManager teamManager;
    private Round round;
    private PortalManager portals;
    private World currentWorld;

    @Override
    public void onEnable() {
        // Plugin startup logic

        currentWorld = getServer().getWorlds().get(0);

        for (Player p : getServer().getOnlinePlayers()) {
            p.setDisplayName(p.getName());
            p.setPlayerListName(p.getName());
            p.setPlayerListHeader("");
        }


        //Create Team Manager
        teamManager = new TeamManager(this);

        //Create Round
        round = new Round(this);

        //Create PortalManager
        portals = new PortalManager(this);

        //Register Commands
        new CommandsManager(this);

        //Register Listeners
        getServer().getPluginManager().registerEvents(new Compass(this), this);
        getServer().getPluginManager().registerEvents(new RespawnManager(this), this);
        getServer().getPluginManager().registerEvents(new MovementManager(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinManager(this), this);
        getServer().getPluginManager().registerEvents(new DragonDeath(this), this);
        getServer().getPluginManager().registerEvents(new ChatManager(this), this);
        getServer().getPluginManager().registerEvents(new PortalEvent(this), this);

    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public Round getRound() {return round;}

    public PortalManager getPortals() {return portals;}

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }
}