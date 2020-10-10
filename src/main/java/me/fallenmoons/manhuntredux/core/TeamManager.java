package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamManager {

    private Main main;
    private Team hunters;
    private Team runners;

    public TeamManager(Main main) {
        this.main = main;
        hunters = new Team("Hunters", ChatColor.YELLOW);
        runners = new Team("Runners", ChatColor.BLUE);
    }

    public void joinTeam(String teamToJoin, Player player) {
        switch(teamToJoin) {
            case "hunters": {
                hunters.joinTeam(player);
                break;
            }
            case "runners": {
                runners.joinTeam(player);
                break;
            }
        }
    }

    public void leaveTeam(String teamToLeave, Player player) {
        switch(teamToLeave) {
            case "hunters": {
                hunters.leaveTeam(player);
                break;
            }
            case "runners": {
                runners.leaveTeam(player);
                break;
            }
        }
    }

    public ArrayList<Player> getTeamPlayers(String team) {
        switch(team) {
            case "hunters": {
                return hunters.getMembers();
            }
            case "runners": {
                return runners.getMembers();
            }
        }

        return null;
    }

    public int getTeamSize(String team) {
        switch(team) {
            case "hunters": {
                return hunters.getNumberOfMembers();
            }
            case "runners": {
                return runners.getNumberOfMembers();
            }
        }

        return 0;
    }

    public void clearTeams() {
        runners.clearTeam();
        hunters.clearTeam();
    }

    public Team getTeamFromPlayer(Player player) {
        for (Player p : hunters.getMembers()) {
            if (p.getName().equals(player.getName())) {
                return hunters;
            }
        }

        for (Player p : runners.getMembers()) {
            if (p.getName().equals(player.getName())) {
                return runners;
            }
        }

        return null;
    }

    public Team getHunters() {
        return hunters;
    }

    public Team getRunners() {
        return runners;
    }
}
