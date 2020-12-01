package me.banana_is_trash.punchsimulator;

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
    public static String normal;
    public static String highlight;
    private HashMap<UUID, Integer> strengthPoints = new HashMap<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        normal = "&" + getConfig().getString("Message.Color.Normal");
        highlight = "&" + getConfig().getString("Message.Color.Highlight");
        
        Data.setup();
        Data.get().addDefault("StrengthPoints", strengthPoints);
        Data.get().options().copyDefaults(true);
        Data.save();

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Message.Prefix") + normal + " Plugin Loaded" + highlight + "!"));
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPunch(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(e.getPlayer().getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                Player player = e.getPlayer();
                UUID playerID = e.getPlayer().getUniqueId();
                HashMap<UUID, Integer> points = (HashMap<UUID, Integer>) Data.get().get("StrengthPoints");
                if (points.get(playerID) != 0 || points.get(playerID) != null) {
                    points.replace(playerID, points.get(playerID) + 1);
                    if (points.get(playerID) % 50 == 0) {
                        if (points.get(playerID) % 100 == 0) {
                            Integer playerPoints = points.get(playerID);
                            Integer playerLevel = playerPoints / 100;
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Message.Prefix") + " " + highlight + player.getDisplayName() + normal + " is now level " + highlight + playerLevel + normal + " with " + highlight + playerPoints + normal + " points" + highlight + "!"));
                                }
                            }
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Message.Prefix") + normal + " You now have &" + highlight + points.get(playerID).toString() + normal + " points" + highlight + "!"));
                        return;
                    }
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPunchPlayer(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            if(e.getDamager().getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                Player player = (Player) e.getDamager();
                UUID playerID = e.getDamager().getUniqueId();
                HashMap<UUID, Integer> points = (HashMap<UUID, Integer>) Data.get().get("StrengthPoints");
                if (points.get(playerID) != 0 || points.get(playerID) != null) {
                    points.replace(playerID, points.get(playerID) + 1);
                    if (points.get(playerID) % 50 == 0) {
                        if (points.get(playerID) % 100 == 0) {
                            Integer playerPoints = points.get(playerID);
                            Integer playerLevel = playerPoints / 100;
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.getWorld().equals(Bukkit.getWorld("PunchingSim"))) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Message.Prefix") + " " + highlight + player.getDisplayName() + normal + " is now level " + highlight + playerLevel + normal + " with " + highlight + playerPoints + normal + " points" + highlight + "!"));
                                }
                            }
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Message.Prefix") + normal + " You now have &" + highlight + points.get(playerID).toString() + normal + " points" + highlight + "!"));
                        return;
                    }
                    return;
                }
            }
        }
    }

}
