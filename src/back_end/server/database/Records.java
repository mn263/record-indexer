package back_end.server.database;

import shared.communication.params.SubmitBatch_Params.RecordValuesSubBatch;
import shared.model.BatchDTO;
import shared.model.FieldDTO;
import shared.model.ProjectDTO;
import shared.model.RecordDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for Records
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:01 AM
 */
public class Records {
	DatabaseAccessUtils databaseAccessUtils = new DatabaseAccessUtils();

	/**
	 * Gets all Records for a Batch
	 *
	 * @param batchId int BatchID
	 * @return List<RecordDTO> List of corresponding Records
	 * @throws Exception
	 */
	public static ArrayList<RecordDTO> getAllRecordsForBatch(int batchId) throws Exception {
		ArrayList<RecordDTO> recordList = new ArrayList<RecordDTO>();
		String getAvailableProjects = "SELECT * FROM record where image_id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(batchId));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int batch_id = rs.getInt(2);
			String value = rs.getString(3);
			int position = rs.getInt(4);
			int recNum = rs.getInt(5);
			RecordDTO recordDTO = new RecordDTO(id, batch_id, value, position, recNum);
			recordList.add(recordDTO);
		}
		stmt.close();
		return recordList;
	}

	/**
	 * Adds a new Record to the database
	 *
	 * @param record RecordDTO
	 * @throws Exception
	 */
	public void add(RecordDTO record) throws Exception {
		String sql = "insert into record (image_id, value, position, rec_num) values (?, ?, ?, ?)";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, Integer.toString(record.getBatchId()));
		stmt.setString(2, record.getValue());
		stmt.setString(3, Integer.toString(record.getPosition()));
		stmt.setString(4, Integer.toString(record.getRecNumber()));

		if (stmt.executeUpdate() == 1) {
			record.setId(databaseAccessUtils.getSavedRowId(Database.connection));
		} else {
			System.out.println("Error saving Record to database");
			throw new Exception();
		}
		stmt.close();
	}

	public static void updateRecords(List<FieldDTO> allFieldsForProject,
									 List<RecordValuesSubBatch> fieldValuesList, int batchId) throws Exception {
		BatchDTO batchDTO = Batches.getBatchById(batchId);
		int projectId = batchDTO.getProjectId();
		ProjectDTO projectDTO = Projects.getProjectById(projectId);
		int recordNumber = 0;
		for (RecordValuesSubBatch fieldValue : fieldValuesList) {
			recordNumber++;
			int position = 0;
			List<String> recordValues = fieldValue.getRecordValues();
			for (String recordValue : recordValues) {
				boolean addRecord = true;
				List<RecordDTO> recordsForBatch = Records.getAllRecordsForBatch(batchId);
				for (RecordDTO recordDTO : recordsForBatch) {
					if (recordValue.equalsIgnoreCase(recordDTO.getValue()) && position == recordDTO.getPosition()) {
						addRecord = false;
					}
				}
				if (addRecord) {
					position++;
					String sql = "insert into record (image_id, value, position, rec_num) values (?, ?, ?, ?)";
					PreparedStatement stmt = Database.connection.prepareStatement(sql);
					stmt.setString(1, Integer.toString(batchDTO.getId()));
					stmt.setString(2, recordValue);
					stmt.setString(3, Integer.toString(position));
					stmt.setString(4, Integer.toString(recordNumber));
					stmt.executeUpdate();
					stmt.close();
					if (position == projectDTO.getRecordsPerImage()) {
						position = 0;
					}
				}
			}
		}
	}
}
