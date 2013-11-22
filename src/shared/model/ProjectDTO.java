package shared.model;

import org.w3c.dom.Element;
import shared.DataImporter;

/**
 * Represents a specific row in the Project table
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class ProjectDTO {

	private int id = -1;
	private String title = null;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;

	public ProjectDTO(Element projectElement) {
		this.title = DataImporter.getValue((Element) projectElement.getElementsByTagName("title").item(0));
		this.recordsPerImage = Integer.parseInt(DataImporter.getValue((Element) projectElement.getElementsByTagName("recordsperimage").item(0)));
		this.firstYCoord = Integer.parseInt(DataImporter.getValue((Element) projectElement.getElementsByTagName("firstycoord").item(0)));
		this.recordHeight = Integer.parseInt(DataImporter.getValue((Element) projectElement.getElementsByTagName("recordheight").item(0)));
	}

	public ProjectDTO(int id, String title, int recordsPerImage, int firstYCoord, int recordHeight) {
		this.id = id;
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRecordsPerImage() {
		return recordsPerImage;
	}

	public int getFirstYCoord() {
		return firstYCoord;
	}

	public int getRecordHeight() {
		return recordHeight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProjectDTO)) return false;

		ProjectDTO that = (ProjectDTO) o;

		if (firstYCoord != that.firstYCoord) return false;
		if (id != that.id) return false;
		if (recordHeight != that.recordHeight) return false;
		if (recordsPerImage != that.recordsPerImage) return false;
		if (!title.equals(that.title)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + title.hashCode();
		result = 31 * result + recordsPerImage;
		result = 31 * result + firstYCoord;
		result = 31 * result + recordHeight;
		return result;
	}
}
