package shared.communication.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns information about all of the fields for the specified project
 * User: mn263
 * Date: 10/12/13
 * Time: 4:19 PM
 */
public class GetFields_Result {

	private List<FieldInfo> fields = new ArrayList<FieldInfo>();

	public List<FieldInfo> getFields() {
		return fields;
	}

	public void setFields(List<FieldInfo> fields) {
		this.fields = fields;
	}

	public void addFieldInfo(int projectId, int id, String title) {
		FieldInfo fieldInfo = new FieldInfo(projectId, id, title);
		fields.add(fieldInfo);
	}

	public class FieldInfo {
		private int projectId;
		private int fieldId;
		private String fieldTitle;

		/**
		 * info about the found fields
		 *
		 * @param projectId  int
		 * @param fieldId    int
		 * @param fieldTitle String
		 */
		public FieldInfo(int projectId, int fieldId, String fieldTitle) {
			this.projectId = projectId;
			this.fieldId = fieldId;
			this.fieldTitle = fieldTitle;
		}

		public int getProjectId() {
			return projectId;
		}

		public void setProjectId(int projectId) {
			this.projectId = projectId;
		}

		public int getFieldId() {
			return fieldId;
		}

		public String getFieldTitle() {
			return fieldTitle;
		}
	}
}
