package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.Round;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoundCommand implements CommandExecutor {
    private Main main;
    private Round round;
    private TeamManager teamManager;

    public RoundCommand(Main main) {
        this.main = main;
        this.round = main.getRound();
        this.teamManager = main.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 1 && args.length != 2) {
                return false;
            }

            if (args[0].toLowerCase().equals("start")) {
                if (teamManager.getRunners().getMembers().size() > 0 && teamManager.getHunters().getMembers().size() > 0) {
                    round.startRound();
                    sender.sendMessage(ChatColor.GREEN + "Starting round...");
                } else {
                    sender.sendMessage(ChatColor.RED + "There must be at least one player on each team to start round!");
                }

            } else if (args[0].toLowerCase().equals("stop")) {
                if (round.hasRoundStarted() == true) {
                    round.stopRound();
                    sender.sendMessage(ChatColor.GREEN + "Round ended!");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "No round started!");
                    return true;
                }
            } else if (args[0].toLowerCase().equals("view")) {
                sender.sendMessage(ChatColor.GREEN + "Round Countdown: " + ChatColor.GOLD + String.valueOf(round.getRoundCountdown()));
                sender.sendMessage(ChatColor.GREEN + "Round Lead Time: " + ChatColor.GOLD + String.valueOf(round.getRoundLeadTime()));
            } else if (args[0].toLowerCase().equals("countdown")) {
                //Check if valid number...
                int num;
                try {
                    num = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "\"" + args[1] + "\" is not a valid number!");
                    return false;
                }

                round.setRoundCountdown(Integer.parseInt(args[1]));
                sender.sendMessage(ChatColor.GREEN + "Set round countdown to " + args[1] + " seconds.");

                main.getConfigFile().set("countdown", num);
                main.saveConfig();
                return true;
            } else if (args[0].toLowerCase().equals("leadtime")) {
                //Check if valid number...
                int num;
                try {
                    num = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "\"" + args[1] + "\" is not a valid number!");
                    return false;
                }
                round.setRoundLeadTime(Integer.parseInt(args[1]));
                sender.sendMessage(ChatColor.GREEN + "Set round lead time to " + args[1] + " seconds.");
                main.getConfigFile().set("leadtime", num);
                main.saveConfig();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
