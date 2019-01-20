package com.mikeekeghe;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.*;

public class TinyUrl {
	public String getNewUrl(String oldUrl) {
		String myNewUrl = "";
		myNewUrl = "https://mikeurl" + randomAlphaNumeric(4);
		System.out.println("myNewUrl is " + myNewUrl);

		String query = "INSERT INTO urltble (oldurl,newurl ) VALUES (" + "'" + oldUrl + "','" + myNewUrl + "');";

		System.out.println("Inserting\n" + query);

		insertStatement(query);
		return myNewUrl;
	}

	private String randomAlphaNumeric(int count) {
		final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	private void insertStatement(String insert_query) {

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/vanhack", "root", "");
			System.out.println("Opened database successfully");
			stmt = (Statement) c.createStatement();
			System.out.println("Our query was: " + insert_query);
			stmt.executeUpdate(insert_query);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
	}

	public String forwardShortUrl(String shortUrl) {
		int myMaxAttId = 0;
		String longUrl = "";
		try {
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/vanhack", "root",
					"");
			Statement stat = (Statement) con.createStatement();
			System.out.println("Opened database successfully");
			String todRepQuery = "SELECT oldurl" + " FROM urltble where newurl = '" + shortUrl + "';";
			System.out.println("todRepQuery is :" + todRepQuery);
			ResultSet rs = con.createStatement().executeQuery(todRepQuery);
			while (rs.next()) {
				longUrl = rs.getString("oldurl");
				System.out.println("longUrl = " + longUrl);
			}

		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return longUrl;
	}

}
