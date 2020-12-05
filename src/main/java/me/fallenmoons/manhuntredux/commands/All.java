package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class All implements CommandExecutor {
    private Main main;
    private TeamManager teamManager;

    public All(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length <= 0) {
                return false;
            }

            Player player = (Player) sender;

            String msg = "";

            for (int i = 0; i < args.length; i++) {
                msg += " " + args[i];
            }

            Bukkit.broadcastMessage("<" + player.getDisplayName() + ">" + ChatColor.GREEN + " [ALL]" + ChatColor.WHITE + msg);

            return true;
        }

        return false;
    }
}
