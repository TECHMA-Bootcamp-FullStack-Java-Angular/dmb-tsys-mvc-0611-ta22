package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.ConnectionMySQL;
import models.ClienteModels;

public class ClientService {

	private static final String clientes = null;
	private ConnectionMySQL sql = ConnectionMySQL.craeteConnectionMySQL();
	private Connection conn = sql.connection(sql.getLOCALHOSTS(), sql.getSQL_PORT_DEFAULT(), sql.getSQL_USER_DEFAULT(), "root");
	private String dbName = clientes;

	public ClientService(String dbName) {
	   this.dbName = dbName;
	}

	Logger logger = Logger.getLogger(ConnectionMySQL.class.getName());

	// Create
	public void insertClient(ClienteModels client) throws SQLException {
		selectDB();
	    // Sentencia SQL para la inserción de un nuevo cliente
		
	    String insertSQL = "INSERT INTO cliente (nombre, apellido, direccion, DNI, fecha) VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
	        // Establecer los valores de los parámetros en la sentencia SQL
	        preparedStatement.setString(1, client.getNombre());
	        preparedStatement.setString(2, client.getApellido());
	        preparedStatement.setString(3, client.getDireccion());
	        preparedStatement.setInt(4, client.getDNI());
	        preparedStatement.setString(5, client.getDate());

	        // Ejecutar la inserción
	        preparedStatement.executeUpdate();
	   
	    }
	}
	
	// GetClientById
	public ClienteModels getClient(int clientId) throws SQLException {
		selectDB();

		ClienteModels client = null;
		 // Sentencia SQL para eliminar un cliente por su ID
	    String getClientByID = "SELECT * FROM cliente WHERE id = "+clientId;

	    try (Statement statement = conn.createStatement(); 
	    	 ResultSet resultSet = statement.executeQuery(getClientByID)) {
	 	        while (resultSet.next()) {
	 	            int id = resultSet.getInt("id");
	 	            String nombre = resultSet.getString("nombre");
	 	            String apellido = resultSet.getString("apellido");
	 	            String direccion = resultSet.getString("direccion");
	 	            int dni = resultSet.getInt("DNI");
	 	            String fecha = resultSet.getString("fecha");
	 	            
	 	             client = new ClienteModels(id, nombre, apellido, direccion, dni, fecha);
	 	        }  
	    }
	 
	    return client;
	            
	}
	
	// UpdateClientById
	public void updateClient(ClienteModels client) throws SQLException {
		selectDB();

	    // Sentencia SQL para actualizar un cliente por su ID
	    String updateSQL = "UPDATE cliente SET nombre = ?, apellido = ?, direccion = ?, DNI = ?, fecha = ? WHERE id = ?";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
	        // Establecer los valores de los parámetros en la sentencia SQL
	    	
	        preparedStatement.setString(1, client.getNombre());
	        preparedStatement.setString(2, client.getApellido());
	        preparedStatement.setString(3, client.getDireccion());
	        preparedStatement.setInt(4, client.getDNI());
	        preparedStatement.setString(5, client.getDate());
	        preparedStatement.setInt(6, client.getId());

	        // Ejecutar la actualización
	        preparedStatement.executeUpdate();
	  
    }
	}
	
	// GetAll
	public List<ClienteModels > getAllClients() throws SQLException {
		selectDB();
	    List<ClienteModels> clientes = new ArrayList<>();
	    String selectSQL = "SELECT id, nombre, apellido, direccion, DNI, fecha FROM cliente";

	    try (Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery(selectSQL)) {
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String nombre = resultSet.getString("nombre");
	            String apellido = resultSet.getString("apellido");
	            String direccion = resultSet.getString("direccion");
	            int dni = resultSet.getInt("DNI");
	            String fecha = resultSet.getString("fecha");

	            ClienteModels cliente = new ClienteModels(id, nombre, apellido, direccion, dni, fecha);
	            clientes.add(cliente);
	        }
	    }

	    return clientes;
	}
	

	
	// DeleteClientById
	public void deleteClient(int clientId) throws SQLException {
	    // Sentencia SQL para eliminar un cliente por su ID
	    String deleteSQL = "DELETE FROM cliente WHERE id = ?";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
	        // Establecer el valor del parámetro en la sentencia SQL
	        preparedStatement.setInt(1, clientId);

	        // Ejecutar la eliminación
	        preparedStatement.executeUpdate();
	    }
	}


	
	
	// Crea una base de datos si no existe.
	public void createDB(String dbName) {
		sendQuery("CREATE DATABASE IF NOT EXISTS " + dbName + ";");
		this.dbName = dbName;
	}
	


	// Selecciona una base de datos.
	public void selectDB() {
		sendQuery("USE " + this.dbName + ";");	
	}

	// Cierra la conexion
	public void closeConnection() {
		if (conn != null) {
			try {
				System.out.println("CONECXION CERRADA");
				conn.close();

			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "NO SE PUDO CERRAR LA CONECXION\n", ex);
			}
		}
	}

	// Realiza una consulata SQL
	public void sendQuery(String query) {
		try {

			Statement st = conn.createStatement();
			st.executeUpdate(query);
			query = query.length() < 100 ? query : "Successful sentence";
			System.out.println("Query succes: OK" + " - " + query);

		} catch (SQLException ex) {

			logger.log(Level.WARNING, "REVISA LA QUERY\n" + query, ex);
		}
	}



	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}