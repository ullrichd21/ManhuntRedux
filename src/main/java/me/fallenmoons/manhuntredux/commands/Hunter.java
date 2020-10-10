package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hunter implements CommandExecutor {
    private Main main;
    private TeamManager teamManager;

    public Hunter(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 2) {
                return false;
            }

            if (args[0].toLowerCase().equals("add")) {
                Player player = null;
                for (Player p : main.getServer().getOnlinePlayers()) {
                    if (p.getName().equals(args[1])) {
                        player = p;
                        break;
                    }
                }

                if (player == null) {
                    return false;
                }

                for(Player p : teamManager.getTeamPlayers("hunters")) {
                    if (p.getName().equals(args[1])) {
                        player.sendMessage(ChatColor.RED + "That player is already on this team!");
                        return false;
                    }
                }

                for(Player p : teamManager.getTeamPlayers("runners")) {
                    if (p.getName().equals(args[1])) {
                        player.sendMessage(ChatColor.RED + "That player is already on the " + ChatColor.BLUE + "RUNNERS" + ChatColor.RED + " team!");
                        return false;
                    }
                }

                teamManager.joinTeam("hunters", (Player) player);
                player.sendMessage(ChatColor.GREEN + "Player successfully joined " + ChatColor.YELLOW + "Hunters" + ChatColor.GREEN + "!");
                return true;
            } else if (args[0].toLowerCase().equals("rem") || args[0].toLowerCase().equals("remove")) {
                Player player = null;
                for (Player p : main.getServer().getOnlinePlayers()) {
                    if (p.getName().equals(args[1])) {
                        player = p;
                        break;
                    }
                }

                if (player == null) {
                    return false;
                }

                for(Player p : teamManager.getTeamPlayers("hunters")) {
                    if (p.getName().equals(args[1])) {
                        teamManager.leaveTeam("hunters", (Player) player);
                        player.sendMessage(ChatColor.GREEN + "Player successfully removed from " + ChatColor.YELLOW + "HUNTERS" + ChatColor.GREEN + "!" );
                        return true;
                    }
                }
                player.sendMessage(ChatColor.RED + "Player not found on team!");
                return false;
            } else {
                return false;
            }
        }

        return false;
    }
}
