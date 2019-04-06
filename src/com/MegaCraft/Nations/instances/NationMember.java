package com.MegaCraft.Nations.instances;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.MegaCraft.ChandCord.ChandCord;
import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.Nations.NationsMethods;
import com.MegaCraft.Nations.enums.PlayerRanks;

public class NationMember {
	private final String name;
	private final UUID uuid;
	private final UUID nationUUID;
	private PlayerRanks rank;
	
	public static Map<UUID, NationMember> nationMembersInstances;
	
	static {
		nationMembersInstances =  new HashMap<UUID, NationMember>();
	}

	public NationMember(UUID player, PlayerRanks rank, Nation nation, boolean sql) {
		this.name = Bukkit.getOfflinePlayer(player).getName();
		this.uuid = player;
		this.nationUUID = nation.getUUID();
		this.rank = rank;
		
		ChandCordMethods.addUserToRole(Bukkit.getOfflinePlayer(player).getPlayer(), nation.getName(), ChandCord.getApi());
		nation.getMembers().put(this.uuid, this);
		if(sql) {
			NationsMethods.addNationMember(this);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public UUID getNationUUID() {
		return nationUUID;
	}
	
	public PlayerRanks getRank() {
		return rank;
	}
	
	public void setRank(PlayerRanks rank) {
		this.rank = rank;
	}
}
