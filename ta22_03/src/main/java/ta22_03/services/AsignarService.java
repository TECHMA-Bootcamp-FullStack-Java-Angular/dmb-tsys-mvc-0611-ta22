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
import ta22_03.models.Asignado;
import ta22_03.models.Proyecto;

public class AsignarService {

	private ConnectionMySQL sql = ConnectionMySQL.craeteConnectionMySQL();
	private Connection conn = sql.connection(sql.getLOCALHOSTS(), sql.getSQL_PORT_DEFAULT(), sql.getSQL_USER_DEFAULT(),
			"root");
	private String dbName;

	public AsignarService(String dbName) {
		this.dbName = dbName;
	}

	Logger logger = Logger.getLogger(ConnectionMySQL.class.getName());

	// CreateAsignado
	public void insertAsignado(Asignado obj) throws SQLException {
		selectDB();
		
		String insertSQL = "INSERT INTO asignado_a ( cientifico , proyecto ) VALUES (?, ?)";

		try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			
			preparedStatement.setString(1, obj.getCientifico());
			preparedStatement.setString(2, obj.getProyecto());
			preparedStatement.executeUpdate();

		}
	}
	

	// GetAsignadoById
	public Asignado getAsiganado(String dni) throws SQLException {
		selectDB();
		Asignado obj = null;
	
		String getByID = "SELECT * FROM asignado_a WHERE cientifico LIKE " + dni +";";

		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(getByID)) {
			while (resultSet.next()) {
				String cientifico = resultSet.getString("cientifico");
				String proyecto = resultSet.getString("proyecto");
				obj = new Asignado( cientifico, proyecto );
			}
		}
		return obj;

	}

	// UpdateAsignadoById
	public void updateAsigando(Asignado obj) throws SQLException {
		selectDB();
		String updateSQL = "UPDATE asignado_a SET cientifico  = ?, proyecto = ?  WHERE cientifico LIKE ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, obj.getCientifico());
			preparedStatement.setString(2, obj.getProyecto());
			preparedStatement.setString(3, obj.getCientifico());
			System.out.println( preparedStatement.executeUpdate());
			preparedStatement.executeUpdate();
		}
	}

	// GetAllAsignado
	public List<Asignado> getAllAsigando() throws SQLException {
		selectDB();
		List<Asignado> asignados = new ArrayList<>();
	
		String selectSQL = "SELECT * FROM asignado_a ";

		try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(selectSQL)) {
			while (resultSet.next()) {
				
				String cientifico = resultSet.getString("cientifico");
				String proyecto = resultSet.getString("proyecto");

				Asignado obj = new Asignado( cientifico, proyecto);
				asignados.add(obj);
			}
		}

		return asignados;
	}

	// DeleteAsignadoById
	public void deleteAsiganado(String dni) throws SQLException {
		String deleteSQL = "DELETE FROM asignado_a WHERE cientifico LIKE ?";

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
