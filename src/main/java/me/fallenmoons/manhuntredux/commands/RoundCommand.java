package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.Round;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoundCommand implements CommandExecutor {
    private Main main;
    private Round round;

    public RoundCommand(Main main) {
        this.main = main;
        this.round = main.getRound();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 1 && args.length != 2) {
                return false;
            }

            if (args[0].toLowerCase().equals("start")) {
                round.startRound();
                sender.sendMessage(ChatColor.GREEN + "Starting round...");
            } else if (args[0].toLowerCase().equals("stop")) {
                if (round.hasRoundStarted() == true) {
                    round.stopRound();
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
                    num = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "\"" + args[2] + "\" is not a valid number!");
                    return false;
                }

                round.setRoundCountdown(Integer.parseInt(args[2]));
                sender.sendMessage(ChatColor.GREEN + "Set round countdown to " + args[2] + " seconds.");
                return true;
            } else if (args[0].toLowerCase().equals("leadtime")) {
                //Check if valid number...
                int num;
                try {
                    num = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "\"" + args[2] + "\" is not a valid number!");
                    return false;
                }
                round.setRoundLeadTime(Integer.parseInt(args[2]));
                sender.sendMessage(ChatColor.RED + "\"" + args[2] + "\" is not a valid number!");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
