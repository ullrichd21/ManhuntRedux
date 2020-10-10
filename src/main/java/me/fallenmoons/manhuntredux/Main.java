package me.fallenmoons.manhuntredux;

import me.fallenmoons.manhuntredux.core.*;
import me.fallenmoons.manhuntredux.items.Compass;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    private TeamManager teamManager;
    private Round round;

    @Override
    public void onEnable() {
        // Plugin startup logic

        for (Player p : getServer().getOnlinePlayers()) {
            p.setDisplayName(p.getName());
            p.setPlayerListName(p.getName());
            p.setPlayerListHeader("");
        }

        //Create Team Manager
        teamManager = new TeamManager(this);

        //Create Round
        round = new Round(this);

        //Register Commands
        new CommandsManager(this);

        //Register Listeners
        getServer().getPluginManager().registerEvents(new Compass(this), this);
        getServer().getPluginManager().registerEvents(new RespawnManager(this), this);
        getServer().getPluginManager().registerEvents(new MovementManager(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinManager(this), this);
        getServer().getPluginManager().registerEvents(new DragonDeath(this), this);

    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public Round getRound() {return round;}
}

/*
Commands:
two teams
hunter and hunted



/hunteradd [player name] - adds to hunter team, gives tracking compass
/huntedadd [player name] - add to hunted team

/setcountdown [time]
/setleadtime [time]




 */