package me.stewiestudio.buildersRatingSystem;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private static DatabaseConnection databaseConnection = null;
	static File dataFolder;
	
	private Connection conn;
	private File folder;
	
	private DatabaseConnection(File f) {
		this.folder = f;
		String url = "jdbc:sqlite:"+this.folder.getPath() + "/ranks.db";
		try {
			conn = DriverManager.getConnection(url);
			if(conn != null) {
				System.out.println("Connected to db");
			}
			String sql = "CREATE TABLE IF NOT EXISTS ranks (\n"
	                + "	id integer PRIMARY KEY,\n"
	                + "	name text NOT NULL,\n"
	                + "	rank integer DEFAULT 1\n"
	                + " blocksPlaced integer DEFAULT 0\n"
	                + " blocksDestroyed integer DEFAULT 0\n"
	                + " timePlayed integer DEFAULT 0\n"
	                + ");";
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("DB created successfully");
		}
		catch(Exception ex) {
			System.out.println("Error when connection to local db");
		}
	}
	public void insertNewPlayer(String name) {
		String sql = "INSERT INTO ranks(name) VALUES(?)";
		 try (
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public ResultSet getPlayerInfo(String name) {
		String sql = "SELECT name, rank, blocksPlaced, blocksDestroyed, timePlayed "
                + "FROM ranks WHERE name = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			return statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void updatePlayerInfo(String name, int rank, int blocksPlaced, int blocksRemoved, int timePlayed) {
        String sql = "UPDATE ranks SET rank = ? , "
                + "blocksPlaced = ? "
        		+ "blocksDestroyed = ? "
                + "timePlayed = ? "
                + "WHERE name = ?";
 
        try {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	// set the corresponding param
        	pstmt.setInt(1, rank);
        	pstmt.setInt(2, blocksPlaced);
        	pstmt.setInt(3, blocksRemoved);
        	pstmt.setInt(4, timePlayed);
        	pstmt.setString(5, name);
        	// update 
        	pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public static DatabaseConnection createConnection(File f) {
		dataFolder = f;
		if(databaseConnection == null) {
			databaseConnection = new DatabaseConnection(dataFolder);
		}
		return databaseConnection;
	}
	public static DatabaseConnection getConnection() {
		return databaseConnection;
	}
}
