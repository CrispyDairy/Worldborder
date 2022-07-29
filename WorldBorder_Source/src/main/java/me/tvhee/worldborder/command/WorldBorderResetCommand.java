package me.tvhee.worldborder.command;

import me.tvhee.worldborder.WorldBorderPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class WorldBorderResetCommand implements CommandExecutor
{
    private final WorldBorderPlugin plugin;

    public WorldBorderResetCommand(WorldBorderPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!sender.hasPermission("worldborder.command.reset"))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("plugin.messages.command.no-permission", "null")));
            return true;
        }

        plugin.resetBorder();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("plugin.messages.command.border-reset", "null")));
        return true;
    }
}
