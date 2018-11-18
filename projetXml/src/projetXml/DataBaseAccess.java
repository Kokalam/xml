package projetXml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseAccess {
	
	private final static String CREATE_AUTEUR = "CREATE TABLE auteur (ID VARCHAR(200) NOT NULL PRIMARY KEY)";
	private final static String CREATE_COAUTEUR = "CREATE TABLE coauteur (ID_AUTEUR VARCHAR(200) NOT NULL, ID_COAUTEUR VARCHAR(200) NOT NULL, PRIMARY KEY(ID_AUTEUR,ID_COAUTEUR))";
	
	private final static String DROP_AUTEUR = "DROP TABLE auteur";
	private final static String DROP_COAUTEUR = "DROP TABLE coauteur"; 
	
	private final static String INSERT_AUTEUR = "INSERT INTO auteur VALUES(?)";
	private final static String INSERT_COAUTEUR = "INSERT INTO coauteur VALUES(?,?)";
	private static Connection conn;
	
	static {
		try {
			conn = DriverManager.getConnection("jdbc:derby:XMLDB;create=true");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection() {
		try {
			if(conn.isClosed()) {
				conn = DriverManager.getConnection("jdbc:derby:XMlDB;create=true");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void createTable() {
		try(PreparedStatement pst = conn.prepareStatement(CREATE_AUTEUR)) {
			pst.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try(PreparedStatement pst = conn.prepareStatement(CREATE_COAUTEUR)) {
			pst.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void dropTable() {
		try(PreparedStatement pst = conn.prepareStatement(DROP_AUTEUR)) {
			pst.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try(PreparedStatement pst = conn.prepareStatement(DROP_COAUTEUR)) {
			pst.executeUpdate();
		}catch(SQLException e) {
			
		}
	}
	
	public static void insertAuteur(String name) {
		try(PreparedStatement pst = conn.prepareStatement(INSERT_AUTEUR)) {
			pst.setString(1, name);
			pst.executeUpdate();
		}catch(SQLException e) {
			
		}
	}
	
	public static void insertCoAuteur(String auteur, String coauteur) {
		try(PreparedStatement pst = conn.prepareStatement(INSERT_COAUTEUR)) {
			pst.setString(1, auteur);
			pst.setString(2,coauteur);
			pst.executeUpdate();
		}catch(SQLException e) {
			
		}
	}
}
