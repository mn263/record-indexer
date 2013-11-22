package shared.communication.params;

import java.util.ArrayList;
import java.util.List;

/**
 * submits the indexed record field values for a batch to the Server
 * * User: mn263
 * Date: 10/12/13
 * Time: 4:14 PM
 */
public class SubmitBatch_Params {

	private String userName;
	private String password;
	private int batchId;
	private List<RecordValuesSubBatch> fieldValues = new ArrayList<RecordValuesSubBatch>();

	/**
	 * The required params for
	 *
	 * @param userName String
	 * @param password String
	 * @param batchId  int
	 */
	public SubmitBatch_Params(String userName, String password, int batchId) {
		this.userName = userName;
		this.password = password;
		this.batchId = batchId;
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

	public int getBatchId() {
		return batchId;
	}

	public List<RecordValuesSubBatch> getFieldValues() {
		return fieldValues;
	}

	public void addRecordToField(List<String> recordValues) {
		RecordValuesSubBatch recordValuesSubBatch = new RecordValuesSubBatch(recordValues);
		this.fieldValues.add(recordValuesSubBatch);
	}

	public class RecordValuesSubBatch {

		private List<String> recordValues;

		public RecordValuesSubBatch(List<String> recordValues) {
			this.recordValues = recordValues;
		}

		public List<String> getRecordValues() {
			return recordValues;
		}
	}
}
