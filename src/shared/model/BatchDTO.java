package shared.model;

import org.w3c.dom.Element;
import shared.DataImporter;

/**
 * Represents a specific column in the Batch table
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class BatchDTO {

	private int id = -1;
	private int projectId;
	private boolean is_indexed = false;
	//the current user's userName
	private String assignedUser;
	private String filePath;

	public BatchDTO(int projectId, Element batchElement) {
		this.projectId = projectId;
		this.assignedUser = null;
		this.filePath = DataImporter.getValue((Element) batchElement.getElementsByTagName("file").item(0));
	}

	public BatchDTO(int id, int project_id, boolean indexed, String assignedUser, String filePath) {
		this.id = id;
		this.projectId = project_id;
		this.is_indexed = indexed;
		this.assignedUser = assignedUser;
		this.filePath = filePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public boolean isIndexed() {
		return is_indexed;
	}

	public String getAssignedUser() {
		return assignedUser;
	}

	public String getFilePath() {
		return filePath;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BatchDTO batchDTO = (BatchDTO) o;

		if (id != batchDTO.id) return false;
		if (projectId != batchDTO.projectId) return false;
		if (assignedUser != null ? !assignedUser.equals(batchDTO.assignedUser) : batchDTO.assignedUser != null)
			return false;
		if (!filePath.equals(batchDTO.filePath)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + projectId;
		result = 31 * result + (assignedUser != null ? assignedUser.hashCode() : 0);
		result = 31 * result + filePath.hashCode();
		return result;
	}
}
