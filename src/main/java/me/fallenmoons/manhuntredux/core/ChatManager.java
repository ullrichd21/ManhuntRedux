package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;
import java.util.Set;

public class ChatManager implements Listener {

    private Main main;
    private TeamManager teamManager;

    //Teams
    private Team runners;
    private Team hunters;

    public ChatManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.runners = teamManager.getRunners();
        this.hunters = teamManager.getHunters();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player sender = (Player) e.getPlayer();
        System.out.println("Player Chat");
        if (teamManager.playerOnTeam(sender)) {
            Set<Player> recip = e.getRecipients();
            Iterator<Player> it = recip.iterator();
            Team senderTeam = teamManager.getTeamFromPlayer(e.getPlayer());
//            System.out.println("Player on team");
            while(it.hasNext()) {
                if (teamManager.getTeamFromPlayer(it.next()) != senderTeam) {
                    it.remove();
                }
            }

            e.setMessage(ChatColor.GRAY + "[TEAM] " + ChatColor.WHITE + e.getMessage());
//            for (Player p : e.getRecipients()) {
//                if (teamManager.getTeamFromPlayer(p) != senderTeam) {
//                    System.out.println(p.getName() + " cancelled");
//                    e.getRecipients().remove(p);
//                }
//            }

//            for (Player p : senderTeam.getMembers()) {
////                p.sendMessage("[" + senderTeam.getTeamColor() + sender.getName() + "] " + msg);
//            }
        }
    }
}
