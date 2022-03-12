package ru.alexpshkov.reportplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.alexpshkov.reportplugin.ReportPlugin;

public class ReportPluginCommand implements CommandExecutor {
    private final ReportPlugin reportplugin;

    public ReportPluginCommand(ReportPlugin reportplugin) {
        this.reportplugin = reportplugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()){
            sender.sendMessage(ChatColor.RED + "U don't have permissions to do it!");
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(ChatColor.RED + String.format("Use /%s <args>", label));
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")){
            reportplugin.handleReload();
            sender.sendMessage(ChatColor.GREEN + "Plugin successfully reloaded");
            return true;
        }
        return false;
    }
}
