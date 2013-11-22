package shared.model;

import org.w3c.dom.Element;
import shared.DataImporter;

/**
 * Represents a specific row in the Field table
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class FieldDTO {

	private int id = -1;
	private int projectId;
	private String title;
	private int xCoord;
	private int width;
	private String helpFilePath;
	private String knownDataPath;
	private int position;

	public FieldDTO(int position, int projectId, Element fieldElement) {
		this.projectId = projectId;
		this.title = DataImporter.getValue((Element) fieldElement.getElementsByTagName("title").item(0));
		this.xCoord = Integer.parseInt(DataImporter.getValue((Element) fieldElement.getElementsByTagName("xcoord").item(0)));
		this.width = Integer.parseInt(DataImporter.getValue((Element) fieldElement.getElementsByTagName("width").item(0)));
		this.helpFilePath = DataImporter.getValue((Element) fieldElement.getElementsByTagName("helphtml").item(0));
		this.knownDataPath = DataImporter.getValue((Element) fieldElement.getElementsByTagName("knowndata").item(0));
		this.position = position;
	}

	public FieldDTO(int id, int projectId, String title, int xCoord, int width, String helpFilePath,
					String knownDataPath, int position) {

		this.id = id;
		this.projectId = projectId;
		this.title = title;
		this.xCoord = xCoord;
		this.width = width;
		this.helpFilePath = helpFilePath;
		this.knownDataPath = knownDataPath;
		this.position = position;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getXCoord() {
		return xCoord;
	}

	public int getWidth() {
		return width;
	}

	public String getHelpFilePath() {
		return helpFilePath;
	}

	public String getKnownDataPath() {
		return knownDataPath;
	}

	public int getPosition() {
		return position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldDTO fieldDTO = (FieldDTO) o;

		if (id != fieldDTO.id) return false;
		if (projectId != fieldDTO.projectId) return false;
		if (width != fieldDTO.width) return false;
		if (xCoord != fieldDTO.xCoord) return false;
		if (helpFilePath != null ? !helpFilePath.equals(fieldDTO.helpFilePath) : fieldDTO.helpFilePath != null)
			return false;
		if (knownDataPath != null ? !knownDataPath.equals(fieldDTO.knownDataPath) : fieldDTO.knownDataPath != null)
			return false;
		if (!title.equals(fieldDTO.title)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + projectId;
		result = 31 * result + title.hashCode();
		result = 31 * result + xCoord;
		result = 31 * result + width;
		result = 31 * result + (helpFilePath != null ? helpFilePath.hashCode() : 0);
		result = 31 * result + (knownDataPath != null ? knownDataPath.hashCode() : 0);
		return result;
	}
}
