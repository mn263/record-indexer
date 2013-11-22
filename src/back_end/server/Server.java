package back_end.server;


import back_end.server.database.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.params.*;
import shared.communication.results.*;

import java.io.*;
import java.net.InetSocketAddress;

/**
 * Sets up a back_end.server on a specified TCP port number
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:20 AM
 */
public class Server {

	private static final int MAX_WAITING_CONNECTIONS = 10;
	private static int portNumber;
	private HttpHandler validateUserHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			ValidateUser_Params validateUser_params = (ValidateUser_Params) o;
			Database.startTransaction();
			ValidateUser_Result validateUser_result = Users.validateUser(validateUser_params);
			if (validateUser_result == null) {
				returnObject(exchange, xStream, new ValidateUser_Result(false, null, null, 0));
			} else {
				returnObject(exchange, xStream, validateUser_result);
			}
		}
	};
	private HttpHandler getProjectHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			GetProjects_Params getProjects_params = (GetProjects_Params) o;
			Database.startTransaction();
			GetProjects_Result getProjects_result = Projects.getProjects(getProjects_params);
			if (getProjects_result != null) {
				returnObject(exchange, xStream, getProjects_result);
			} else {
				returnFailed(exchange);
			}
		}
	};
	private HttpHandler getSampleImageHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			GetSampleImage_Params getSampleImage_params = (GetSampleImage_Params) o;
			Database.startTransaction();
			GetSampleImage_Result getSampleImage_result = Batches.getSampleImage(getSampleImage_params);
			if (getSampleImage_result == null) {
				returnFailed(exchange);
			} else {
				returnObject(exchange, xStream, getSampleImage_result);
			}
		}
	};
	private HttpHandler downloadBatchHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			DownloadBatch_Params downloadBatch_params = (DownloadBatch_Params) o;
			Database.startTransaction();
			DownloadBatch_Result downloadBatch_result = Batches.downloadBatch(downloadBatch_params);
			if (downloadBatch_result == null) {
				returnFailed(exchange);
			} else {
				returnObject(exchange, xStream, downloadBatch_result);
			}
		}
	};
	private HttpHandler submitBatchHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			SubmitBatch_Params submitBatch_params = (SubmitBatch_Params) o;
			Database.startTransaction();
			if (Batches.submitBatch(submitBatch_params)) {
				returnObject(exchange, xStream, "true");
			} else {
				returnFailed(exchange);
			}
		}
	};
	private HttpHandler getFieldsHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			GetFields_Params getFields_params = (GetFields_Params) o;
			Database.startTransaction();
			GetFields_Result getFields_result = Fields.getFields(getFields_params);
			if (getFields_result == null) {
				returnFailed(exchange);
			} else {
				returnObject(exchange, xStream, getFields_result);
			}
		}
	};
	private HttpHandler searchHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			XStream xStream = new XStream(new DomDriver());
			Object o = readRequest(exchange, xStream);
			Search_Params search_params = (Search_Params) o;

			Database.startTransaction();

			Search_Result search_result = Projects.search(search_params);
			if (search_result == null) {
				returnFailed(exchange);
			} else {
				returnObject(exchange, xStream, search_result);
			}
		}
	};
	private HttpHandler downloadFileHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {

			byte[] buffer = new byte[1024];
			String fileName = System.getProperty("user.dir");   //<---This gives me path for the current working directory.
			String classPath = exchange.getRequestURI().getPath();
			String absolutePath = fileName + "/Records" + classPath;
			try (InputStream fileToSend = new FileInputStream(absolutePath)) {
				int bytes;
				exchange.sendResponseHeaders(200, 0);
				while ((bytes = fileToSend.read(buffer)) >= 0) {
					exchange.getResponseBody().write(buffer, 0, bytes);
				}
				exchange.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private Server(int portNumber) {
		Server.portNumber = portNumber;
	}

	public static void main(String[] args) {

		if (args.length == 1) {
			portNumber = Integer.parseInt(args[0]);
		}
		if (portNumber > 0 && portNumber <= 65535) {
			Server server = new Server(portNumber);
			server.run();
		} else {
			Server server = new Server(39640);
			server.run();
		}
	}

	private void run() {

		HttpServer httpServer;
		try {
			httpServer = HttpServer.create(new InetSocketAddress(portNumber), MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		httpServer.setExecutor(null); // use the default executor
		httpServer.createContext("/validate_user", validateUserHandler);
		httpServer.createContext("/get_projects", getProjectHandler);
		httpServer.createContext("/get_sample_image", getSampleImageHandler);
		httpServer.createContext("/download_batch", downloadBatchHandler);
		httpServer.createContext("/submit_batch", submitBatchHandler);
		httpServer.createContext("/get_fields", getFieldsHandler);
		httpServer.createContext("/search", searchHandler);
		httpServer.createContext("/", downloadFileHandler);
		httpServer.start();
	}

	private Object readRequest(HttpExchange exchange, XStream xStream) throws IOException {
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line;
		String userInXML = "";
		while ((line = br.readLine()) != null) {
			userInXML += line;
		}
		return xStream.fromXML(userInXML);
	}

	private void returnObject(HttpExchange exchange, XStream xStream, Object o) throws IOException {
		exchange.sendResponseHeaders(200, 0);
		OutputStream outputStream = exchange.getResponseBody();
		xStream.toXML(o, outputStream);
		outputStream.close();
		exchange.close();
		Database.endTransaction(true);
	}

	public void returnFailed(HttpExchange exchange) throws IOException {
		exchange.sendResponseHeaders(400, 0);
		exchange.close();
		if (Database.connection != null) {
			Database.endTransaction(false);
		}
	}
}
