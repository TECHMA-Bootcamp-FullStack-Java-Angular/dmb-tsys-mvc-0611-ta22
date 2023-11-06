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
import ta22_03.models.Proyecto;

public class ProyectoService {

	private ConnectionMySQL sql = ConnectionMySQL.craeteConnectionMySQL();
	private Connection conn = sql.connection(sql.getLOCALHOSTS(), sql.getSQL_PORT_DEFAULT(), sql.getSQL_USER_DEFAULT(),
			"root");
	private String dbName;

	public ProyectoService(String dbName) {
		this.dbName = dbName;
	}

	Logger logger = Logger.getLogger(ConnectionMySQL.class.getName());

	// CreateProyecto
	public void insertProyecto(Proyecto obj) throws SQLException {
		selectDB();
		
		String insertSQL = "INSERT INTO proyecto ( id , Nombre , Horas) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			
			preparedStatement.setString(1, obj.getId());
			preparedStatement.setString(2, obj.getNombre());
			preparedStatement.setInt(3, obj.getHoras());
			preparedStatement.executeUpdate();

		}
	}

	// GetProyectoById
	public Proyecto getProyecto(String Id) throws SQLException {
		selectDB();
		Proyecto obj = null;
	
		String getClientByID = "SELECT * FROM proyecto WHERE id LIKE '" + Id +"'";


		try (Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(getClientByID)) {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String nombre = resultSet.getString("Nombre");
				Integer horas = resultSet.getInt("Horas");
				obj = new Proyecto( id, nombre, Integer.valueOf(horas) );
			}
		}
		return obj;

	}

	// UpdateProyectoById
	public void updateProyecto(Proyecto obj) throws SQLException {
		selectDB();
		String updateSQL = "UPDATE proyecto SET Nombre  = ?, Horas = ?  WHERE id LIKE ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, obj.getNombre());
			preparedStatement.setInt(2, obj.getHoras());
			preparedStatement.setString(3, obj.getId());
			preparedStatement.executeUpdate();
		}
	}

	// GetAllProyecto
	public List<Proyecto> getAllProyecto() throws SQLException {
		selectDB();
		List<Proyecto> proyectos = new ArrayList<>();
	
		String selectSQL = "SELECT * FROM proyecto ";

		try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(selectSQL)) {
			while (resultSet.next()) {
				
				String id = resultSet.getString("id");
				String nombre = resultSet.getString("Nombre");
				Integer horas = resultSet.getInt("Horas");
				Proyecto obj = new Proyecto( id, nombre, Integer.valueOf(horas) );
				proyectos.add(obj);
			}
		}

		return proyectos;
	}

	// DeleteProyectoById
	public void deleteProyecto(String id) throws SQLException {
		String deleteSQL = "DELETE FROM proyecto WHERE id LIKE ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {

			preparedStatement.setString(1, id);
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
