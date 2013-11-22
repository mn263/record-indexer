
package front_end.client.gui_searcher.controllers;

import front_end.client.ClientCommunicator;
import front_end.client.gui_searcher.views.might_not_use.BaseFrame;

public class BaseController {

	private BaseFrame frame;
	private ClientCommunicator clientClass;
	String isNumberRegex = "[0-9]+";


	public BaseController(BaseFrame frame) {
		this.frame = frame;
	}

	void setFrame(BaseFrame frame) {
		this.frame = frame;
	}

	BaseFrame getFrame() {
		return frame;
	}

	void setClientClass(ClientCommunicator clientClass) {
		this.clientClass = clientClass;
	}

	ClientCommunicator getClientClass() {
		return clientClass;
	}

	void initialize() {
		this.clientClass = new ClientCommunicator(frame);
	}
}

