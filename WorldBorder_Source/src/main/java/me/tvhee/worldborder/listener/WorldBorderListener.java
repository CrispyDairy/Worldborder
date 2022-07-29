package me.tvhee.worldborder.listener;

import me.tvhee.worldborder.WorldBorderPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.world.WorldLoadEvent;

public final class WorldBorderListener implements Listener
{
    private final WorldBorderPlugin plugin;

    public WorldBorderListener(WorldBorderPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDie(PlayerDeathEvent event)
    {
        double blocksToRemove = plugin.getConfig().getDouble("plugin.player-dies.remove-radius", 1);
        long speed = plugin.getConfig().getLong("plugin.player-dies.speed", 1L);
        plugin.changeBorder(blocksToRemove, speed, false);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldLoad(WorldLoadEvent e)
    {
        e.getWorld().getWorldBorder().setSize(Bukkit.getWorlds().get(0).getWorldBorder().getSize());
    }
}
