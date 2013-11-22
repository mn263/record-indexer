package shared.communication.params;

/**
 * User: mn263
 * Date: 10/18/13
 * Time: 12:04 PM
 */
public class GetProjects_Params {

	private String userName;
	private String password;

	/**
	 * required parameters to get projects
	 *
	 * @param userName String
	 * @param password String
	 */
	public GetProjects_Params(String userName, String password) {
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
