package com.MegaCraft.Nations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.MegaCraft.Nations.enums.PlayerRanks;
import com.MegaCraft.Nations.instances.Nation;
import com.MegaCraft.Nations.instances.NationMember;
import com.MegaCraft.Nations.storage.DBConnection;

public class NationsMethods {
	public static boolean addNation(Nation nation) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        String sql = "INSERT INTO nations (uuid, name, founder, description) VALUES (?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, nation.getUUID().toString());
			ps.setString(2, nation.getName());
	        ps.setString(3, nation.getFounder().toString());
	        ps.setString(4, nation.getDescription());
	        
	        ps.executeUpdate();
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}

	public static boolean addNationMember(NationMember member) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        String sql = "INSERT INTO members (nationuuid, playeruuid, rank) VALUES (?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getNationUUID().toString());
			ps.setString(2, member.getUUID().toString());
	        ps.setString(3, member.getRank().toString());
	        
	        ps.executeUpdate();
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}
	
	public static boolean removeNation(Nation nation) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        String sql = "DELETE FROM nations WHERE uuid = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nation.getUUID().toString());
	        ps.executeUpdate();
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}
	
	public static boolean removeNationMembers(Nation nation) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        ps = conn.prepareStatement("DELETE FROM members WHERE nationuuid = ?");
			ps.setString(1, nation.getUUID().toString());

	        ps.executeUpdate();
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}
	
	public static boolean removeNationMember(Nation nation, Player player) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        ps = conn.prepareStatement("DELETE FROM members WHERE nationuuid = ? AND playeruuid = ?");
			ps.setString(1, nation.getUUID().toString());
			ps.setString(2, player.getUniqueId().toString());

	        ps.executeUpdate();
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}

	public static boolean saveNation(Nation nation) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        String sql = "UPDATE nations SET name = ?, description = ? WHERE uuid = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, nation.getName());
	        ps.setString(2, nation.getDescription());
			ps.setString(3, nation.getUUID().toString());
	        
	        ps.executeUpdate();
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}
	
	public static boolean saveNationMembers(Nation nation) {
		Connection conn = null;
		PreparedStatement ps = null;
	    try {
	        conn = DBConnection.sql.getConnection();
	        ps = conn.prepareStatement("UPDATE members SET rank = ? WHERE playeruuid = ?");
			
	        int i = 0;

	        for(NationMember member : nation.getMembers().values()) {
	            ps.setString(1, member.getRank().toString());
	            ps.setString(2, member.getUUID().toString());
	            
	            ps.addBatch();
	            i++;

	            if (i % 50 == 0 || i == nation.getMembers().size()) {
	                ps.executeBatch();
	            }
	        }
	        return true;
	    } catch (SQLException ex) {
	        Nations.plugin.getLogger().log(Level.SEVERE, "Could not execute update!", ex);
	    } finally {
	        try {
	            if (ps != null)
	                ps.close();
	        } catch (SQLException ex) {
	        	Nations.plugin.getLogger().log(Level.SEVERE, "Exception caught, closing database.", ex);
	        }
	    }
		return false;
	}

	public static boolean loadNations() {
		ResultSet rs2 = DBConnection.sql.readQuery("SELECT * FROM nations");
		try {
			while(rs2.next()) {
				Nation n = new Nation(UUID.fromString(rs2.getString(1)), rs2.getString(2), UUID.fromString(rs2.getString(3)), false);
				n.setDescription(rs2.getString(4));
			}
			rs2.close();
			return true;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean loadNationsMembers() {
		ResultSet rs2 = DBConnection.sql.readQuery("SELECT * FROM members");
		try {
			while(rs2.next()) {
				new NationMember(UUID.fromString(rs2.getString(2)), PlayerRanks.valueOf(rs2.getString(3)), Nation.nationInstances.get(UUID.fromString(rs2.getString(1))), false);
			}
			rs2.close();
			return true;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
