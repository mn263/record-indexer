
package front_end.client.gui.base_classes;

import front_end.client.gui.ClientController;

public class BaseController {

	protected ClientController clientController;

	public BaseController(ClientController clientController) {
		this.clientController = clientController;
	}

	public ClientController getClientController() {
		return clientController;
	}
}

