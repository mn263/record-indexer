package shared.communication.results;

import shared.model.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Gets all projects that are available for a given User
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class GetProjects_Result {

	private List<ProjectInfo> projects = new ArrayList<ProjectInfo>();

	/**
	 * returns a list of the found projects
	 *
	 * @param projectDTOs List<ProjectDTO>
	 */
	public GetProjects_Result(List<ProjectDTO> projectDTOs) {
		for (ProjectDTO projectDTO : projectDTOs) {
			ProjectInfo projectInfo = new ProjectInfo(projectDTO.getId(), projectDTO.getTitle());
			projects.add(projectInfo);
		}
	}

	public List<ProjectInfo> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectInfo> projects) {
		this.projects = projects;
	}

	public class ProjectInfo {

		private int projectId;
		private String projectTitle;

		public ProjectInfo(int projectId, String projectTitle) {
			this.projectId = projectId;
			this.projectTitle = projectTitle;
		}

		public int getProjectId() {
			return projectId;
		}

		public void setProjectId(int projectId) {
			this.projectId = projectId;
		}

		public String getProjectTitle() {
			return projectTitle;
		}
	}
}
