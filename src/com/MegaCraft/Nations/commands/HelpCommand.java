package com.MegaCraft.Nations.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.message.Message;

public class HelpCommand extends CCCommand {
	public HelpCommand() {
        super("help", "/nations help", "Shows help on the Nations plugin.", 
        		new String[]{"help", "h"}, Nations.plugin);
    }

	public void execute(Player player, Message m, List<String> args, DiscordApi api) {
		if (args == null || args.size() == 1) {
			String message = "";
			String discordMessage = "";
			message += title("Nations") + "\n";
			
			discordMessage += title("Nations") + "\n";
			discordMessage += alignment("Number Of Commands") + CCCommand.getPluginCommands(Nations.plugin).size() + "\n";

			discordMessage += "\n" + title("Commands") + "\n";
			for(CCCommand command : CCCommand.getPluginCommands(Nations.plugin).values()) {
				String name = command.getName().substring(0, 1).toUpperCase() + command.getName().substring(1, command.getName().length());
				message += name + " - " + command.getDescription() + "\n";
				discordMessage += alignment(name) + "\"" + command.getDescription() + "\"\n";
			}
			message = (m != null) ? ChandCordMethods.mlConvertion(discordMessage) : message;

			sendMessage(player, m, message, false);
			return;
		}
		
		String arg = args.get(1);
		if(CCCommand.getPluginCommands(Nations.plugin).keySet().contains(arg.toLowerCase())) {
			CCCommand.getPluginCommands(Nations.plugin).get(arg).help(player, m);
		}
	}
}

