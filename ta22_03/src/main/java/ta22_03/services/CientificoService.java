package ta22_03.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ta22_03.database.ConnectionMySQL;
import ta22_03.models.Cientifico;

public class CientificoService {

	private ConnectionMySQL sql = ConnectionMySQL.craeteConnectionMySQL();
	private Connection conn = sql.connection(sql.getLOCALHOSTS(), sql.getSQL_PORT_DEFAULT(), sql.getSQL_USER_DEFAULT(),
			"root");
	private String dbName;

	public CientificoService(String dbName) {
		this.dbName = dbName;
	}

	Logger logger = Logger.getLogger(ConnectionMySQL.class.getName());

	// Create Cientifico
	public void insertCientifico(Cientifico obj) throws SQLException {
		selectDB();
		
		String insertSQL = "INSERT INTO cientificos (DNI , NomApels) VALUES (?, ?)";

		try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			
			preparedStatement.setString(1, obj.getDni());
			preparedStatement.setString(2, obj.getNomApels());
			preparedStatement.executeUpdate();

		}
	}

	// GetClientById
	public Cientifico getCientifico(String dni) throws SQLException {
		selectDB();
		Cientifico obj = null;
	
		String getClientByID = "SELECT * FROM cientificos WHERE DNI = " + dni;

		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(getClientByID)) {
			while (resultSet.next()) {
				String DNI = resultSet.getString("DNI");
				String nomApels = resultSet.getString("NomApels");
				obj = new Cientifico( DNI , nomApels);
			}
		}
		return obj;

	}

	// UpdateClientById
	public void updateCientifico(Cientifico obj) throws SQLException {
		selectDB();
		String updateSQL = "UPDATE cientificos SET DNI = ?, NomApels = ? WHERE DNI = ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, obj.getDni());
			preparedStatement.setString(2, obj.getNomApels());
			preparedStatement.setString(3, obj.getDni());
			preparedStatement.executeUpdate();
		}
	}

	// GetAllCientificos
	public List<Cientifico> getAllCientificos() throws SQLException {
		selectDB();
		List<Cientifico> cientificos = new ArrayList<>();
	
		String selectSQL = "SELECT * FROM cientificos ";

		try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(selectSQL)) {
			while (resultSet.next()) {
				
				String dni = resultSet.getString("DNI");
				String nomApels = resultSet.getString("NomApels");
				Cientifico cliente = new Cientifico( dni , nomApels);
				cientificos.add(cliente);
			}
		}

		return cientificos;
	}

	// DeleteClientById
	public void deleteCientifico(String dni) throws SQLException {
		String deleteSQL = "DELETE FROM cientificos WHERE DNI = ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {

			preparedStatement.setString(1, dni);
			preparedStatement.executeUpdate();
		}
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
	


}
