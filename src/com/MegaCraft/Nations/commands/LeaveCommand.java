package com.MegaCraft.Nations.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;
import com.MegaCraft.Nations.NationsMethods;
import com.MegaCraft.Nations.instances.Nation;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.message.Message;

public class LeaveCommand extends CCCommand {
	public LeaveCommand() {
        super("leave", "/nations leave", "This command allows you to leave a Nation.", 
        		new String[]{"leave", "l"}, Nations.plugin);
    }

    public void execute(Player player, Message m, List<String> args, DiscordApi api) {
    	if(m != null) {
    		sendMessage(player, m, ChandCordMethods.dispatchChat("Please be in game to perform this command to recieve your monument.", "Nations"), false);
    	}
    	if(player != null ) {
    		if(args.size() == 1) {
    			for(Nation nation : Nation.nationInstances.values()) {
    				if(nation.getMembers().containsKey(player.getUniqueId())) {
    					if(NationsMethods.removeNationMember(nation, player)) {
    						nation.getMembers().remove(player.getUniqueId());
    						ChandCordMethods.removeUserToRole(player, nation.getName(), api);
        	        		player.sendMessage(ChandCordMethods.dispatchChat("You have left the " + nation.getName() + " Nation.", "Nations"));
        	        		return;
    					} else {
    			    		sendMessage(player, m, ChandCordMethods.dispatchChat("There has been a problem. Please contact a member of staff.", "Nations"), false);
    			    		return;
    					}
    				}
    			}
    			sendMessage(player, m, ChandCordMethods.dispatchChat("You are not in a Nation.", "Nations"), false);
    		} else {
        		player.sendMessage(ChandCordMethods.dispatchChat("To leave a Nation use /nations leave", "Nations"));
			}
    	}
    }
}

