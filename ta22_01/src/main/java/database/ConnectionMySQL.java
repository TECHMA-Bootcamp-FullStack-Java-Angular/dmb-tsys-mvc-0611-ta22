package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * La clase SQLClient singleton al trtatarse de un recurso compartido.
 *
 * @author David Maza
 */
public class ConnectionMySQL {

	private static ConnectionMySQL instance;

	private Connection connection;

	private final String LOCALHOSTS = "127.0.0.1";
	private final String SQL_PORT_DEFAULT = "3306";
	private final String SQL_USER_DEFAULT = "root";
	private final String SQL_PASWORD_DEFAULT = "";

	private ConnectionMySQL() {
	}

	public static ConnectionMySQL craeteConnectionMySQL() {
		return instance == null ? instance = new ConnectionMySQL() : instance;
	}

	/**
	 * Conecta a la base de datos MySQL especificada.
	 *
	 * @param hosts la dirección IP del servidor MySQL.
	 * @param port la dirección IP del servidor MySQL.
	 * @param user el nombre de usuario de la base de datos MySQL.
	 * @param password la contraseña de la base de datos MySQL.
	 * @return una conexión (sesión). 
	 *
	 * @throws SQLException si ocurre un error al conectar a la base de datos MySQL.
	 */
	public Connection connection(String hosts, String port, String user, String password) {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://" + hosts + ":" + port + " ", user, password);
				System.out.println("CONECTADO AL PUERTO: " + port + "\n");
			} catch (SQLException ex) {
				Logger.getLogger(ConnectionMySQL.class.getName()).log(Level.SEVERE, "FALLO en la conecxion\n", ex);
			}
		}
		return connection;
	}

	public String getLOCALHOSTS() {
		return LOCALHOSTS;
	}

	public String getSQL_PORT_DEFAULT() {
		return SQL_PORT_DEFAULT;
	}

	public String getSQL_USER_DEFAULT() {
		return SQL_USER_DEFAULT;
	}

	public String getSQL_PASWORD_DEFAULT() {
		return SQL_PASWORD_DEFAULT;
	}
}