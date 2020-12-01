package me.banana_is_trash.punchsimulator.Commands.TabComplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PunchTabCompelte implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Var
        ArrayList<String> tab = new ArrayList<>();

        // First Arg
        if(args.length == 1) {
            // Add
            tab.add("help");
            tab.add("points");

            // Tab Complete
            return tab;
        }
        return null;
    }
}
