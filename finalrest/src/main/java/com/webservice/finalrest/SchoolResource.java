package com.webservice.finalrest;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Request;
import spark.Response;
import spark.Route;





public class SchoolResource {
    
	//@GET
    //@Path("schools")
	//@Produces(MediaType.APPLICATION_JSON)
	 public static Route getSchools = (Request req, Response res) ->{ 
		
		res.type("application/json");
		Schools schools = null;
		School school = null;
		try {
			Connection conn;
			Statement stmtS, stmtT;
			// Write the response message, in an HTML page
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
					"User1"); // <== Check!
			// database-URL(hostname, port, default database), username, password
			// Step 2: Allocate a Statement object within the Connection
			stmtS = conn.createStatement();
			stmtT = conn.createStatement();

			ResultSet rsS, rsT;
			rsS = stmtS.executeQuery("select * from schools");
			// ResultSetMetaData rsmd = rs.getMetaData();
			
			boolean empty = true;
			while (rsS.next()) {
				if(empty) {
					schools = new Schools();
					empty = false;
				}
				school = new School();
				school.setSchool_id(rsS.getInt(1));
				school.setSchool_name(rsS.getString(2));
				school.setSchool_website(rsS.getString(3));
				school.setSchool_gender(rsS.getString(4));

				rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

				while (rsT.next()) {

					Levels l = new Levels(rsT.getString(2));
					school.addSchool_level(l);

				}

				schools.addSchool(school);

			}

			if(schools == null) {
				res.status(404);
				return "NOT FOUND";
			}else {
			return JsonUtil.jsonData(schools);
			}
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
			Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
		}
		res.status(500);
		return "INTERNAL SERVER ERROR";
	 };
	 
	 
	//@GET
    //@Path("schools/{admin_id}")
	//@Produces(MediaType.APPLICATION_JSON)
	 public static Route getSchool = (Request req, Response res) ->{

		 	res.type("application/json");		
			School school = null;
			try {
				Connection conn;
				Statement stmtS, stmtT;
				// Write the response message, in an HTML page
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				// Step 1: Allocate a database Connection object
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
						"User1"); // <== Check!
				// database-URL(hostname, port, default database), username, password
				// Step 2: Allocate a Statement object within the Connection
				stmtS = conn.createStatement();
				stmtT = conn.createStatement();

				ResultSet rsS, rsT;
				System.out.println(req.params(":admin_id")); //Debug
				rsS = stmtS.executeQuery("select * from schools where admin_id = "+ req.params(":admin_id"));
				// ResultSetMetaData rsmd = rs.getMetaData();
				if (rsS.next()) {

					school = new School();
					school.setSchool_id(rsS.getInt(1));
					school.setSchool_name(rsS.getString(2));
					school.setSchool_website(rsS.getString(3));
					school.setSchool_gender(rsS.getString(4));

					rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

					while (rsT.next()) {

						Levels l = new Levels(rsT.getString(2));
						school.addSchool_level(l);

					}

					
				}
				if(school == null) {
					res.status(404);
					return "NOT FOUND";
				}else {
				return JsonUtil.jsonData(school);
				}
			} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
				Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
			}
			res.status(500);
			return "INTERNAL SERVER ERROR";
		 };

	//@POST
	//@Path("schools/{admin_id}/levels")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)	
	 public static Route postSchool_levels = (Request req, Response res) -> { 
		
		 res.type("application/json");	 
		 ObjectMapper mapper = new ObjectMapper();
		 School s = mapper.readValue(req.body(),School.class);
		 
		 School school = null;

		try {
			Connection conn;
			Statement stmtS, stmtT;
			// Write the response message, in an HTML page
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
					"User1"); // <== Check!
			// database-URL(hostname, port, default database), username, password
			// Step 2: Allocate a Statement object within the Connection
			stmtS = conn.createStatement();
			stmtT = conn.createStatement();

			ResultSet rsS, rsT;
			for (Levels level : s.getSchool_levels()) {
				stmtT.executeUpdate("insert into levels (admin_id,level_name) values (" + req.params(":admin_id") + ",'"
						+ level.getLevel_name() + "')");// POST)
			}
			rsS = stmtS.executeQuery("select * from schools where admin_id =" + req.params(":admin_id"));

			if (rsS.next()) {

				school = new School();
				school.setSchool_id(rsS.getInt(1));
				school.setSchool_name(rsS.getString(2));
				school.setSchool_website(rsS.getString(3));
				school.setSchool_gender(rsS.getString(4));

				rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

				while (rsT.next()) {

					Levels l = new Levels(rsT.getString(2));
					school.addSchool_level(l);

				}
			}
			if(school == null) {
				res.status(404);
				return "NOT FOUND";
			}else {
			return JsonUtil.jsonData(school);
			}
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
			Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
		}
		res.status(500);
		return "INTERNAL SERVER ERROR";
	 };


	//@DELETE
	//@Path("schools/{admin_id}/levels")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)	
	 public static Route deleteSchool_levels = (Request req, Response res) -> {  
		
		 res.type("application/json"); 
		 ObjectMapper mapper = new ObjectMapper();
		 School s = mapper.readValue(req.body(),School.class);
		 
		 School school = null;

		try {
			Connection conn;
			Statement stmtS, stmtT;
			// Write the response message, in an HTML page
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
					"User1"); // <== Check!
			// database-URL(hostname, port, default database), username, password
			// Step 2: Allocate a Statement object within the Connection
			stmtS = conn.createStatement();
			stmtT = conn.createStatement();

			ResultSet rsS, rsT;
			for (Levels level : s.getSchool_levels()) {
				stmtT.executeUpdate("delete from levels where admin_id =" + req.params(":admin_id") + " AND level_name= '"
						+ level.getLevel_name() + "'");// DELETE
			}
			rsS = stmtS.executeQuery("select * from schools where admin_id =" + req.params(":admin_id"));

			if (rsS.next()) {

				school = new School();
				school.setSchool_id(rsS.getInt(1));
				school.setSchool_name(rsS.getString(2));
				school.setSchool_website(rsS.getString(3));
				school.setSchool_gender(rsS.getString(4));

				rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

				while (rsT.next()) {

					Levels l = new Levels(rsT.getString(2));
					school.addSchool_level(l);

				}
			}
			if(school == null) {
				res.status(404);
				return "NOT FOUND";
			}else {
			return JsonUtil.jsonData(school);
			}
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
			Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
		}
		res.status(500);
		return "INTERNAL SERVER ERROR";
	 };


	//@PUT
	//@Path("schools/{admin_id}/name")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	 public static Route putSchool_name = (Request req, Response res) -> {  
		
		 res.type("application/json");
		 ObjectMapper mapper = new ObjectMapper();
		 School s = mapper.readValue(req.body(),School.class);
		
		 School school = null;
		
		try {
			Connection conn;
			Statement stmtS, stmtT;
			// Write the response message, in an HTML page
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
					"User1"); // <== Check!
			// database-URL(hostname, port, default database), username, password
			// Step 2: Allocate a Statement object within the Connection
			stmtS = conn.createStatement();
			stmtT = conn.createStatement();

			ResultSet rsS, rsT;

			stmtS.executeUpdate(
					"update schools set school_name = '" + s.getSchool_name() + "' where admin_id = " + req.params(":admin_id"));// PUT
			rsS = stmtS.executeQuery("select * from schools where admin_id =" + req.params(":admin_id"));
			// ResultSetMetaData rsmd = rs.getMetaData();
			if (rsS.next()) {

				school = new School();
				school.setSchool_id(rsS.getInt(1));
				school.setSchool_name(rsS.getString(2));
				school.setSchool_website(rsS.getString(3));
				school.setSchool_gender(rsS.getString(4));

				rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

				while (rsT.next()) {
					Levels l = new Levels(rsT.getString(2));
					school.addSchool_level(l);
				}
			}
			if(school == null) {
				res.status(404);
				return "NOT FOUND";
			}else {
			return JsonUtil.jsonData(school);
			}
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
			Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
		}
		res.status(500);
		return "INTERNAL SERVER ERROR";
	 };


	//@PUT
	//@Path("schools/{admin_id}/website")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public static Route putSchool_website = (Request req, Response res) -> {  
		
		 res.type("application/json"); 
		 ObjectMapper mapper = new ObjectMapper();
		 School s = mapper.readValue(req.body(),School.class);
		
		 School school = null;
		try {
			Connection conn;
			Statement stmtS, stmtT;
			// Write the response message, in an HTML page
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
					"User1"); // <== Check!
			// database-URL(hostname, port, default database), username, password
			// Step 2: Allocate a Statement object within the Connection
			stmtS = conn.createStatement();
			stmtT = conn.createStatement();

			ResultSet rsS, rsT;

			stmtS.executeUpdate(
					"update schools set school_website = '" + s.getSchool_website() + "' where admin_id = " + req.params(":admin_id"));// PUT
			rsS = stmtS.executeQuery("select * from schools where admin_id =" + req.params(":admin_id"));
			// ResultSetMetaData rsmd = rs.getMetaData();
			if (rsS.next()) {

				school = new School();
				school.setSchool_id(rsS.getInt(1));
				school.setSchool_name(rsS.getString(2));
				school.setSchool_website(rsS.getString(3));
				school.setSchool_gender(rsS.getString(4));

				rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

				while (rsT.next()) {
					Levels l = new Levels(rsT.getString(2));
					school.addSchool_level(l);

				}

			}
		
			if(school == null) {
				res.status(404);
				return "NOT FOUND";
			}else {
			return JsonUtil.jsonData(school);
			}
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
			Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
		}
		res.status(500);
		return "INTERNAL SERVER ERROR";
	 };


	//@PUT
	//@Path("schools/{admin_id}/gender")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public static Route putSchool_gender = (Request req, Response res) -> {  
		
		 res.type("application/json");		 
		 ObjectMapper mapper = new ObjectMapper();
		 School s = mapper.readValue(req.body(),School.class);
		
		 School school = null;
		
		try {
			Connection conn;
			Statement stmtS, stmtT;
			// Write the response message, in an HTML page
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_schools?useSSL=false", "myuser",
					"User1"); // <== Check!
			// database-URL(hostname, port, default database), username, password
			// Step 2: Allocate a Statement object within the Connection
			stmtS = conn.createStatement();
			stmtT = conn.createStatement();

			ResultSet rsS, rsT;

			stmtS.executeUpdate(
					"update schools set school_gender = '" + s.getSchool_gender() + "' where admin_id = " + req.params(":admin_id"));// PUT
			rsS = stmtS.executeQuery("select * from schools where admin_id =" + req.params(":admin_id"));
			// ResultSetMetaData rsmd = rs.getMetaData();
			if (rsS.next()) {

				school = new School();
				school.setSchool_id(rsS.getInt(1));
				school.setSchool_name(rsS.getString(2));
				school.setSchool_website(rsS.getString(3));
				school.setSchool_gender(rsS.getString(4));

				rsT = stmtT.executeQuery("select * from levels where admin_id = " + rsS.getString(1));

				while (rsT.next()) {

					Levels l = new Levels(rsT.getString(2));
					school.addSchool_level(l);

				}

			}
			if(school == null) {
				res.status(404);
				return "NOT FOUND";
			}else {
			return JsonUtil.jsonData(school);
			}
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex3) {
			Logger.getLogger(SchoolResource.class.getName()).log(Level.SEVERE, null, ex3);
		}
		res.status(500);
		return "INTERNAL SERVER ERROR";
	 };
}
