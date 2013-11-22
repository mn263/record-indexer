package back_end.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:24 AM
 */
public class Database {

	public static Connection connection;
	public Projects projects = new Projects();
	public Batches batches = new Batches();
	public Fields fields = new Fields();
	public Records records = new Records();

	public Database() {
		reset();
	}

	/**
	 * Resets the database to whatever is in the Records.xml file (or any file specified for reading in)
	 */
	private static void reset() {
		try {
			Class.forName("org.sqlite.JDBC");
			startTransaction();

			Statement stmt = connection.createStatement();

			String createUserTableSQL = "CREATE  TABLE user (user_name VARCHAR PRIMARY KEY  NOT NULL  UNIQUE , " +
					"f_name VARCHAR NOT NULL , l_name VARCHAR NOT NULL , password VARCHAR NOT NULL , " +
					"email_address VARCHAR NOT NULL , indexed_record INTEGER NOT NULL )";

			String createProjectTableSQL = "CREATE  TABLE project (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , " +
					"title VARCHAR NOT NULL , records_per_image INTEGER NOT NULL , first_y_coord INTEGER NOT NULL," +
					"record_height INTEGER NOT NULL )";

			String createFieldTableSQL = "CREATE  TABLE field (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , " +
					"project_id INTEGER NOT NULL ,title VARCHAR NOT NULL , x_coord INTEGER NOT NULL , " +
					"width INTEGER NOT NULL , help_html VARCHAR, known_data VARCHAR, position INTEGER NOT NULL)";

			String createBatchTableSQL = "CREATE  TABLE batch (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , " +
					"project_id  INTEGER NOT NULL , is_indexed BOOL DEFAULT FALSE, assigned_user VARCHAR, " +
					"file_path VARCHAR NOT NULL)";

			String createRecordTableSQL = "CREATE  TABLE record (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , " +
					"image_id INTEGER NOT NULL , value VARCHAR, position INTEGER NOT NULL, rec_num INTEGER NOT NULL)";

			stmt.executeUpdate(createUserTableSQL);
			stmt.executeUpdate(createProjectTableSQL);
			stmt.executeUpdate(createFieldTableSQL);
			stmt.executeUpdate(createBatchTableSQL);
			stmt.executeUpdate(createRecordTableSQL);
			stmt.close();

			endTransaction(true);
		} catch (Exception e) {
			e.printStackTrace();
			endTransaction(false);
		}
	}

	/**
	 * Opens a connection to the database
	 */
	public static void startTransaction() {
		assert connection == null;
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
			connection = DriverManager.getConnection("jdbc:sqlite:recordindexer.sqlite");
			connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Opening database");
		}
	}

	/**
	 * Closes the current connection to the database and either commits or rolls back
	 *
	 * @param commit boolean
	 */
	public static void endTransaction(boolean commit) {
		assert connection != null;
		try {
			if (commit) {
				connection.commit();
			} else {
				connection.rollback();
			}
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error Closing database");
		}
	}
}

