package shared.communication.params;

/**
 * User: mn263
 * Date: 10/18/13
 * Time: 11:51 AM
 */
public class ValidateUser_Params {

	private String userName;
	private String password;

	/**
	 * Contains the required params to validate the user
	 *
	 * @param userName String
	 * @param password String
	 */
	public ValidateUser_Params(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
