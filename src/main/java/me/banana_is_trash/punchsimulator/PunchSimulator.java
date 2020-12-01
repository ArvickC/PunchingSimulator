package me.banana_is_trash.punchsimulator;

import me.banana_is_trash.punchsimulator.Commands.Punch;
import me.banana_is_trash.punchsimulator.Commands.TabComplete.PunchTabCompelte;
import me.banana_is_trash.punchsimulator.Files.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.util.HashMap;
import java.util.UUID;

public final class PunchSimulator extends JavaPlugin implements Listener {
    // Vars
    public static String normal;
    public static String highlight;
    public static String prefix;

    @Override
    public void onEnable() {
        // Config Setup
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Var Setup
        normal = "&" + getConfig().getString("Message.Color.Normal");
        highlight = "&" + getConfig().getString("Message.Color.Highlight");
        prefix = getConfig().getString("Message.Prefix");

        // Data Setup
        Data.setup();
        Data.get().options().copyDefaults(true);
        Data.save();

        // Event Setup
        Bukkit.getPluginManager().registerEvents(this, this);

        // Command Setup
        getCommand("punch").setExecutor(new Punch());
        getCommand("punch").setTabCompleter(new PunchTabCompelte());

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + normal + " Plugin Loaded" + highlight + "!"));
    }

    @Override
    public void onDisable() {
        // Save
        Data.save();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + normal + " Saved Files, see you next time" + highlight + "!"));
    }

    @EventHandler
    // PlayerPunch Event (Not punching a player)
    public void onPunch(PlayerInteractEvent e) {
        // Checks if "e.getPlayer()" is punching (Left_Click)
        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            // Checks if "e.getPlayer()" is in the correct world
            if(e.getPlayer().getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                // Var Setup
                Player player = e.getPlayer();
                UUID playerID = e.getPlayer().getUniqueId();

                // Checks if Points aren't null
                if (Data.get().getInt(playerID.toString()) != 0 || Data.get().contains(playerID.toString())) {
                    // Add Point
                    Data.get().set(playerID.toString(), Data.get().getInt(playerID.toString()) + 1);
                    Data.save();

                    // Checks if +50
                    if (Data.get().getInt(playerID.toString())%50 == 0) {
                        // Checks LevelUp
                        if (Data.get().getInt(playerID.toString())%100 == 0) {
                            // Var Setup
                            Integer playerPoints = Data.get().getInt(playerID.toString());
                            Integer playerLevel = playerPoints / 100;

                            // Broadcast
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + highlight + player.getDisplayName() + normal + " is now level " + highlight + playerLevel + normal + " with " + highlight + playerPoints + normal + " points" + highlight + "!"));
                                }
                            }
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + normal + " You now have &" + highlight + Data.get().getInt(playerID.toString()) + normal + " points" + highlight + "!"));
                        return;
                    }
                }
                // Creates Section for player
                Data.get().set(playerID.toString(), 1);
                Data.save();
                Data.reload();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + normal + " You have started your " + highlight + "journey" + normal + " in " + highlight + "Punching Simulator" + normal + "!"));
                return;
                }
            }
        }

    @EventHandler
    // PlayerPunch Event (Punching Player)
    public void onPunchPlayer(EntityDamageByEntityEvent e) {
        // Checks if "e.getDamager()" is Player
        if(e.getDamager() instanceof Player) {
            // Checks if "e.getDamager()" is in the correct world
            if(e.getDamager().getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                // Var Setup
                Player player = (Player) e.getDamager();
                UUID playerID = e.getDamager().getUniqueId();

                // Checks if Points aren't null
                if (Data.get().getInt(playerID.toString()) != 0 || Data.get().contains(playerID.toString())) {
                    // Add Point
                    Data.get().set(playerID.toString(), Data.get().getInt(playerID.toString()) + 1);
                    Data.save();

                    // Checks if +50
                    if (Data.get().getInt(playerID.toString()) % 50 == 0) {
                        // Checks LevelUp
                        if (Data.get().getInt(playerID.toString()) % 100 == 0) {
                            // Var Setup
                            Integer playerPoints = Data.get().getInt(playerID.toString());
                            Integer playerLevel = playerPoints / 100;

                            // Broadcast
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + highlight + player.getDisplayName() + normal + " is now level " + highlight + playerLevel + normal + " with " + highlight + playerPoints + normal + " points" + highlight + "!"));
                                }
                            }
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + normal + " You now have &" + highlight + Data.get().getInt(playerID.toString()) + normal + " points" + highlight + "!"));
                        return;
                    }
                }
                // Creates Section for player
                Data.get().set(playerID.toString(), 1);
                Data.save();
                Data.reload();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + normal + " You have started your " + highlight + "journey" + normal + " in " + highlight + "Punching Simulator" + normal + "!"));
                return;
            }
        }
    }

}
