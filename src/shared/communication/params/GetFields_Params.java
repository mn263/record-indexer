package shared.communication.params;

/**
 * User: mn263
 * Date: 10/18/13
 * Time: 12:16 PM
 */
public class GetFields_Params {

	private String userName;
	private String password;
	private int projectId;

	/**
	 * required params to get fields
	 *
	 * @param userName  String
	 * @param password  String
	 * @param projectId int
	 */
	public GetFields_Params(String userName, String password, int projectId) {
		this.userName = userName;
		this.password = password;
		this.projectId = projectId;
	}

	/**
	 * required params to get fields
	 *
	 * @param userName String
	 * @param password String
	 */
	public GetFields_Params(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.projectId = -1;
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

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
