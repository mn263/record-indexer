package shared.communication.results;

/**
 * Validates that a Given username and password match up for a user in the database
 * User: mn263
 * Date: 10/12/13
 * Time: 3:31 PM
 */
public class ValidateUser_Result {

	private boolean isUser;
	private String userNameFirst;
	private String userNameLast;
	private int recordCount;

	/**
	 * response params to validating a user
	 *
	 * @param isUser        boolean
	 * @param userNameFirst String
	 * @param userNameLast  String
	 * @param recordCount   int
	 */
	public ValidateUser_Result(boolean isUser, String userNameFirst, String userNameLast, int recordCount) {
		this.isUser = isUser;
		this.userNameFirst = userNameFirst;
		this.userNameLast = userNameLast;
		this.recordCount = recordCount;
	}

	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean user) {
		this.isUser = user;
	}

	public String getUserNameFirst() {
		return userNameFirst;
	}

	public String getUserNameLast() {
		return userNameLast;
	}

	public int getRecordCount() {
		return recordCount;
	}
}
