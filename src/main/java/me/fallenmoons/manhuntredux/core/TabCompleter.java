package me.fallenmoons.manhuntredux.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        switch (command.getName()) {
            case "hunter": {
                if (args.length == 1) {
                    List<String> autoCompletes = new ArrayList<String>();
                    autoCompletes.add("add");
                    autoCompletes.add("remove");
                    return autoCompletes;
                }
                break;
            }

            case "runner": {
                if (args.length == 1) {
                    List<String> autoCompletes = new ArrayList<String>();
                    autoCompletes.add("add");
                    autoCompletes.add("remove");
                    return autoCompletes;
                }
                break;
            }

            case "round": {
                if (args.length == 1) {
                    List<String> autoCompletes = new ArrayList<String>();
                    autoCompletes.add("start");
                    autoCompletes.add("stop");
                    autoCompletes.add("view");
                    autoCompletes.add("countdown");
                    autoCompletes.add("leadtime");
                    return autoCompletes;
                }
                break;
            }
        }
        return null;
    }
}
