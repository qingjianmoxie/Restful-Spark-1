package com.webservice.finalrest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.StringTokenizer;

import spark.Request;

public class AuthenticationService {
	public boolean authenticate(Request req, String encodedUserPassword) throws SQLException {

		if (null == encodedUserPassword)
			return false;
		
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		
		//final String encodedUserPassword = authCredentials.replaceFirst("Basic"
		//		+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(
					encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// we have fixed the userid and password as admin
		// call some UserService/LDAP here try {
        Connection conn;
        Statement stmt;
        // Write the response message, in an HTML page
        try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Step 1: Allocate a database Connection object
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser", "User1"); // <== Check!
        // database-URL(hostname, port, default database), username, password
        // Step 2: Allocate a Statement object within the Connection
        stmt = conn.createStatement();
       
        ResultSet rs = stmt.executeQuery("select * from auth");
        //   ResultSetMetaData rsmd = rs.getMetaData();
        boolean authenticationStatus = false;
        while(rs.next()) {
		authenticationStatus = rs.getInt(1) == Integer.parseInt(username)
				&& rs.getString(2).equals(password);
		if(authenticationStatus) {
			req.session().attribute("user", Integer.parseInt(username));
			break;
		}
	}
        return authenticationStatus;
	}
}
