package back_end.server.database;

import shared.communication.params.GetFields_Params;
import shared.communication.results.GetFields_Result;
import shared.model.FieldDTO;
import shared.model.UserDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for Fields
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:01 AM
 */
public class Fields {
	DatabaseAccessUtils databaseAccessUtils = new DatabaseAccessUtils();

	/**
	 * Gets all of the Fields for a specific Project
	 *
	 * @param fieldId int FieldID
	 * @return List of corresponding Fields
	 * @throws Exception
	 */
	public static FieldDTO getFieldById(int fieldId) throws Exception {
		FieldDTO fieldDTO = null;
		String getAvailableProjects = "SELECT * FROM field where id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(fieldId));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int project_id = rs.getInt(2);
			String title = rs.getString(3);
			int xCoord = rs.getInt(4);
			int width = rs.getInt(5);
			String helpHTML = rs.getString(6);
			String knownData = rs.getString(7);
			int position = rs.getInt(8);
			fieldDTO = new FieldDTO(id, project_id, title, xCoord, width, helpHTML, knownData, position);
		}
		stmt.close();
		return fieldDTO;
	}

	/**
	 * Gets all of the Fields for a specific Project
	 *
	 * @param projectId int ProjectID
	 * @return List of corresponding Fields
	 * @throws Exception
	 */
	public static List<FieldDTO> getAllFieldsForProject(int projectId) throws Exception {
		List<FieldDTO> fieldList = new ArrayList<FieldDTO>();
		String getAvailableProjects = "SELECT * FROM field where project_id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(projectId));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int project_id = rs.getInt(2);
			String title = rs.getString(3);
			int xCoord = rs.getInt(4);
			int width = rs.getInt(5);
			String helpHTML = rs.getString(6);
			String knownData = rs.getString(7);
			int position = rs.getInt(8);
			FieldDTO fieldDTO = new FieldDTO(id, project_id, title, xCoord, width, helpHTML, knownData, position);
			fieldList.add(fieldDTO);
		}
		stmt.close();
		return fieldList;
	}

	/**
	 * Gets All of the fields for the specified project
	 *
	 * @param getFields_params GetFields_Params
	 */
	public static GetFields_Result getFields(GetFields_Params getFields_params) {
		try {
			GetFields_Result getFields_result = null;
			UserDTO userDTO = Users.getUserByUserName(getFields_params.getUserName(), getFields_params.getPassword());
			if (userDTO != null) {
				List<FieldDTO> fieldDTOList;
				if (getFields_params.getProjectId() == -1) {
					fieldDTOList = Fields.getAllFields();
				} else {
					fieldDTOList = Fields.getAllFieldsForProject(getFields_params.getProjectId());
				}
				getFields_result = new GetFields_Result();
				for (FieldDTO fieldDTO : fieldDTOList) {
					getFields_result.addFieldInfo(fieldDTO.getProjectId(), fieldDTO.getId(), fieldDTO.getTitle());
				}
			}
			if ((getFields_result == null || Projects.getProjectById(getFields_params.getProjectId()) == null) &&
					getFields_params.getProjectId() != -1) {
				return null;
			} else {
				return getFields_result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<FieldDTO> getAllFields() throws Exception {
		List<FieldDTO> fieldList = new ArrayList<FieldDTO>();
		String getAvailableProjects = "SELECT * FROM field";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int project_id = rs.getInt(2);
			String title = rs.getString(3);
			int xCoord = rs.getInt(4);
			int width = rs.getInt(5);
			String helpHTML = rs.getString(6);
			String knownData = rs.getString(7);
			int position = rs.getInt(8);
			FieldDTO fieldDTO = new FieldDTO(id, project_id, title, xCoord, width, helpHTML, knownData, position);
			fieldList.add(fieldDTO);
		}
		stmt.close();
		return fieldList;
	}

	/**
	 * Adds a new Field to the database
	 *
	 * @param field FieldDTO
	 * @throws Exception
	 */
	public void add(FieldDTO field) throws Exception {
		String sql = "insert into field (project_id, title, x_coord, width, help_html, known_data, position) values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = Database.connection.prepareStatement(sql);
		stmt.setString(1, Integer.toString(field.getProjectId()));
		stmt.setString(2, field.getTitle());
		stmt.setString(3, Integer.toString(field.getXCoord()));
		stmt.setString(4, Integer.toString(field.getWidth()));
		stmt.setString(5, field.getHelpFilePath());
		stmt.setString(6, field.getKnownDataPath());
		stmt.setString(7, Integer.toString(field.getPosition()));
		if (stmt.executeUpdate() == 1) {
			field.setId(databaseAccessUtils.getSavedRowId(Database.connection));
		} else {
			System.out.println("Error saving Field to database");
			throw new Exception();
		}
		stmt.close();
	}


	/**
	 * Gets the number of projects in the database
	 *
	 * @param projectId int
	 * @throws Exception
	 */
	public static int getFieldCountByProject(int projectId) throws Exception {
		String getAvailableProjects = "SELECT COUNT(*) FROM field WHERE project_id = ?";
		PreparedStatement stmt = Database.connection.prepareStatement(getAvailableProjects);
		stmt.setString(1, Integer.toString(projectId));
		ResultSet rs = stmt.executeQuery();
		int count = rs.getInt(1);
		stmt.close();
		return count;
	}
}