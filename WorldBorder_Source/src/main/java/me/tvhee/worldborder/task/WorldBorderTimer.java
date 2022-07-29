package me.tvhee.worldborder.task;

import me.tvhee.worldborder.WorldBorderPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public final class WorldBorderTimer implements Runnable
{
    private final WorldBorderPlugin plugin;
    private BukkitTask task;

    public WorldBorderTimer(WorldBorderPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        double blocksToAdd = plugin.getConfig().getDouble("plugin.timer.add-radius", 1);
        long speed = plugin.getConfig().getLong("plugin.timer.speed", 1L);
        plugin.changeBorder(blocksToAdd, speed, true);
    }

    public void cancel()
    {
        if(this.task != null)
        {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
            this.task = null;
        }
    }

    public void start()
    {
        int period = plugin.getConfig().getInt("plugin.timer.period", 1);
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0L, period * 20L);
    }

    public boolean isRunning()
    {
        return this.task != null;
    }
}
