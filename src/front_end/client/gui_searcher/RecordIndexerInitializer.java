package front_end.client.gui_searcher;

import front_end.client.gui_searcher.controllers.LoginController;
import front_end.client.gui_searcher.views.gui_panels.login.LoginFrame;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 10:35 PM
 */
public class RecordIndexerInitializer {

	private String host;
	private String port;

	public RecordIndexerInitializer(String host, String port) {
		this.host = host;
		this.port = port;
	}

	public void startIndexer() {
		login();
	}

	private void login() {
		LoginFrame frame = new LoginFrame(host, port);
		LoginController loginController = new LoginController(frame);
		frame.setController(loginController);
		frame.setVisible(true);
	}
}
