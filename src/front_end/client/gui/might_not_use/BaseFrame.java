package front_end.client.gui.might_not_use;

import front_end.client.gui.controllers.ClientController;

import javax.swing.*;

public class BaseFrame extends JFrame {

	public static final int LOGIN_WIDTH = 500;
	public static final int LOGIN_HEIGHT = 145;

	private ClientController clientController;

	public BaseFrame(ClientController clientController) {
		super();
		this.clientController = clientController;
	}

	public ClientController getClientController() {
		return clientController;
	}
}

