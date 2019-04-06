package com.MegaCraft.Nations.commands;

import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import com.MegaCraft.ChandCord.command.Commands;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;

public class NationsCommand {
    public NationsCommand(Nations plugin) {
		ArrayList<String> commandAliases = new ArrayList<String>();
		commandAliases.add("nations");
		commandAliases.add("n");
		Commands.commandAliases.put(Nations.plugin, commandAliases);
		
        new CreateCommand();
        new PromoteCommand();
        new JoinCommand();
        new LeaveCommand();
        new DisbandCommand();
        new HelpCommand();
    }
    
    public static void disconnect() {
    	for(Plugin plugin : Commands.commandAliases.keySet()) {
    		if(plugin == Nations.plugin) {
    			Commands.commandAliases.remove(plugin);
    		}
    	}
    	for(Plugin plugin : CCCommand.pluginInstances.keySet()) {
    		if(plugin == Nations.plugin) {
    			CCCommand.pluginInstances.remove(plugin);
    		}
    	}
    }
}

