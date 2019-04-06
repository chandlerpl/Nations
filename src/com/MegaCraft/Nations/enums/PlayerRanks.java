package com.MegaCraft.Nations.enums;

import java.util.Arrays;
import java.util.List;

public enum PlayerRanks {
	Recruit, Member, Officer, Leader;
	
	public static List<PlayerRanks> getAsList() {
		return Arrays.asList(PlayerRanks.values());
	}
}
