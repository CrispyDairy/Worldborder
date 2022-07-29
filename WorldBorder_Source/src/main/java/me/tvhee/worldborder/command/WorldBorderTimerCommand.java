package me.tvhee.worldborder.command;

import me.tvhee.worldborder.WorldBorderPlugin;
import me.tvhee.worldborder.task.WorldBorderTimer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class WorldBorderTimerCommand implements CommandExecutor
{
    private final WorldBorderPlugin plugin;

    public WorldBorderTimerCommand(WorldBorderPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!sender.hasPermission("worldborder.command.timer"))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("plugin.messages.command.no-permission", "null")));
            return true;
        }

        WorldBorderTimer timer = plugin.getTimer();

        if(timer.isRunning())
        {
            timer.cancel();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("plugin.messages.command.border-task-cancelled", "null")));
        }
        else
        {
            timer.start();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("plugin.messages.command.border-task-resumed", "null")));
        }

        return true;
    }
}
