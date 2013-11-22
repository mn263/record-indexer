package back_end.server.database;

import shared.communication.params.ValidateUser_Params;
import shared.communication.results.ValidateUser_Result;
import shared.model.UserDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Accesses the database for Users
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:01 AM
 */
public class Users {

	/**
	 * Gets current Users by his/her UserName
	 *
	 * @param userName String
	 * @return UserDTO
	 * @throws Exception
	 */
	public static UserDTO getUserByUserName(String userName, String password) throws Exception {
		String getUser = "SELECT * FROM user where user_name = ? and password = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getUser);
		stmt.setString(1, userName);
		stmt.setString(2, password);

		ResultSet rs = stmt.executeQuery();
		UserDTO userDTO = null;
		while (rs.next()) {
			String userNameRS = rs.getString(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			String passwordRS = rs.getString(4);
			String emailAddress = rs.getString(5);
			int indexedRecord = rs.getInt(6);
			userDTO = new UserDTO(userNameRS, firstName, lastName, passwordRS, emailAddress, indexedRecord);
		}
		stmt.close();
		return userDTO;
	}

	/**
	 * Adds a new User to the database
	 *
	 * @param user UserDTO
	 * @throws Exception
	 */
	public static void add(UserDTO user) throws Exception {
		String insertUserSQL = "insert into user (user_name, password, f_name, l_name, email_address, indexed_record) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = Database.connection.prepareStatement(insertUserSQL);
		stmt.setString(1, user.getUserName());
		stmt.setString(2, user.getPassword());
		stmt.setString(3, user.getFirstName());
		stmt.setString(4, user.getLastName());
		stmt.setString(5, user.getEmail());
		stmt.setString(6, Integer.toString(user.getIndexedCount()));
		if (stmt.executeUpdate() != 1) {
			throw new Exception();
		}
		stmt.close();
	}

	/**
	 * updates a specific User's account
	 *
	 * @param userName String User's username
	 * @throws Exception
	 */
	public static Boolean hasAssignedBatch(String userName) throws Exception {
		String getUser = "SELECT * FROM batch where assigned_user = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getUser);
		stmt.setString(1, userName);

		ResultSet rs = stmt.executeQuery();
		String next = null;
		if (rs.next()) {
			next = rs.getString(1);
		}
		boolean hasAssignedBatch = (next != null);
		stmt.close();
		return hasAssignedBatch;
	}

	/**
	 * updates a specific User's account
	 *
	 * @param userName    String User's username
	 * @param recordCount int The number of fields that were indexed
	 * @throws Exception
	 */
	public static void updateUsersIndexedCount(String userName, int recordCount) throws Exception {
		String updateUser = "UPDATE user SET indexed_record = (indexed_record + ?) WHERE user_name = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(updateUser);
		stmt.setString(1, Integer.toString(recordCount));
		stmt.setString(2, userName);
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Validates a user's name and password
	 *
	 * @param validateUser_params ValidateUser_Params
	 */
	public static ValidateUser_Result validateUser(ValidateUser_Params validateUser_params) {
		try {
			UserDTO userDTO = Users.getUserByUserName(validateUser_params.getUserName(), validateUser_params.getPassword());
			if (userDTO != null) {
				return new ValidateUser_Result(true, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getIndexedCount());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
