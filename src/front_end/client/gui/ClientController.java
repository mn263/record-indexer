package front_end.client.gui;

import front_end.client.ClientCommunicator;
import front_end.client.gui.batch_state.BatchState;
import front_end.client.gui.batch_state.BatchStateReader;
import front_end.client.gui.batch_state.BatchStateWriter;
import shared.communication.params.*;
import shared.communication.results.DownloadBatch_Result;
import shared.communication.results.GetProjects_Result;
import shared.communication.results.GetSampleImage_Result;
import shared.communication.results.ValidateUser_Result;

import java.util.ArrayList;
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
		BatchState readBS = batchStateReader.readBatchState();
		if (readBS != null) {
			batchState = readBS;
		}
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

	public String getSampleImage(int projectId) {
		String userName = getBatchState().getUserName();
		String password = getBatchState().getPassword();
		GetSampleImage_Result result = clientCommunicator.getSampleImage(new GetSampleImage_Params(userName, password, projectId));
		return getHostAndPort() + result.getImageURL();
	}


	public DownloadBatch_Result downloadBatch(String projectId) {
		if (projectId.matches(isNumberRegex)) {
			String userName = getBatchState().getUserName();
			String password = getBatchState().getPassword();
			DownloadBatch_Result result = clientCommunicator.downloadBatch(new DownloadBatch_Params(userName, password, Integer.parseInt(projectId)));
			result.setImageURL(getHostAndPort() + result.getImageURL());
			return result;
		} else {
			return null;
		}
	}

	public boolean submitBatch() {
		String userName = getBatchState().getUserName();
		String password = getBatchState().getPassword();
		String batchId = Integer.toString(getBatchState().getDownloadBatchResult().getBatchId());
		String[][] fieldValuesList = getBatchState().getRecordValues();

		if (batchId.matches(isNumberRegex)) {
			SubmitBatch_Params submitBatch_params = new SubmitBatch_Params(userName, password, Integer.parseInt(batchId));
			for (int i = 0; i < fieldValuesList[0].length; i++) {
				List<String> recordValues = new ArrayList<>();
				for (String[] batchRow : fieldValuesList) {
					recordValues.add(batchRow[i]);
				}
				submitBatch_params.addRecordToField(recordValues);
			}
			String result = clientCommunicator.submitBatch(submitBatch_params);
			if (result != null && result.equalsIgnoreCase("true")) {
				return true;
			}
		}
		return false;
	}

	public String getHostAndPort() {
		return "http://" + clientCommunicator.getHost() + ":" + clientCommunicator.getPort() + "/";
	}
}

