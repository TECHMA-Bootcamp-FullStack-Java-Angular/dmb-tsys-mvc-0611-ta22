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
import models.Video;

public class VideoService {
	
private static final String video_club = null;
	
	private ConnectionMySQL sql = ConnectionMySQL.craeteConnectionMySQL();
	private Connection conn = sql.connection(sql.getLOCALHOSTS(), sql.getSQL_PORT_DEFAULT(), sql.getSQL_USER_DEFAULT(), "root");
	private String dbName = video_club;

	public VideoService(String dbName) {
	   this.dbName = dbName;
	}

	Logger logger = Logger.getLogger(ConnectionMySQL.class.getName());

	// Create
	public void insertVideo(Video video) throws SQLException {
		selectDB();
	    // Sentencia SQL para la inserción de un nuevo cliente
		
	    String insertSQL = "INSERT INTO videos (title, director , cli_id ) VALUES (?, ?, ?)";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
	        // Establecer los valores de los parámetros en la sentencia SQL
	        preparedStatement.setString(1, video.getTitle());
	        preparedStatement.setString(2, video.getDirector());
	        preparedStatement.setString(3, String.valueOf(video.getCli_id()));
	      

	        // Ejecutar la inserción
	        preparedStatement.executeUpdate();
	   
	    }
	}
	
	// GetClientById
	public Video getVideo(int Id) throws SQLException {
		selectDB();

		Video video = null;
		 // Sentencia SQL para eliminar un cliente por su ID
	    String getVideoByID = "SELECT * FROM videos WHERE id = "+Id;

	    try (Statement statement = conn.createStatement(); 
	    	 ResultSet resultSet = statement.executeQuery(getVideoByID)) {
	 	        while (resultSet.next()) {
	 	            int id = resultSet.getInt("id");
	 	            String title = resultSet.getString("title");
	 	            String director = resultSet.getString("director");
	 	            String cli_id = resultSet.getString("cli_id");

	 	            
	 	             video = new Video(Integer.valueOf(id), title, director,Integer.valueOf( cli_id));
	 	        }  
	    }
	 
	    return video;
	            
	}
	
	// UpdateVideoById
	public void updateVideo(Video v) throws SQLException {
		selectDB();

	    // Sentencia SQL para actualizar un cliente por su ID
	    String updateSQL = "UPDATE videos SET title = ?, director = ?, cli_id = ? WHERE id = ?";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
	        // Establecer los valores de los parámetros en la sentencia SQL
	    	
	        preparedStatement.setString(1, v.getTitle());
	        preparedStatement.setString(2, v.getDirector());
	        preparedStatement.setInt(3, Integer.valueOf(v.getCli_id()) );
	        preparedStatement.setInt(4, Integer.valueOf(v.getId()));
	    
	        // Ejecutar la actualización
	        preparedStatement.executeUpdate();
	  
    }
	}
	
	// GetAll
	public List<Video> getAllVideo() throws SQLException {
		selectDB();
	    List<Video> videos = new ArrayList<>();
	    String selectSQL = "SELECT * FROM videos";

	    try (Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery(selectSQL)) {
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String title = resultSet.getString("title");
	            String director = resultSet.getString("director");
	            int cli_id = resultSet.getInt("cli_id");

	            Video video = new Video(id, title, director, cli_id );
	            videos.add(video);
	        }
	    }

	    return videos;
	}
	
	
	// DeleteVideotById
	public void deleteVideo(int Id) throws SQLException {
		selectDB();
	    String deleteSQL = "DELETE FROM videos WHERE id = ?";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
	        // Establecer el valor del parámetro en la sentencia SQL
	        preparedStatement.setInt(1, Id);

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
