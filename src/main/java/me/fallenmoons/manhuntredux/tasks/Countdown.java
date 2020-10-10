package me.fallenmoons.manhuntredux.tasks;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.core.Round;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Main main;
    private Round round;

    public Countdown(Main main, Round round) {
        this.main = main;
        this.round = round;
    }

    @Override
    public void run() {
        if (round.hasCountdownStarted() == true) {
            round.countdown();
        } else if (round.hasLeadCountdownStarted() == true) {
            round.leadCountdown();
        }

    }
}
