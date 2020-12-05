package me.fallenmoons.manhuntredux.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {

    private String teamName;
    private ArrayList<String> playerNames;
    private ChatColor teamColor;

    public Team(String teamName, ChatColor teamColor) {
        this.teamName = teamName;
        this.teamColor = teamColor;
        playerNames = new ArrayList<String>();
    }

    public ArrayList<Player> getMembers() {
        ArrayList<Player> members = new ArrayList<>();
        for(String name : playerNames) {
            members.add(Bukkit.getPlayer(name));
        }
        return members;
    }

    public ArrayList<String> getPlayerNames() { return playerNames; }

    public int getNumberOfMembers() {
        return playerNames.size();
    }

    public void addMember(Player player) {
        if (!playerNames.contains(player.getName())) {
            playerNames.add(player.getName());
        }
        player.setDisplayName(teamColor + player.getDisplayName() + ChatColor.WHITE);
        player.setPlayerListName(teamColor + player.getDisplayName() + ChatColor.WHITE);
        player.setPlayerListHeader(teamColor + teamName);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ChatColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(ChatColor teamColor) {
        this.teamColor = teamColor;
    }

    public void joinTeam(Player player) {
        playerNames.add(player.getName());
        player.setDisplayName(teamColor + player.getDisplayName() + ChatColor.WHITE);
        player.setPlayerListName(teamColor + player.getDisplayName() + ChatColor.WHITE);
        player.setPlayerListHeader(teamColor + teamName);
    }

    public void leaveTeam(Player player) {
        playerNames.remove(player.getName());
        player.setDisplayName(ChatColor.stripColor(player.getDisplayName()));
        player.setPlayerListName(ChatColor.stripColor(player.getDisplayName()));
        player.setPlayerListHeader("");
    }

    public void clearTeam() {
        for (String name : playerNames) {
            Player p = Bukkit.getPlayer(name);
            p.setDisplayName(ChatColor.stripColor(p.getDisplayName()));
            p.setPlayerListName(ChatColor.stripColor(p.getDisplayName()));
            p.setPlayerListHeader("");
        }
        playerNames.clear();
    }
}
