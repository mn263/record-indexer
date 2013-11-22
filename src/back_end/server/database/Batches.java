package back_end.server.database;

import shared.communication.params.DownloadBatch_Params;
import shared.communication.params.GetSampleImage_Params;
import shared.communication.params.SubmitBatch_Params;
import shared.communication.results.DownloadBatch_Result;
import shared.communication.results.GetSampleImage_Result;
import shared.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for Batches
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:01 AM
 */
public class Batches {

	DatabaseAccessUtils databaseAccessUtils = new DatabaseAccessUtils();

	/**
	 * Gets a sample Batch for a specific Project
	 *
	 * @return BatchDTO a sample Batch for the specified Project
	 */
	public static DownloadBatch_Result downloadBatch(DownloadBatch_Params downloadBatch_params) {
		try {
			int projectId = downloadBatch_params.getProjectId();
			ProjectDTO projectDTO = null;
			List<FieldDTO> fieldDTOList = null;
			BatchDTO batchDTO = null;
			List<RecordDTO> recordDTOList = null;
			Boolean alreadyHasABatch = Users.hasAssignedBatch(downloadBatch_params.getUserName());
			if (!alreadyHasABatch) {
				projectDTO = Projects.getProjectById(projectId);
				fieldDTOList = Fields.getAllFieldsForProject(projectId);
				batchDTO = Batches.getUnassignedBatchForProject(projectId);
				if (batchDTO != null) {
					recordDTOList = Records.getAllRecordsForBatch(batchDTO.getId());
				}
			}
			if (!alreadyHasABatch && projectDTO != null && fieldDTOList != null && batchDTO != null && recordDTOList != null) {
				int batchId = batchDTO.getId();
				String imageURL = batchDTO.getFilePath();
				int firstYCoord = projectDTO.getFirstYCoord();
				int recordHeight = projectDTO.getRecordHeight();
				int recordCount = projectDTO.getRecordsPerImage();
				int fieldCount = fieldDTOList.size();
				DownloadBatch_Result downloadBatch_result = new DownloadBatch_Result(batchId, projectId, imageURL, firstYCoord, recordHeight, recordCount, fieldCount, fieldDTOList);
				Batches.assignBatchToUser(downloadBatch_params.getUserName(), downloadBatch_result.getBatchId());
				return downloadBatch_result;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets a sample Batch for a specific Project
	 *
	 * @param projectId int projectId
	 * @return BatchDTO a sample Batch for the specified Project
	 * @throws Exception
	 */
	public static BatchDTO getSampleBatch(int projectId) throws Exception {
		if (projectId > Projects.getProjectCount() || projectId < 1) {
			return null;
		}
		String getAvailableProjects = "SELECT * FROM batch where project_id = ? AND is_indexed = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(projectId));
		stmt.setString(2, "false");
		ResultSet rs = stmt.executeQuery();

		int id = rs.getInt(1);
		int project_id = rs.getInt(2);
		boolean isIndexed = rs.getBoolean(3);
		String assignedUser = rs.getString(4);
		String filePath = rs.getString(5);
		BatchDTO batchDTO = new BatchDTO(id, project_id, isIndexed, assignedUser, filePath);
		stmt.close();
		return batchDTO;
	}

	private static BatchDTO getUnassignedBatchForProject(int projectId) throws Exception {
		String getAvailableProjects = "SELECT * FROM batch where project_id = ? and assigned_user is null and is_indexed = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(projectId));
		stmt.setString(2, "false");
		ResultSet rs = stmt.executeQuery();
		BatchDTO batchDTO = null;
		if (rs.next()) {
			int id = rs.getInt(1);
			int project_id = rs.getInt(2);
			boolean isIndexed = rs.getBoolean(3);
			String assignedUser = rs.getString(4);
			String filePath = rs.getString(5);
			batchDTO = new BatchDTO(id, project_id, isIndexed, assignedUser, filePath);
		}
		stmt.close();
		return batchDTO;
	}

	/**
	 * Gets an unassigned Batch for a specific Project
	 *
	 * @param projectId int ProjectID
	 * @return List<BatchDTO> a list of all indexed Batches for the specified Project
	 * @throws Exception
	 */
	public static List<BatchDTO> getAllIndexedBatchesForProjectId(int projectId) throws Exception {
		List<BatchDTO> batchDTOList = new ArrayList<BatchDTO>();
		String getAvailableProjects = "SELECT * FROM batch where project_id = ? and is_indexed = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(projectId));
		stmt.setString(2, "true");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int project_id = rs.getInt(2);
			boolean isIndexed = rs.getBoolean(3);
			String assignedUser = rs.getString(4);
			String filePath = rs.getString(5);
			BatchDTO batchDTO = new BatchDTO(id, project_id, isIndexed, assignedUser, filePath);
			batchDTOList.add(batchDTO);
		}
		stmt.close();
		return batchDTOList;
	}

	/**
	 * Gets an Batch by its id
	 *
	 * @param id int BatchID
	 * @return List<BatchDTO> a list of all indexed Batches for the specified Project
	 * @throws Exception
	 */
	public static BatchDTO getBatchById(int id) throws Exception {
		BatchDTO batchDTO = null;
		String getAvailableProjects = "SELECT * FROM batch where id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(id));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int project_id = rs.getInt(2);
			boolean isIndexed = rs.getBoolean(3);
			String assignedUser = rs.getString(4);
			String filePath = rs.getString(5);
			batchDTO = new BatchDTO(id, project_id, isIndexed, assignedUser, filePath);
		}
		stmt.close();
		return batchDTO;
	}

	/**
	 * Assigns a Batch to a specified User
	 *
	 * @param userName String
	 * @param batchId  int
	 */
	public static void assignBatchToUser(String userName, int batchId) throws Exception {
		String sql = "UPDATE batch SET assigned_user = ? where id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, userName);
		stmt.setString(2, Integer.toString(batchId));
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * updates a specific Batch
	 *
	 * @param batchId int BatchID
	 * @throws Exception
	 */
	public static void markBatchAsIndexed(int batchId) throws Exception {
		String sql = "UPDATE batch SET is_indexed = ?, assigned_user = null WHERE  id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, "true");
		stmt.setString(2, Integer.toString(batchId));
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Submits a batch
	 *
	 * @param submitBatch_params SubmitBatch_Params
	 * @return boolean - whether or not it submitted
	 */
	public static boolean submitBatch(SubmitBatch_Params submitBatch_params) {
		try {
			UserDTO userDTO = Users.getUserByUserName(submitBatch_params.getUserName(), submitBatch_params.getPassword());
			if (Batches.isBatchIndexed(submitBatch_params.getBatchId())) {
				return false;
			}
			if (userDTO != null) {
				BatchDTO batchDTO = Batches.getBatchById(submitBatch_params.getBatchId());
				if (batchDTO == null || batchDTO.getAssignedUser() == null) {
					return false;
				}
				if (batchDTO.isIndexed() || !batchDTO.getAssignedUser().equals(submitBatch_params.getUserName())) {
					return false;
				}
				Batches.markBatchAsIndexed(submitBatch_params.getBatchId());
				List<SubmitBatch_Params.RecordValuesSubBatch> fieldValuesList = submitBatch_params.getFieldValues();
				int recordsIndexed = fieldValuesList.size();
				if (recordsIndexed == 1 && Records.getAllRecordsForBatch(submitBatch_params.getBatchId()).size() != 1) {
					recordsIndexed--;
				}
				int projectId = Batches.getBatchById(submitBatch_params.getBatchId()).getProjectId();
				int recordsPerImage = (Projects.getProjectById(projectId)).getRecordsPerImage();
				int fieldsPerProject = Fields.getFieldCountByProject(projectId);
				Records.updateRecords(Fields.getAllFieldsForProject(projectId), fieldValuesList, submitBatch_params.getBatchId());
				if (recordsIndexed != (recordsPerImage * fieldsPerProject) &&
						fieldValuesList.size() != (Projects.getProjectById(projectId)).getRecordsPerImage()) {
					return false;
				} else {
					Users.updateUsersIndexedCount(submitBatch_params.getUserName(), recordsIndexed);
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks whether a batch has been indexed or not based on its ID
	 *
	 * @param batchId int
	 * @return boolean - whether or not it has been indexed
	 */
	private static boolean isBatchIndexed(int batchId) throws SQLException {
		String sql = "SELECT is_indexed FROM batch WHERE  id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, Integer.toString(batchId));
		ResultSet rs = stmt.executeQuery();
		boolean isIndexed = rs.getBoolean(1);
		stmt.close();
		return isIndexed;
	}

	/**
	 * Gets a sample image for the user
	 *
	 * @param getSampleImage_params GetSampleImage_Params
	 * @return GetSampleImage_Result
	 */
	public static GetSampleImage_Result getSampleImage(GetSampleImage_Params getSampleImage_params) {
		try {
			UserDTO userDTO = Users.getUserByUserName(getSampleImage_params.getUserName(), getSampleImage_params.getPassword());
			GetSampleImage_Result getSampleImage_result = null;
			if (userDTO != null) {
				BatchDTO batchDTO = Batches.getSampleBatch(getSampleImage_params.getProjectId());
				if (batchDTO == null) {
					return null;
				}
				getSampleImage_result = new GetSampleImage_Result(batchDTO.getFilePath());
			}
			if (getSampleImage_result == null) {
				return null;
			} else {
				return getSampleImage_result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds a new Batch to the database
	 *
	 * @param batch BatchDTO
	 * @throws Exception
	 */
	public void add(BatchDTO batch) throws Exception {
		String sql = "insert into batch (project_id, is_indexed, assigned_user, file_path) values (?, ?, ?, ?)";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, Integer.toString(batch.getProjectId()));
		stmt.setString(2, String.valueOf(batch.isIndexed()));
		stmt.setString(3, batch.getAssignedUser());
		stmt.setString(4, batch.getFilePath());
		if (stmt.executeUpdate() == 1) {
			batch.setId(databaseAccessUtils.getSavedRowId(Database.connection));
		} else {
			System.out.println("Error saving Batch to database");
		}
		stmt.close();
	}
}
