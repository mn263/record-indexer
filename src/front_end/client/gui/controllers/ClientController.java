package front_end.client.gui.controllers;

import front_end.client.ClientCommunicator;
import front_end.client.gui.batch_state.BatchState;
import front_end.client.gui.batch_state.BatchStateReader;
import front_end.client.gui.batch_state.BatchStateWriter;
import shared.communication.params.*;
import shared.communication.results.*;

import java.util.Arrays;
import java.util.List;

public class ClientController {

	private BatchState batchState;
	private ClientCommunicator clientCommunicator;

	private String isNumberRegex = "[0-9]+";

	public ClientController(String host, String port) {
		clientCommunicator = new ClientCommunicator(host, port);
		batchState = new BatchState();
	}

	public void saveBatchState() {
		BatchStateWriter batchStateWriter = new BatchStateWriter(batchState);
		batchStateWriter.writeBatchState();
	}

	public void readBatchState() {
		BatchStateReader batchStateReader = new BatchStateReader(batchState);
		batchStateReader.readBatchState();
	}

	public ValidateUser_Result validateUser(String userName, String password) {
		ValidateUser_Result result = clientCommunicator.validateUser(new ValidateUser_Params(userName, password));
		if (result.isUser()) {
			getBatchState().setUserName(userName);
			getBatchState().setPassword(password);
		}
		return result;
	}

	public GetProjects_Result getProjects() {
		String userName = getBatchState().getUserName();
		String password = getBatchState().getPassword();
		return clientCommunicator.getProjects(new GetProjects_Params(userName, password));
	}

	public BatchState getBatchState() {
		return batchState;
	}

	public void search(List<String> fieldIDs, List<String> searchValues) {
		Search_Params search_params = new Search_Params(batchState.getUserName(), batchState.getPassword(),
				fieldIDs, searchValues);
		clientCommunicator.search(search_params);
	}

	public void showImage(String selectedImage) {
//		String url = selectedImage.substring(0, selectedImage.length() - 1);
//		try {
//			getGuiSearchFrame().showImage(new URL(url));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
	}

	public String getSampleImage(int projectId) {
		String userName = getBatchState().getUserName();
		String password = getBatchState().getPassword();
		GetSampleImage_Result result = clientCommunicator.getSampleImage(new GetSampleImage_Params(userName, password, projectId));
		return getHostAndPort() + result.getImageURL();
	}


	private DownloadBatch_Result downloadBatch(String userName, String password, String projectId) {
		if (projectId.matches(isNumberRegex)) {
			DownloadBatch_Result result = clientCommunicator.downloadBatch(new DownloadBatch_Params(userName, password, Integer.parseInt(projectId)));
			result.setImageURL(getHostAndPort() + result.getImageURL());
			return result;
		} else {
			return null;
		}
	}

	private boolean submitBatch(String userName, String password, String batchId, String fieldValuesList) {
		if (batchId.matches(isNumberRegex)) {
			SubmitBatch_Params submitBatch_params = new SubmitBatch_Params(userName, password, Integer.parseInt(batchId));
			List<String> fieldValues = Arrays.asList(fieldValuesList.split(";"));
			for (String recordString : fieldValues) {
				List<String> recordValues = Arrays.asList(recordString.split(","));
				submitBatch_params.addRecordToField(recordValues);
			}
			String result = clientCommunicator.submitBatch(submitBatch_params);
			if (result != null && result.equalsIgnoreCase("true")) {
				return true;
			}
		}
		return false;
	}

	private GetFields_Result getFields(String userName, String password, String projectId) {
		if (projectId.matches(isNumberRegex) || projectId.isEmpty()) {
			GetFields_Params getFields_params;
			if (projectId.matches(isNumberRegex)) {
				getFields_params = new GetFields_Params(userName, password, Integer.parseInt(projectId));
			} else {
				getFields_params = new GetFields_Params(userName, password);
			}
			return clientCommunicator.getFields(getFields_params);
		}
		return null;
	}

//	private void search(String userName, String password, String fields, String searches) {
//		_frame.clearImagesPanel();
//		List<String> fieldIds = Arrays.asList(fields.split(","));
//		List<String> searchValues = Arrays.asList(searches.split(","));
//		Search_Result result = this.clientClass.search(new Search_Params(userName, password, fieldIds, searchValues));
//		String hostAndPort = "http://" + getGuiSearchFrame().getHost() + ":" + getGuiSearchFrame().getPort() + "/";
//		if (result != null) {
////            String output = "";
//			List<Search_Result.SearchResult> searchResults = result.getSearchResults();
//
//			ArrayList<String> results = new ArrayList<>();
//			for (Search_Result.SearchResult sr : searchResults) {
//				results.add(hostAndPort + sr.getImageURL() + "\n");
////                output += sr.getBatchId() + "\n" + hostAndPort + sr.getImageURL() + "\n" + sr.getRecordCount() + "\n" + sr.getFieldId() + "\n";
//			}
//			_frame.setImagesPanel(results);
//		} else {
//
//			ArrayList<String> failed = new ArrayList<>();
//			failed.add("FAILED\n");
//			_frame.setImagesPanel(failed);
//		}
//	}

	public String getHostAndPort() {
		return "http://" + clientCommunicator.getHost() + ":" + clientCommunicator.getPort() + "/";
	}
}

