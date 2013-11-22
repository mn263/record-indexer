package shared.model;

import org.w3c.dom.Element;
import shared.DataImporter;

/**
 * Represents a specific column in the User table
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class UserDTO {

	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private int indexedCount;

	public UserDTO(Element userElement) {
		this.userName = DataImporter.getValue((Element) userElement.getElementsByTagName("username").item(0));
		this.password = DataImporter.getValue((Element) userElement.getElementsByTagName("password").item(0));
		this.firstName = DataImporter.getValue((Element) userElement.getElementsByTagName("firstname").item(0));
		this.lastName = DataImporter.getValue((Element) userElement.getElementsByTagName("lastname").item(0));
		this.email = DataImporter.getValue((Element) userElement.getElementsByTagName("email").item(0));
		this.indexedCount = Integer.parseInt(DataImporter.getValue((Element) userElement.getElementsByTagName("indexedrecords").item(0)));
	}

	public UserDTO(String userName, String firstName, String lastName, String password, String email, int indexedCount) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.indexedCount = indexedCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIndexedCount() {
		return indexedCount;
	}

	public void setIndexedCount(int indexedCount) {
		this.indexedCount = indexedCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserDTO userDTO = (UserDTO) o;

		if (indexedCount != userDTO.indexedCount) return false;
		if (!email.equals(userDTO.email)) return false;
		if (!firstName.equals(userDTO.firstName)) return false;
		if (!lastName.equals(userDTO.lastName)) return false;
		if (!password.equals(userDTO.password)) return false;
		if (!userName.equals(userDTO.userName)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userName.hashCode();
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + indexedCount;
		return result;
	}
}
