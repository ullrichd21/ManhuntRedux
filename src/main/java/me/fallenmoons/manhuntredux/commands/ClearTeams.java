package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearTeams implements CommandExecutor {
    private Main main;
    private TeamManager teamManager;

    public ClearTeams(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 0) {
                return false;
            }

            teamManager.clearTeams();
            sender.sendMessage(ChatColor.GREEN + "Successfully cleared all teams!");
            return true;
        }

        return false;
    }
}
