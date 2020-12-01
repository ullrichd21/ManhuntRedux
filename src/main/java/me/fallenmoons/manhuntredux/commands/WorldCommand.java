package me.fallenmoons.manhuntredux.commands;

import me.fallenmoons.manhuntredux.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.Random;

public class WorldCommand implements CommandExecutor {

    private Main main;

    public WorldCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {
                if (main.getRound().hasRoundStarted() == false ) {
                    Player player = (Player) sender;

                    String oldWorld = player.getWorld().getName();

                    String worldNum;
                    do {
                        worldNum = Integer.toString(new Random().nextInt(1000));
                    } while(main.getServer().getWorld("NewWorld" + worldNum) != null);

                    Location loc = new WorldCreator("NewWorld" + worldNum).createWorld().getSpawnLocation();
                    for (Player p : main.getServer().getOnlinePlayers()) {
                        p.teleport(loc);
                        main.setCurrentWorld(loc.getWorld());
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getLogger().warning("Trying to delete " + oldWorld + ", " + oldWorld + "_nether" + " and " + oldWorld + "_the_end");

                            File folder = Bukkit.getWorld(oldWorld).getWorldFolder();
                            Bukkit.unloadWorld(oldWorld, false);
                            deleteDirectory(folder);
                            if (Bukkit.getWorld(oldWorld + "_nether") != null) {
                                folder = Bukkit.getWorld(oldWorld + "_nether").getWorldFolder();
                                Bukkit.unloadWorld(oldWorld + "nether", false);
                                deleteDirectory(folder);
                            }
                            if (Bukkit.getWorld(oldWorld + "_the_end") != null) {
                                folder = Bukkit.getWorld(oldWorld + "_the_end").getWorldFolder();
                                Bukkit.unloadWorld(oldWorld + "_the_end", false);
                                deleteDirectory(folder);
                            }

                            player.sendMessage(ChatColor.GREEN + "Finished deleting old world.");
                        }
                    }.runTaskLater(main, 20*20);

                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You cannot use this command while a game is running!");
                    return false;
                }
            } else {
                return false;
            }

        }

        return true;
    }

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                } //end else
            }
        }
        return( path.delete() );
    }
}
