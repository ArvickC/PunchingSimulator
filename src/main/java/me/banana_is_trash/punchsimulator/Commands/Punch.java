package me.banana_is_trash.punchsimulator.Commands;

import me.banana_is_trash.punchsimulator.Files.Data;
import me.banana_is_trash.punchsimulator.PunchSimulator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Punch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks player
        if(sender instanceof Player) {
            if(sender.hasPermission("punch.use")) {
                // Checks
                if(args.length == 0) return false;
                if(args.length > 1) return false;

                // Help Handler
                if(args[0].equalsIgnoreCase("help")) {
                    helpCommand((Player) sender);
                }
                // Points Handler
                if(args[0].equalsIgnoreCase("points") || args[0].equalsIgnoreCase("pt")) {
                    pointsCommand((Player) sender);
                }

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.prefix + PunchSimulator.normal + " No Permission" + PunchSimulator.highlight + "!"));
            }
        }
        return false;
    }

    // Help Handler
    public void helpCommand(Player p) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.highlight + "*&m-------------------------------------&r*"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.normal + "             &lPunch Simulator             "));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.highlight + "*&m-------------------------------------&r*"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.highlight + "/punch help &f- " + PunchSimulator.normal + "Open the help menu"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.highlight + "/punch points &f- " + PunchSimulator.normal + "See your points"));
    }

    // Points Handler
    public void pointsCommand(Player p) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunchSimulator.prefix + PunchSimulator.normal + " You have " + PunchSimulator.highlight + Data.get().getInt(p.getUniqueId().toString()) + PunchSimulator.normal + " points" + PunchSimulator.highlight + "!"));
    }
}
