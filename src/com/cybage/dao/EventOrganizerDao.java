package com.cybage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizerDao {
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcEventname = "root";
	private String jdbcPassword = "root";

	private static final String INSERT_EVENTS_SQL = "INSERT INTO events" + "  (name, email, venue, price) VALUES "
			+ " (?, ?, ?, ?);";

	private static final String SELECT_EVENT_BY_ID = "select id,name,email,venue,price from events where id =?";
	private static final String SELECT_ALL_EVENTS = "select * from events";
	private static final String DELETE_EVENTS_SQL = "delete from events where id = ?;";
	private static final String UPDATE_EVENTS_SQL = "update events set name = ?,email= ?, venue =?, price=? where id = ?;";

	public EventOrganizerDao() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcEventname, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertEvent(Event event) throws SQLException {
		System.out.println(INSERT_EVENTS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENTS_SQL)) {
			preparedStatement.setString(1, event.getName());
			preparedStatement.setString(2, event.getEmail());
			preparedStatement.setString(3, event.getVenue());
			preparedStatement.setString(4, event.getPrice());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Event selectEvent(int id) {
		Event event = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String venue = rs.getString("venue");
				String price = rs.getString("price");
				event = new Event(id, name, email, venue, price);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return event;
	}

	public List<Event> selectAllEvents() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Event> events = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String venue = rs.getString("venue");
				String price = rs.getString("price");
				events.add(new Event(id, name, email, venue, price));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return events;
	}

	public boolean deleteEvent(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_EVENTS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateEvent(Event event) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_EVENTS_SQL);) {
			statement.setString(1, event.getName());
			statement.setString(2, event.getEmail());
			statement.setString(3, event.getVenue());
			statement.setString(4, event.getPrice());
			statement.setInt(5, event.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}