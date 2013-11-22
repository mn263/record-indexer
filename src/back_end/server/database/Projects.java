package back_end.server.database;


import shared.communication.params.GetProjects_Params;
import shared.communication.params.Search_Params;
import shared.communication.results.GetProjects_Result;
import shared.communication.results.Search_Result;
import shared.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Accesses the database for Projects
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:00 AM
 */
public class Projects {
	DatabaseAccessUtils databaseAccessUtils = new DatabaseAccessUtils();

	/**
	 * Gets a specific Project by its ID
	 *
	 * @param projectId int
	 * @return ProjectDTO
	 * @throws Exception
	 */
	public static ProjectDTO getProjectById(int projectId) throws Exception {
		String getAvailableProjects = "SELECT * FROM project where id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(projectId));
		ResultSet rs = stmt.executeQuery();
		ProjectDTO projectDTO = null;
		while (rs.next()) {
			int id = rs.getInt(1);
			String title = rs.getString(2);
			int recordsPerImage = rs.getInt(3);
			int firstYCoord = rs.getInt(4);
			int recordHeight = rs.getInt(5);
			projectDTO = new ProjectDTO(id, title, recordsPerImage, firstYCoord, recordHeight);
		}
		stmt.close();
		return projectDTO;
	}

	/**
	 * Gets the number of projects in the database
	 *
	 * @throws Exception
	 */
	public static int getProjectCount() throws Exception {
		String getAvailableProjects = "SELECT COUNT(*) FROM project";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		ResultSet rs = stmt.executeQuery();
		int count = rs.getInt(1);
		stmt.close();
		return count;
	}

	/**
	 * Gets a project from the database for a User
	 *
	 * @param getProjects_params GetProjects_Params
	 */
	public static GetProjects_Result getProjects(GetProjects_Params getProjects_params) {
		try {
			GetProjects_Result getProjects_result = null;
			if (Users.getUserByUserName(getProjects_params.getUserName(), getProjects_params.getPassword()) != null) {
				List<ProjectDTO> projectDTOList = Projects.getAvailableProjects();
				getProjects_result = new GetProjects_Result(projectDTOList);
			}
			if (getProjects_result != null) {
				return getProjects_result;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets a search results for Fields specified by their IDs
	 *
	 * @param search_params Search_Params
	 */
	public static Search_Result search(Search_Params search_params) {
		try {
			String isNumberRegex = "[0-9]+";
			UserDTO userDTO = Users.getUserByUserName(search_params.getUserName(), search_params.getPassword());
			Map<Integer, ArrayList<RecordDTO>> projectIdToRecordDTOsMap = new HashMap<Integer, ArrayList<RecordDTO>>();
			Search_Result search_result = new Search_Result();
			if (userDTO != null) {
				List<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
				Set<BatchDTO> batchDTOSet = new HashSet<BatchDTO>();
				for (String fieldId : search_params.getFiledIDs()) {
					if (fieldId.matches(isNumberRegex)) {
						FieldDTO fieldDTO = Fields.getFieldById(Integer.parseInt(fieldId));
						fieldDTOList.add(fieldDTO);
					}
				}
				for (FieldDTO fieldDTO : fieldDTOList) {
					batchDTOSet.addAll(Batches.getAllIndexedBatchesForProjectId(fieldDTO.getProjectId()));
				}
				for (BatchDTO batchDTO : batchDTOSet) {
					ArrayList<RecordDTO> recordDTOs = Records.getAllRecordsForBatch(batchDTO.getId());
					if (projectIdToRecordDTOsMap.get(batchDTO.getProjectId()) == null) {
						projectIdToRecordDTOsMap.put(batchDTO.getProjectId(), recordDTOs);
					} else {
						projectIdToRecordDTOsMap.get(batchDTO.getProjectId()).addAll(recordDTOs);
					}
				}
				for (FieldDTO fieldDTO : fieldDTOList) {
					List<RecordDTO> records = projectIdToRecordDTOsMap.get(fieldDTO.getProjectId());
					if (records == null) {
						return null;
					}

					for (RecordDTO recordDTO : records) {
						BatchDTO batchDTO = Batches.getBatchById(recordDTO.getBatchId());
						compareValueToQueries(search_result, recordDTO, batchDTO, fieldDTO, search_params.getSearchValues());
					}
				}
			}
			if (search_result.getSearchResults().isEmpty()) {
				return null;
			}
			return search_result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void compareValueToQueries(Search_Result s_r, RecordDTO recDTO, BatchDTO batchDTO, FieldDTO fieldDTO, List<String> searchValues) {
		if (recDTO.getPosition() == fieldDTO.getPosition()) {
			for (String searchValue : searchValues) {
				if (recDTO.getValue().equalsIgnoreCase(searchValue)) {
					s_r.addSearchResult(batchDTO.getId(), batchDTO.getFilePath(), recDTO.getRecNumber(), fieldDTO.getId());
				}
			}
		}
	}

	private static List<ProjectDTO> getAvailableProjects() throws Exception {
		List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();
		String getAvailableProjects = "SELECT * FROM project";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String title = rs.getString(2);
			int recordsPerImage = rs.getInt(3);
			int firstYCoord = rs.getInt(4);
			int recordHeight = rs.getInt(5);
			ProjectDTO projectDTO = new ProjectDTO(id, title, recordsPerImage, firstYCoord, recordHeight);
			projectList.add(projectDTO);
		}
		stmt.close();
		return projectList;
	}

	/**
	 * Adds a new Project to the database
	 *
	 * @param project ProjectDTO
	 * @throws Exception
	 */
	public void add(ProjectDTO project) throws Exception {
		String sql = "insert into project (title, records_per_image, first_y_coord, record_height) values (?, ?, ?, ?)";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, project.getTitle());
		stmt.setString(2, Integer.toString(project.getRecordsPerImage()));
		stmt.setString(3, Integer.toString(project.getFirstYCoord()));
		stmt.setString(4, Integer.toString(project.getRecordHeight()));
		if (stmt.executeUpdate() == 1) {
			project.setId(databaseAccessUtils.getSavedRowId(Database.connection));
		}
		stmt.close();
	}
}

