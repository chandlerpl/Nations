package com.MegaCraft.Nations.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;
import com.MegaCraft.Nations.enums.PlayerRanks;
import com.MegaCraft.Nations.instances.Nation;
import com.MegaCraft.Nations.instances.NationMember;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.message.Message;

public class PromoteCommand extends CCCommand {
	public PromoteCommand() {
        super("promote", "/nations promote", "This command is used to promote Nation members.", 
        		new String[]{"promote", "p"}, Nations.plugin);
    }

    public void execute(Player player, Message m, List<String> args, DiscordApi api) {
    	if(m != null) {
    		
    	}
    	if(player != null ) {
    		if(args.size() == 2) {
    			for(Nation nation : Nation.nationInstances.values()) {
    				UUID ouuid = Bukkit.getOfflinePlayer(args.get(1)).getUniqueId();
    				if(!nation.getMembers().containsKey(ouuid)) {
    	        		player.sendMessage(ChandCordMethods.dispatchChat("That player is not a member of the nation.", "Nations"));
    	        		return;
    				} else {
    					if(nation.getMembers().get(ouuid).getRank() == PlayerRanks.Leader) {
        	        		player.sendMessage(ChandCordMethods.dispatchChat("This player is already a Leader of the Nation.", "Nations"));
        	        		return;
    					}
    					PlayerRanks pr = nation.getMembers().get(ouuid).getRank();
    					PlayerRanks npr = PlayerRanks.getAsList().get(PlayerRanks.getAsList().indexOf(pr) + 1);
    					nation.getMembers().get(ouuid).setRank(npr);
    					player.sendMessage(ChandCordMethods.dispatchChat("You have premoted " + Bukkit.getOfflinePlayer(ouuid).getName() + " to " + npr.toString() + " in the Nation.", "Nations"));
    					if(Bukkit.getPlayer(ouuid) != null) {
    						Bukkit.getPlayer(ouuid).sendMessage(ChandCordMethods.dispatchChat("You have been premoted to " + npr.toString() + " in the Nation.", "Nations"));
    					}
    					for(NationMember member : nation.getMembers().values()) {
    						if(Bukkit.getOfflinePlayer(member.getUUID()).isOnline() && !member.getUUID().equals(ouuid) && !member.getUUID().equals(player.getUniqueId())) {
    							Bukkit.getPlayer(member.getUUID()).sendMessage(ChandCordMethods.dispatchChat(Bukkit.getOfflinePlayer(ouuid).getName() + " has been premoted to " + npr.toString() + " in the Nation.", "Nations"));
    						}
    					}
    				}
    			}
    		} else if(args.size() == 3) {
        			for(Nation nation : Nation.nationInstances.values()) {
        				UUID ouuid = Bukkit.getOfflinePlayer(args.get(1)).getUniqueId();
        				if(nation.getMembers().containsKey(ouuid)) {
        					PlayerRanks pr = nation.getMembers().get(ouuid).getRank();
	    					PlayerRanks npr = PlayerRanks.getAsList().get(PlayerRanks.getAsList().indexOf(PlayerRanks.valueOf(args.get(2))));
	    					
	        				if(nation.getMembers().containsKey(player.getUniqueId())) {
	        					if(PlayerRanks.getAsList().indexOf(nation.getMembers().get(player.getUniqueId()).getRank()) >= PlayerRanks.getAsList().indexOf(PlayerRanks.Officer)) {
	        						if(PlayerRanks.getAsList().indexOf(nation.getMembers().get(player.getUniqueId()).getRank()) < PlayerRanks.getAsList().indexOf(npr) || PlayerRanks.getAsList().indexOf(nation.getMembers().get(player.getUniqueId()).getRank()) < PlayerRanks.getAsList().indexOf(pr)) {
		        						sendMessage(player, m, ChandCordMethods.dispatchChat("You are trying to promote a member higher than you or a member below you to a higher rank than you.", "Nations"), false);
		        						return;
	        						}
	        					} else {
	        						sendMessage(player, m, ChandCordMethods.dispatchChat("You are not Officer or higher so cannot promote members.", "Nations"), false);
	        						return;
	        					}
	        				}
        					if(nation.getMembers().get(ouuid).getRank() == npr) {
            	        		player.sendMessage(ChandCordMethods.dispatchChat("This player is already a Leader of the Nation.", "Nations"));
            	        		return;
        					}
        	    			nation.getMembers().get(ouuid).setRank(npr);
        					player.sendMessage(ChandCordMethods.dispatchChat("You have premoted " + Bukkit.getOfflinePlayer(ouuid).getName() + " to " + npr.toString() + " in the Nation.", "Nations"));
        					if(Bukkit.getPlayer(ouuid) != null) {
        						Bukkit.getPlayer(ouuid).sendMessage(ChandCordMethods.dispatchChat("You have been premoted to " + npr.toString() + " in the Nation.", "Nations"));
        					}
        					for(NationMember member : nation.getMembers().values()) {
        						if(Bukkit.getOfflinePlayer(member.getUUID()).isOnline() && !member.getUUID().equals(ouuid) && !member.getUUID().equals(player.getUniqueId())) {
        							Bukkit.getPlayer(member.getUUID()).sendMessage(ChandCordMethods.dispatchChat(Bukkit.getOfflinePlayer(ouuid).getName() + " has been premoted to " + npr.toString() + " in the Nation.", "Nations"));
        						}
        					}
        					return;
        				}
        			}
	        		player.sendMessage(ChandCordMethods.dispatchChat("That player is not a member of the nation.", "Nations"));
    		} else {
        		player.sendMessage(ChandCordMethods.dispatchChat("To promote a member of a Nation use /nations promote (player) (rank)", "Nations"));
    		}
    	}
    }
}

