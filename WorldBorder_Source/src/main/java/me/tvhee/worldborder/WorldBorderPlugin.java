package me.tvhee.worldborder;

import me.tvhee.worldborder.command.WorldBorderResetCommand;
import me.tvhee.worldborder.command.WorldBorderTimerCommand;
import me.tvhee.worldborder.listener.WorldBorderListener;
import me.tvhee.worldborder.task.WorldBorderTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class WorldBorderPlugin extends JavaPlugin
{
    private WorldBorderTimer timer;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        if(!getConfig().contains("plugin.first-use"))
        {
            getConfig().set("plugin.first-use", System.currentTimeMillis());
            saveConfig();
            resetBorder();
        }

        this.timer = new WorldBorderTimer(this);
        this.timer.start();

        getServer().getPluginCommand("worldbordertimer").setExecutor(new WorldBorderTimerCommand(this));
        getServer().getPluginCommand("worldborderreset").setExecutor(new WorldBorderResetCommand(this));
        getServer().getPluginManager().registerEvents(new WorldBorderListener(this), this);

        getLogger().info("has been enabled!");
        getLogger().info("made by Tvhee!");
    }

    @Override
    public void onDisable()
    {
        getLogger().info("has been disabled!");
        getLogger().info("made by Tvhee!");
    }

    public WorldBorderTimer getTimer()
    {
        return timer;
    }

    public List<World> getEnabledWorlds()
    {
        List<World> worlds = new ArrayList<>();

        for(String worldName : getConfig().getStringList("plugin.worlds"))
        {
            World world = getServer().getWorld(worldName);

            if(world != null)
                worlds.add(world);
        }

        return worlds;
    }

    public void resetBorder()
    {
        double minSize = getConfig().getDouble("plugin.min-size", 1);

        for(World world : getEnabledWorlds())
        {
            WorldBorder worldBorder = world.getWorldBorder();
            double size = worldBorder.getSize();

            if(size <= minSize)
                continue;

            worldBorder.setSize(minSize, 1);
        }

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin.messages.announcement.min-size", "")));
    }

    public void changeBorder(double blocks, long speed, boolean grow)
    {
        if(!timer.isRunning())
            return;

        double minSize = getConfig().getDouble("plugin.min-size", 1);
        double maxSize = getServer().getMaxWorldSize();
        boolean messageSent = false;

        for(World world : getEnabledWorlds())
        {
            WorldBorder worldBorder = world.getWorldBorder();
            double size = worldBorder.getSize();

            if((size >= maxSize && grow) || (size <= minSize && !grow))
            {
                messageSent = true;
                continue;
            }

            double newSize = grow ? size + blocks : size - blocks;

            if(newSize >= maxSize)
            {
                newSize = maxSize;

                if(!messageSent)
                {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin.messages.announcement.max-size", "")));
                    messageSent = true;
                }
            }
            else if(newSize <= minSize)
            {
                newSize = minSize;

                if(!messageSent)
                {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin.messages.announcement.min-size", "")));
                    messageSent = true;
                }
            }

            worldBorder.setSize(newSize, speed);
        }

        if(!messageSent)
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin.messages.announcement." + (grow ? "grow" : "shrink"), "")));
    }
}
