package com.MegaCraft.Nations.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;
import com.MegaCraft.Nations.enums.PlayerRanks;
import com.MegaCraft.Nations.instances.Nation;
import com.MegaCraft.Nations.instances.NationMember;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.message.Message;

public class JoinCommand extends CCCommand {
	public JoinCommand() {
        super("join", "/nations join", "This command allows you to join a Nation.", 
        		new String[]{"join", "j"}, Nations.plugin);
    }

    public void execute(Player player, Message m, List<String> args, DiscordApi api) {
    	if(m != null) {
    		sendMessage(player, m, ChandCordMethods.dispatchChat("Please be in game to perform this command to recieve your monument.", "Nations"), false);
    	}
    	if(player != null ) {
    		if(args.size() == 2) {
    			Nation n = null;
    			for(Nation nation : Nation.nationInstances.values()) {
    				if(nation.getName().equalsIgnoreCase(args.get(1))) {
    					n = nation;
    				}
    				if(nation.getMembers().containsKey(player.getUniqueId())) {
    	        		player.sendMessage(ChandCordMethods.dispatchChat("You are already a member of a Nation.", "Nations"));
    	        		return;
    				}
    			}
    			if(n != null) {
    				new NationMember(player.getUniqueId(), PlayerRanks.Recruit, n, true);
	        		player.sendMessage(ChandCordMethods.dispatchChat("You have joined the " + args.get(1) + " nation.", "Nations"));
    			} else {
	        		player.sendMessage(ChandCordMethods.dispatchChat("Nation does not exist.", "Nations"));
    			}
    		} else {
        		player.sendMessage(ChandCordMethods.dispatchChat("To join a Nation use /nations join (nation)", "Nations"));
			}
    	}
    }
}

