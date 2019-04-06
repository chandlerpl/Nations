package com.MegaCraft.Nations.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.MegaCraft.ChandCord.ChandCord;
import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.ChandCord.command.CCCommand;
import com.MegaCraft.Nations.Nations;
import com.MegaCraft.Nations.instances.Nation;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.message.Message;

public class CreateCommand extends CCCommand {
	public CreateCommand() {
        super("create", "/nations create", "This command allows you to create a Nation.", 
        		new String[]{"create", "c"}, Nations.plugin);
    }

    public void execute(Player player, Message m, List<String> args, DiscordApi api) {
    	if(m != null) {
    		sendMessage(player, m, ChandCordMethods.dispatchChat("Please be in game to perform this command to recieve your monument.", "Nations"), false);
    	}
    	if(player != null ) {
    		if(args.size() == 2) {
    			for(Nation nation : Nation.nationInstances.values()) {
    				if(nation.getMembers().containsKey(player.getUniqueId())) {
    	        		player.sendMessage(ChandCordMethods.dispatchChat("You are already a member of a Nation.", "Nations"));
    	        		return;
    				}
    				if(nation.getName().equalsIgnoreCase(args.get(1)) || ChandCord.channelIDs.containsKey(args.get(1).toLowerCase())) {
    	        		player.sendMessage(ChandCordMethods.dispatchChat("Nation name taken.", "Nations"));
    	        		return;
    				}
    			}
    			
    			new Nation(null, args.get(1), player.getUniqueId(), true);
    	        ItemStack is = new ItemStack(Material.BEACON, 1);
    	        ItemMeta im = is.getItemMeta();
    	        im.setDisplayName(args.get(1) + "'s Monument.");
    	        List<String> list = new ArrayList<String>();
    	        list.add("Place down to claim land for your faction.");
    	        im.setLore(list);
    	        im.addEnchant(Enchantment.DURABILITY, 10, true);
    	        
    	        is.setItemMeta(im);
    	        player.getInventory().addItem(is);
        		sendMessage(player, m, ChandCordMethods.dispatchChat("Your Nation has been founded.", "Nations"), false);
    		}
    	}
    }
}

