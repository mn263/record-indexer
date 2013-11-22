package front_end.client.gui_searcher.controllers;

import front_end.client.ClientCommunicator;
import front_end.client.gui_searcher.views.might_not_use.BaseFrame;
import front_end.client.gui_searcher.views.might_not_use.GuiSearchFrame;
import junit.framework.Assert;
import shared.communication.params.*;
import shared.communication.results.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OldController extends BaseController {

	private GuiSearchFrame _frame;
	private ClientCommunicator clientClass;
	private String isNumberRegex = "[0-9]+";

	public OldController() {
		super(new GuiSearchFrame());
	}

	public void setBaseFrame(BaseFrame frame) {
		_frame = (GuiSearchFrame) frame;
	}

	public GuiSearchFrame getGuiSearchFrame() {
		_frame = (GuiSearchFrame) getFrame();
		return _frame;
	}

	// BaseController methods
	//

	@Override
	public void initialize() {
		operationSelected();
		this.clientClass = new ClientCommunicator(getGuiSearchFrame());
	}

	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<>();
		paramNames.add("Field ID(s)");
		paramNames.add("Search Values");

		switch (getGuiSearchFrame().getOperation()) {
			case VALIDATE_USER:
				break;
			case GET_PROJECTS:
				break;
			case GET_SAMPLE_IMAGE:
				paramNames.add("Project");
				break;
			case DOWNLOAD_BATCH:
				paramNames.add("Project");
				break;
			case GET_FIELDS:
				paramNames.add("Project");
				break;
			case SUBMIT_BATCH:
				paramNames.add("Batch");
				paramNames.add("Record Values");
				break;
			case SEARCH:
				paramNames.add("Fields");
				paramNames.add("Search Values");
				break;
			default:
				assert false;
				break;
		}

		getGuiSearchFrame().setFieldsPanel("");
		getGuiSearchFrame().setProjectsPanel("");
		getGuiSearchFrame().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

//	public void login() {
//		String[] paramValues = getFrame().getLoginValues();
//		validateUser(paramValues[0], paramValues[1]);
//		getProjects(paramValues[0], paramValues[1]);
//
////		String projects = getFrame().getProjectsPanel();
////		String[] projectList = projects.split("\n");
////		for (String project : projectList) {
////			if (project.matches(isNumberRegex) && !project.isEmpty()) {
//				getFields(paramValues[0], paramValues[1], "");
////			}
////		}
//	}

	public void search() {
//		String[] loginValues = getFrame().getLoginValues();
		String[] paramValues = getGuiSearchFrame().getParameterValues();
//		search(loginValues[0], loginValues[1], paramValues[0], paramValues[1]);
	}

	public void showImage(String selectedImage) {
		String url = selectedImage.substring(0, selectedImage.length() - 1);
		try {
			getGuiSearchFrame().showImage(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void executeOperation() {
		String[] paramValues = getGuiSearchFrame().getParameterValues();
		switch (getGuiSearchFrame().getOperation()) {
			case VALIDATE_USER:
				validateUser(paramValues[0], paramValues[1]);
				break;
			case GET_PROJECTS:
				getProjects(paramValues[0], paramValues[1]);
				break;
			case GET_SAMPLE_IMAGE:
				getSampleImage(paramValues[0], paramValues[1], paramValues[2]);
				break;
			case DOWNLOAD_BATCH:
				downloadBatch(paramValues[0], paramValues[1], paramValues[2]);
				break;
			case SUBMIT_BATCH:
				submitBatch(paramValues[0], paramValues[1], paramValues[2], paramValues[3]);
				break;
			case GET_FIELDS:
				getFields(paramValues[0], paramValues[1], paramValues[2]);
				break;
			case SEARCH:
				search(paramValues[0], paramValues[1], paramValues[2], paramValues[3]);
				break;
			default:
				assert false;
				break;
		}
	}

	private void validateUser(String userName, String password) {
		ValidateUser_Result result = this.clientClass.validateUser(new ValidateUser_Params(userName, password));
//        if (result == null) {
//        	_frame.setResponse("FAILED\n");
//        }else if (result.isUser()) {
//            _frame.setResponse("TRUE\n" + result.getUserNameFirst() + "\n" + result.getUserNameLast() + "\n" + result.getRecordCount() + "\n");
//        } else {
//            _frame.setResponse("FALSE\n");
//        }
	}

	private void getProjects(String userName, String password) {
		GetProjects_Result result = this.clientClass.getProjects(new GetProjects_Params(userName, password));
		if (result != null) {
			List<GetProjects_Result.ProjectInfo> projectInfos = result.getProjects();
			String output = "";
			for (GetProjects_Result.ProjectInfo projectInfo : projectInfos) {
//                output += projectInfo.getProjectId() + "\n";
				output += projectInfo.getProjectTitle() + "\n";
			}
			_frame.setProjectsPanel(output);
		} else {
			_frame.setProjectsPanel("FAILED\n");
		}
	}

	private void getSampleImage(String userName, String password, String projectId) {
		if (projectId.matches(isNumberRegex)) {
			GetSampleImage_Result result = this.clientClass.getSampleImage(new GetSampleImage_Params(userName, password, Integer.parseInt(projectId)));
			if (result != null) {
				String hostAndPort = "http://" + getGuiSearchFrame().getHost() + ":" + getGuiSearchFrame().getPort() + "/";
//            	_frame.setResponse(hostAndPort + result.getImageURL() + "\n");
//                return;
			}
		}
//        _frame.setResponse("FAILED\n");
	}

	private void downloadBatch(String userName, String password, String projectId) {
		if (projectId.matches(isNumberRegex)) {
			DownloadBatch_Result result = this.clientClass.downloadBatch(new DownloadBatch_Params(userName, password, Integer.parseInt(projectId)));
			String hostAndPort = "http://" + getGuiSearchFrame().getHost() + ":" + getGuiSearchFrame().getPort() + "/";
			if (result != null) {
				String output = result.getBatchId() + "\n" + result.getProjectId() + "\n" + hostAndPort + result.getImageURL() + "\n" +
						result.getFirstYCoord() + "\n" + result.getRecordHeight() + "\n" + result.getRecordCount() + "\n";

				List<DownloadBatch_Result.FieldInformation> fieldInformationList = result.getFieldInformationList();
				Assert.assertEquals(result.getFieldCount(), fieldInformationList.size());
				output += fieldInformationList.size() + "\n";
				for (DownloadBatch_Result.FieldInformation fInfo : fieldInformationList) {
					output += fInfo.getFieldId() + "\n" + fInfo.getFieldNumber() + "\n" + fInfo.getFieldTitle() + "\n" +
							hostAndPort + fInfo.getHelpURL() + "\n" + fInfo.setXCoord() + "\n" + fInfo.getPixelWidth() + "\n";
					if (fInfo.getKnownDataPath() != null) {
						output += hostAndPort + fInfo.getKnownDataPath() + "\n";
					}
				}
//                _frame.setResponse(output);
//                return;
			}
		}
//        _frame.setResponse("FAILED\n");
	}

	private void submitBatch(String userName, String password, String batchId, String fieldValuesList) {
		if (batchId.matches(isNumberRegex)) {
			SubmitBatch_Params submitBatch_params = new SubmitBatch_Params(userName, password, Integer.parseInt(batchId));
			List<String> fieldValues = Arrays.asList(fieldValuesList.split(";"));
			for (String recordString : fieldValues) {
				List<String> recordValues = Arrays.asList(recordString.split(","));
				submitBatch_params.addRecordToField(recordValues);
			}
//            String result = this.clientClass.submitBatch(submitBatch_params);
//            if (result != null && result.equalsIgnoreCase("true")) {
//                _frame.setResponse("TRUE\n");
//                return;
//            } else if (result != null) {
//                _frame.setResponse("FALSE\n");
//                return;
//            }
		}
//        _frame.setResponse("FAILED\n");
	}

	private void getFields(String userName, String password, String projectId) {
		if (projectId.matches(isNumberRegex) || projectId.isEmpty()) {
			GetFields_Params getFields_params;
			if (projectId.matches(isNumberRegex)) {
				getFields_params = new GetFields_Params(userName, password, Integer.parseInt(projectId));
			} else {
				getFields_params = new GetFields_Params(userName, password);
			}
			GetFields_Result result = this.clientClass.getFields(getFields_params);
			if (result != null) {
				List<GetFields_Result.FieldInfo> fieldInfoList = result.getFields();
				String output = "";
				for (GetFields_Result.FieldInfo fieldInfo : fieldInfoList) {
//                    output += fieldInfo.getProjectId() + "\n" + fieldInfo.getFieldId() + "\n" + fieldInfo.getFieldTitle() + "\n";
					output += fieldInfo.getFieldId() + " -- " + fieldInfo.getFieldTitle() + "\n";
				}
				_frame.setFieldsPanel(output);
				return;
			}
		}
		_frame.setFieldsPanel("FAILED\n");
	}

	private void search(String userName, String password, String fields, String searches) {
		_frame.clearImagesPanel();
		List<String> fieldIds = Arrays.asList(fields.split(","));
		List<String> searchValues = Arrays.asList(searches.split(","));
		Search_Result result = this.clientClass.search(new Search_Params(userName, password, fieldIds, searchValues));
		String hostAndPort = "http://" + getGuiSearchFrame().getHost() + ":" + getGuiSearchFrame().getPort() + "/";
		if (result != null) {
//            String output = "";
			List<Search_Result.SearchResult> searchResults = result.getSearchResults();

			ArrayList<String> results = new ArrayList<>();
			for (Search_Result.SearchResult sr : searchResults) {
				results.add(hostAndPort + sr.getImageURL() + "\n");
//                output += sr.getBatchId() + "\n" + hostAndPort + sr.getImageURL() + "\n" + sr.getRecordCount() + "\n" + sr.getFieldId() + "\n";
			}
			_frame.setImagesPanel(results);
		} else {

			ArrayList<String> failed = new ArrayList<>();
			failed.add("FAILED\n");
			_frame.setImagesPanel(failed);
		}
	}
}

