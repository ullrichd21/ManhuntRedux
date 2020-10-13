package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

        Player sender = e.getPlayer();

        if (teamManager.playerOnTeam(sender)) {
            Set<Player> recip = e.getRecipients();
            Team senderTeam = teamManager.getTeamFromPlayer(e.getPlayer());
            String msg = e.getMessage();
            for (Player p : recip) {
                if (teamManager.getTeamFromPlayer(p) != senderTeam) {
                    e.setCancelled(true);
                }
            }

//            for (Player p : senderTeam.getMembers()) {
////                p.sendMessage("[" + senderTeam.getTeamColor() + sender.getName() + "] " + msg);
//            }
        }
    }
}
