package shared.communication.results;

import shared.model.FieldDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Gets a Batch result for the user to index
 * User: mn263
 * Date: 10/12/13
 * Time: 4:06 PM
 */
public class DownloadBatch_Result {

	private int batchId;
	private int projectId;
	private String imageURL;
	private int firstYCoord;
	private int recordHeight;
	private int recordCount;
	private int fieldCount;
	private List<FieldInformation> fieldInformationList = new ArrayList<FieldInformation>();

	/**
	 * Gets a Batch for a specified project
	 *
	 * @param batchId      int
	 * @param projectId    int
	 * @param imageURL     String
	 * @param firstYCoord  int
	 * @param recordHeight int
	 * @param recordCount  int
	 * @param fieldCount   int
	 */
	public DownloadBatch_Result(int batchId, int projectId, String imageURL, int firstYCoord,
								int recordHeight, int recordCount, int fieldCount, List<FieldDTO> fieldDTOList) {
		this.batchId = batchId;
		this.projectId = projectId;
		this.imageURL = imageURL;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
		this.recordCount = recordCount;
		this.fieldCount = fieldCount;
		for (FieldDTO fieldDTO : fieldDTOList) {
			int fieldId = fieldDTO.getId();
			int fieldNumber = fieldDTO.getPosition();
			String fieldTitle = fieldDTO.getTitle();
			String helpURL = fieldDTO.getHelpFilePath();
			int xCoord = fieldDTO.getXCoord();
			int pixelWidth = fieldDTO.getWidth();
			String knownDataPath = fieldDTO.getKnownDataPath();
			FieldInformation fieldInformation = new FieldInformation(fieldId, fieldNumber, fieldTitle, helpURL, xCoord, pixelWidth, knownDataPath);
			fieldInformationList.add(fieldInformation);
		}
	}

	public int getBatchId() {
		return batchId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getImageURL() {
		return imageURL;
	}

	public int getFirstYCoord() {
		return firstYCoord;
	}

	public int getRecordHeight() {
		return recordHeight;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public int getFieldCount() {
		return fieldCount;
	}

	public List<FieldInformation> getFieldInformationList() {
		return fieldInformationList;
	}

	public class FieldInformation {
		private int fieldId;
		private int fieldNumber;
		private String fieldTitle;
		private String helpURL;
		private int xCoord;
		private int pixelWidth;
		private String knownDataPath;

		/**
		 * info about the found fields
		 *
		 * @param fieldId       int
		 * @param fieldNumber   int
		 * @param fieldTitle    String
		 * @param helpURL       String
		 * @param xCoord        int
		 * @param pixelWidth    int
		 * @param knownDataPath String
		 */
		public FieldInformation(int fieldId, int fieldNumber, String fieldTitle, String helpURL, int xCoord, int pixelWidth, String knownDataPath) {
			this.fieldId = fieldId;
			this.fieldNumber = fieldNumber;
			this.fieldTitle = fieldTitle;
			this.helpURL = helpURL;
			this.xCoord = xCoord;
			this.pixelWidth = pixelWidth;
			this.knownDataPath = knownDataPath;
		}

		public int getFieldId() {
			return fieldId;
		}

		public int getFieldNumber() {
			return fieldNumber;
		}

		public String getFieldTitle() {
			return fieldTitle;
		}

		public String getHelpURL() {
			return helpURL;
		}

		public int setXCoord() {
			return xCoord;
		}

		public int getPixelWidth() {
			return pixelWidth;
		}

		public String getKnownDataPath() {
			return knownDataPath;
		}
	}
}
