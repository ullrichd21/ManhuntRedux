package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Coordinates implements CommandExecutor {
    private Main main;

    public Coordinates(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 0) {
                return false;
            }

            Player player = (Player) sender;

            Location loc = player.getLocation();

            player.chat("My Location: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
            return true;
        }

        return false;
    }
}
