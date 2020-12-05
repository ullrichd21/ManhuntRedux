package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.tasks.Countdown;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class Round {
    private Main main;
    private TeamManager teamManager;

    private boolean roundStarted;
    private boolean countdownStarted;
    private boolean leadCountdownStarted;

    private boolean huntersCanMove;
    private boolean runnersCanMove;

    //For countdown
    private int roundCountdown;
    private int count;

    private BukkitTask countTask;

    private int roundLeadTime;

    private BossBar bar;

    public Round(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.roundCountdown = main.getConfigFile().getInt("countdown");
        this.roundLeadTime = main.getConfigFile().getInt("leadtime");
        this.roundStarted = false;
        this.runnersCanMove = true;
        this.huntersCanMove = true;
    }

    public Round(Main main, int roundCountdown, int roundLeadTime) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.roundCountdown = roundCountdown;
        this.roundLeadTime = roundLeadTime;
        this.roundStarted = false;
        this.runnersCanMove = true;
        this.huntersCanMove = true;
    }

    public boolean hasRoundStarted() {
        return roundStarted;
    }

    public boolean hasCountdownStarted() {
        return countdownStarted;
    }

    public boolean hasLeadCountdownStarted() {
        return leadCountdownStarted;
    }

    public boolean isHuntersCanMove() {
        return huntersCanMove;
    }

    public boolean isRunnersCanMove() {
        return runnersCanMove;
    }

    public void setRoundCountdown(int val) {
        roundCountdown = val;
    }

    public void setRoundLeadTime(int val) {
        roundLeadTime = val;
    }

    public int getRoundCountdown() {
        return roundCountdown;
    }

    public int getRoundLeadTime() {
        return roundLeadTime;
    }

    public void startRound() {
        startCountdown();
    }

    public void stopRound() {
        roundStarted = false;
        teamManager.getRunners().clearTeam();
        teamManager.getHunters().clearTeam();
        main.getPortals().clearPortalCoords();
    }

    private void startCountdown() {
        countdownStarted = true;
        count = roundCountdown;

        runnersCanMove = false;
        huntersCanMove = false;

        bar = Bukkit.createBossBar(ChatColor.GOLD + "Game Starts in " + count + "...", BarColor.YELLOW, BarStyle.SOLID);
        bar.setVisible(true);
        bar.removeAll();

        boolean first = true;

        for (Player p : main.getServer().getOnlinePlayers()) {
            bar.addPlayer(p);
            p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 100, 1);

            //Set up other things.
            float yaw = p.getLocation().getYaw();
            float pitch = p.getLocation().getPitch();
            p.teleport(p.getWorld().getSpawnLocation());
            p.getLocation().setPitch(pitch);
            p.getLocation().setYaw(yaw);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setExhaustion(0);
            p.getInventory().clear();
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 100));

            if (first == true) {
                p.getWorld().setTime(0);
                p.getWorld().setStorm(false);
                p.getWorld().setThundering(false);
                first = false;
            }


            if (teamManager.getTeamFromPlayer(p) == teamManager.getHunters()) {
                p.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
            }

            if (teamManager.getTeamFromPlayer(p) != teamManager.getRunners() && teamManager.getTeamFromPlayer(p) != teamManager.getHunters()) {
                p.setGameMode(GameMode.SPECTATOR);
            }
        }

        countTask = new Countdown(main, this).runTaskTimer(main, 20L, 20L);

    }

    private void startLeadCountdown() {
        leadCountdownStarted = true;
        count = roundLeadTime;

        bar = Bukkit.createBossBar(ChatColor.GOLD + "Hunters released in " + count + "...", BarColor.YELLOW, BarStyle.SOLID);
        bar.setVisible(true);
        bar.removeAll();

        for (Player p : main.getServer().getOnlinePlayers()) {
            bar.addPlayer(p);
            p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 100, 1);
        }

        countTask = new Countdown(main, this).runTaskTimer(main, 20L, 20L);
    }

    public void countdown() {
        count--;
        bar.setTitle(ChatColor.GOLD + "Game Starts in " + count + "...");
        bar.setProgress(count/(float) roundCountdown);
        for (Player p : main.getServer().getOnlinePlayers()) {
            bar.addPlayer(p);
        }

        if (count <= 5) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "Game starts in " + count + "...");
        }
        if (count == 0) {
            for (Player p : main.getServer().getOnlinePlayers()) {
                p.playSound(p.getWorld().getSpawnLocation(), Sound.EVENT_RAID_HORN, 100, 1);
                p.playSound(p.getLocation(), Sound.UI_TOAST_OUT, 100, 1);
            }
            bar.removeAll();
            Bukkit.broadcastMessage(ChatColor.GOLD + "GAME START!");
            countTask.cancel();
            countdownStarted = false;
            runnersCanMove = true;
            roundStarted = true;
            for (Player p : teamManager.getRunners().getMembers()) {
                for (PotionEffect pot : p.getActivePotionEffects()) {
                    p.removePotionEffect(pot.getType());
                }
            }

            startLeadCountdown();
        }
    }

    public void leadCountdown() {
        count--;
        bar.setTitle(ChatColor.GOLD + "Hunters released in " + count + "...");
        bar.setProgress(count/(float) roundLeadTime);
        for (Player p : main.getServer().getOnlinePlayers()) {
            bar.addPlayer(p);
        }

        if (count <= 5) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "Hunters released in " + count + "...");
        }
        if (count == 0) {
            for (Player p : main.getServer().getOnlinePlayers()) {
                p.playSound(p.getWorld().getSpawnLocation(), Sound.EVENT_RAID_HORN, 100, 1);
                p.playSound(p.getLocation(), Sound.UI_TOAST_OUT, 100, 1);
            }
            bar.removeAll();
            Bukkit.broadcastMessage(ChatColor.GOLD + "HUNTERS RELEASED!");
            countTask.cancel();
            leadCountdownStarted = false;
            huntersCanMove = true;

            for (Player p : teamManager.getHunters().getMembers()) {
                for (PotionEffect pot : p.getActivePotionEffects()) {
                    p.removePotionEffect(pot.getType());
                }
            }
        }
    }
}
