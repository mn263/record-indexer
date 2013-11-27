package front_end.client.gui;

import front_end.client.gui.controllers.ClientController;

import javax.swing.*;

@SuppressWarnings("serial")
public class BasePanel extends JPanel {

	private ClientController clientController;

	public BasePanel(ClientController clientController) {
		super();
		this.clientController = clientController;
	}

	public ClientController getClientController() {
		return clientController;
	}
}

