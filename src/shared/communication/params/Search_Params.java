package shared.communication.params;

import java.util.List;

/**
 * User: mn263
 * Date: 10/18/13
 * Time: 12:24 PM
 */
public class Search_Params {

	private String userName;
	private String password;
	private List<String> filedIDs;
	private List<String> searchValues;

	/**
	 * required params for searching
	 *
	 * @param userName String
	 * @param password String
	 * @param fieldIDs String of comma separated fieldIDs
	 */
	public Search_Params(String userName, String password, List<String> fieldIDs, List<String> searchValues) {
		this.userName = userName;
		this.password = password;
		this.filedIDs = fieldIDs;
		this.searchValues = searchValues;
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

	public List<String> getFiledIDs() {
		return filedIDs;
	}

	public List<String> getSearchValues() {
		return searchValues;
	}
}