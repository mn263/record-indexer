
package front_end.client.gui.controllers;

public class BaseController {

	protected ClientController clientController;

	public BaseController(ClientController clientController) {
		this.clientController = clientController;
	}

	public ClientController getClientController() {
		return clientController;
	}
}

