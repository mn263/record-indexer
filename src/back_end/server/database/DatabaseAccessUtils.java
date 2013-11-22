package back_end.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: matt nielson
 * Date: 10/21/13
 * Time: 9:14 PM
 */
public class DatabaseAccessUtils {

	public int getSavedRowId(Connection connection) throws SQLException {
		Statement keyStmt = connection.createStatement();
		ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		keyRS.next();
		int id = keyRS.getInt(1);   // ID of the new object
		keyRS.close();
		keyStmt.close();
		return id;
	}
}
