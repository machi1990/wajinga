package com.machi.wajinga.ws.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jdo.JDOHelper;

public class DataJanitor {

	public static void clean() {
		try {
			Connection connection = DriverManager.getConnection(JDOHelper.getPersistenceManagerFactory("Wajinga-Test").getConnectionURL(), "SA", "");
			Statement stat = connection.createStatement();
			stat.execute("SHUTDOWN");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
