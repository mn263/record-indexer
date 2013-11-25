package front_end.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.params.*;
import shared.communication.results.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:35 AM
 */

public class ClientCommunicator {

	private String host;
	private String port;

	public ClientCommunicator(String host, String port) {
		this.host = host;
		this.port = port;
	}

	private String getUrlPrefix() {
		String serverHost = host;
		String serverPort = port;
		return "http://" + serverHost + ":" + serverPort;

	}

	/**
	 * validates a specific user
	 *
	 * @param userParams ValidateUser_Params
	 * @return ValidateUser_Result
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params userParams) {
		return (ValidateUser_Result) doPost(getUrlPrefix() + "/validate_user", userParams);
	}

	/**
	 * gets all appropriate projects
	 *
	 * @param projectParams GetProjects_Params
	 * @return GetProjects_Result
	 */
	public GetProjects_Result getProjects(GetProjects_Params projectParams) {
		return (GetProjects_Result) doPost(getUrlPrefix() + "/get_projects", projectParams);
	}

	/**
	 * gets a sample image
	 *
	 * @param params GetSampleImage_Params
	 * @return GetSampleImage_Result
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) {
		return (GetSampleImage_Result) doPost(getUrlPrefix() + "/get_sample_image", params);
	}

	/**
	 * downloads appropriate batch
	 *
	 * @param params DownloadBatch_Params
	 * @return DownloadBatch_Result
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) {
		return (DownloadBatch_Result) doPost(getUrlPrefix() + "/download_batch", params);
	}

	/**
	 * submits a batch
	 *
	 * @param params SubmitBatch_Params
	 * @return boolean
	 */
	public String submitBatch(SubmitBatch_Params params) {
		return (String) doPost(getUrlPrefix() + "/submit_batch", params);
	}

	/**
	 * gets corresponding fields
	 *
	 * @param params GetFields_Params
	 * @return GetFields_Result
	 */
	public GetFields_Result getFields(GetFields_Params params) {
		return (GetFields_Result) doPost(getUrlPrefix() + "/get_fields", params);
	}

	/**
	 * performs search
	 *
	 * @param params Search_Params
	 * @return Search_Result
	 */
	public Search_Result search(Search_Params params) {
		return (Search_Result) doPost(getUrlPrefix() + "/search", params);
	}

	//Send data to back_end.server for processing
	private Object doPost(String urlPath, Object postData) {
		Object returnObject;
		XStream xStream = new XStream(new DomDriver());
		try {
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			OutputStream requestBody = connection.getOutputStream();
			xStream.toXML(postData, requestBody);
			requestBody.close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream responseBody = connection.getInputStream();
				returnObject = xStream.fromXML(responseBody);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return returnObject;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}
}

