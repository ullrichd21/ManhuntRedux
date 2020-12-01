package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class RandomizeTeams implements CommandExecutor {
    private Main main;
    private TeamManager teamManager;

    public RandomizeTeams(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 0) {
                return false;
            }
            Random rand = new Random();

//            int numPlayers = main.getServer().getOnlinePlayers().size();

            teamManager.clearTeams();


            ArrayList<Player> playersLeft = new ArrayList<>();
            for (Player p : main.getServer().getOnlinePlayers()) {
                playersLeft.add(p);
            }

            int i = 0;
            while(playersLeft.size() > 0) {
//                sender.sendMessage("i = " + i);
                i++;
                Player p = playersLeft.get(rand.nextInt(playersLeft.size()));
                if (i % 2 == 0) {
                    teamManager.getRunners().addMember(p);
                } else {
                    teamManager.getHunters().addMember(p);
                }
                playersLeft.remove(p);
            }

            sender.sendMessage(ChatColor.GREEN + "Successfully randomized all teams!");
            return true;
        }

        return false;
    }
}
