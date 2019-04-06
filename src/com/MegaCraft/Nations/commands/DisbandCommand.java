package com.MegaCraft.Nations.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;
import com.MegaCraft.Nations.NationsMethods;
import com.MegaCraft.Nations.enums.PlayerRanks;
import com.MegaCraft.Nations.instances.Nation;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.message.Message;

public class DisbandCommand extends CCCommand {
	public DisbandCommand() {
        super("disband", "/nations disband", "This command allows you to disband your Nation.", 
        		new String[]{"disband"}, Nations.plugin);
    }

    public void execute(Player player, Message m, List<String> args, DiscordApi api) {
    	if(m != null) {
    		sendMessage(player, m, ChandCordMethods.dispatchChat("Please be in game to perform this command.", "Nations"), false);
    	}
    	if(player != null ) {
    		if(args.size() == 1) {
    			Nation n = null;
    			for(Nation nation : Nation.nationInstances.values()) {
    				n = nation;
    			}
    			if(n != null) {
    				if(n.getMembers().containsKey(player.getUniqueId()) && (n.getMembers().get(player.getUniqueId()).getRank() == PlayerRanks.Leader || player.getUniqueId() == n.getFounder())) {
    					String name = n.getName();
    					if(NationsMethods.removeNationMembers(n) && NationsMethods.removeNation(n)) {
        					Nation.nationInstances.remove(n.getUUID());
        					ChandCordMethods.deleteChannel(name, api);
    			    		sendMessage(player, m, ChandCordMethods.dispatchChat("Your Nation has been disbanded.", "Nations"), false);
    					} else {
    			    		sendMessage(player, m, ChandCordMethods.dispatchChat("There has been a problem. Please contact a member of staff.", "Nations"), false);
    					}
    				} else {
    					sendMessage(player, m, ChandCordMethods.dispatchChat("You are not a Leader so cannot disband this Nation.", "Nations"), false);
    				}
    			}
    		}
    	}
    }
}

