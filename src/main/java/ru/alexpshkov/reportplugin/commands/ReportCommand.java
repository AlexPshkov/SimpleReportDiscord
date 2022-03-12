package ru.alexpshkov.reportplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.alexpshkov.reportplugin.ReportPlugin;
import ru.alexpshkov.reportplugin.discord.DiscordWebHook;
import ru.alexpshkov.reportplugin.discord.objects.EmbedObject;

import java.awt.*;
import java.io.IOException;

public class ReportCommand implements CommandExecutor {
    private final ReportPlugin reportplugin;

    public ReportCommand(ReportPlugin reportplugin) {
        this.reportplugin = reportplugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }

        DiscordWebHook discordWebHook = reportplugin.getDiscordWebHook();
        if(discordWebHook == null) {
            sender.sendMessage(ChatColor.RED + "Error. Discord webHook isn't loaded");
            return true;
        }

        discordWebHook.setUsername(sender.getName());
        discordWebHook.setAvatarUrl(String.format("https://minotar.net/avatar/%s/512.png", sender.getName()));

        EmbedObject embedObject = new EmbedObject();
        embedObject.setColor(Color.red);
        embedObject.setDescription(stringBuilder.toString());

        Bukkit.getScheduler().runTaskAsynchronously(reportplugin, () -> {
            try {
                discordWebHook.addEmbed(embedObject).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sender.sendMessage(ChatColor.GREEN + "Message successfully send to the discord");
        return true;
    }
}
