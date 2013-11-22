package shared.communication.params;

/**
 * User: mn263
 * Date: 10/18/13
 * Time: 11:35 AM
 */
public class DownloadBatch_Params {

	private String userName;
	private String password;
	private int projectId;

	/**
	 * Gets a Batch for a specified project
	 *
	 * @param userName  String
	 * @param password  String
	 * @param projectId int
	 */
	public DownloadBatch_Params(String userName, String password, int projectId) {
		this.userName = userName;
		this.password = password;
		this.projectId = projectId;
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
