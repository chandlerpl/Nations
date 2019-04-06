package com.MegaCraft.Nations.instances;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.MegaCraft.ChandCord.ChandCord;
import com.MegaCraft.ChandCord.ChandCordMethods;
import com.MegaCraft.Nations.NationsMethods;
import com.MegaCraft.Nations.enums.PlayerRanks;

public class Nation {
	private String name;
	private final UUID uuid;
	private final UUID founder;
	private Map<UUID, NationMember> members;
	private String description;
	
	public static Map<UUID, Nation> nationInstances;
	
	static {
		nationInstances =  new HashMap<UUID, Nation>();
	}

	public Nation(UUID uuid, String name, UUID founder, boolean sql) {
		this.name = name;
		if(uuid != null) {
			this.uuid = uuid;
		} else {
			this.uuid = UUID.randomUUID();
		}
		this.founder = founder;
		this.members = new HashMap<UUID, NationMember>();
		this.description = "";
		if(!ChandCord.channelIDs.containsKey(name.toLowerCase())) {
			ChandCordMethods.createChannel(name, "Nations", ChandCord.getApi());
		}
		nationInstances.put(this.uuid, this);
		if(sql) {
			new NationMember(founder, PlayerRanks.Leader, this, true);
			NationsMethods.addNation(this);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public UUID getFounder() {
		return founder;
	}
	
	public Map<UUID, NationMember> getMembers() {
		return members;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
}
