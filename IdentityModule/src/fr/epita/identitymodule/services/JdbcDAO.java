package fr.epita.identitymodule.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.identitymodule.models.Identity;

public class JdbcDAO {
	
	private Connection currentConnection;
	
	public JdbcDAO() throws SQLException{
		getConnection();
	}
	
	//Tries current connection if it disconnected, it reconnects again
	private Connection getConnection() throws SQLException {
		try {
			this.currentConnection.getSchema();
			
		} catch (Exception e) {
			String user = "jorge";
			String password = "123";
			String connectionString = "jdbc:derby://localhost:1527/IAM;create=true";
			//Had to add the line below or it did not work from servlet
			DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
			this.currentConnection = DriverManager.getConnection(connectionString, user, password);
		}
		return this.currentConnection;
	}
	
	//Closes connection
	private void releaseResources() {
		try {
			this.currentConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Returns a list of Identities in Database
	public List<Identity> readAllIdentities() throws SQLException {
		List<Identity> identities = new ArrayList<>();

		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID");
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String email = rs.getString("IDENTITY_EMAIL");
			String birthDate = rs.getString("IDENTITY_BIRTHDATE");
			Identity identity = new Identity(String.valueOf(uid), displayName, email, birthDate);
			identities.add(identity);
		}
		releaseResources();
		statement.close();
		return identities;
	}
	
	//Inserts new Identity into Database
	public void write(Identity identity) throws SQLException {
			
		Connection connection = getConnection();

		String sqlInstruction = "INSERT INTO IDENTITIES(IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, "
				+ "IDENTITY_BIRTHDATE) VALUES(?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sqlInstruction);
		statement.setString(1, identity.getDisplayname());
		statement.setString(2, identity.getEmail());
		statement.setString(3, identity.getBirthDate());
		statement.execute();
		
		releaseResources();
		statement.close();
	}
	
	//Updates the Identity using the identity received 
	public void update(Identity identity) throws SQLException {
			
		Connection connection = getConnection();

		String query = "UPDATE IDENTITIES SET IDENTITY_DISPLAYNAME = ?, IDENTITY_EMAIL = ?, "
				+ "IDENTITY_BIRTHDATE = ? WHERE IDENTITY_ID = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, identity.getDisplayname());
		statement.setString(2, identity.getEmail());
		statement.setString(3, identity.getBirthDate());
		statement.setString(4, identity.getUid());
		statement.execute();
		
		releaseResources();
		statement.close();
	}
	
	//Returns an Identity according to its uid
	public Identity getIdentity(String uid) throws SQLException {
			
		Connection connection = getConnection();
			
		String query = "select * from IDENTITIES where IDENTITY_ID = ?";
			
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, uid);
			
		ResultSet rs = statement.executeQuery();
		String user = "";
		String email = "";
		String birthdate = "";
		
		while(rs.next()){
			user = rs.getString("IDENTITY_DISPLAYNAME");
			email = rs.getString("IDENTITY_EMAIL");
			birthdate = rs.getString("IDENTITY_BIRTHDATE");
		}
		Identity identity = new Identity(uid,user,email,birthdate);
		
		statement.close();
		releaseResources();
		return identity;
	}
	
	//This method looks for the user in the db and his password, then compares the passwords and returns
	//a message or an empty String meaning that there was no problem
	public String authenticate(String user, String pass) throws SQLException {
		
		String errors = "";
		
		if("".equals(user) || "".equals(pass)){
			errors = "Username and Password cannot be empty!";
			
		}else{
			Connection connection = getConnection();
	
			String query = "SELECT * FROM IDENTITIES WHERE "
					+ " IDENTITY_DISPLAYNAME = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, user);
			
			ResultSet rs = statement.executeQuery();
			
			String dbPass = "";
			while(rs.next()){
				dbPass = rs.getString("PASSWORD");
			}
			if (!dbPass.equals(pass)){
				errors = "Incorrect username or password. Please try again.";
			}
			statement.close();
		}
		releaseResources();
		return errors;
	}
		
	//Deletes Identity according to the uid received
	public void delete(String uid) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "DELETE FROM IDENTITIES WHERE IDENTITY_ID = ?";
		PreparedStatement statement = connection.prepareStatement(sqlInstruction);
		statement.setString(1, uid);
		statement.execute();
		
		statement.close();
		releaseResources();
	}
}
