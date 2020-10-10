package me.fallenmoons.manhuntredux.core;

import me.fallenmoons.manhuntredux.Main;
import me.fallenmoons.manhuntredux.commands.*;

public class CommandsManager {

    private Main main;

    public CommandsManager(Main main) {
        this.main = main;

        main.getCommand("hunter").setExecutor(new Hunter(main));
        main.getCommand("runner").setExecutor(new Runner(main));
        main.getCommand("clearteams").setExecutor(new ClearTeams(main));
        main.getCommand("round").setExecutor(new RoundCommand(main));
//        main.getCommand("setcountdown").setExecutor(new SetCountdown(main));
//        main.getCommand("setleadcountdown").setExecutor(new SetLeadCountdown(main));
    }
}