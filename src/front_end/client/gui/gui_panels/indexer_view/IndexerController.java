package front_end.client.gui.gui_panels.indexer_view;

import front_end.client.gui.BatchState;
import front_end.client.gui.controllers.ClientController;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:28 PM
 */
public class IndexerController {

	private ClientController clientController;

	public IndexerController(ClientController clientController) {
		this.clientController = clientController;
		BatchState batchState = clientController.getBatchState();
		openMainFrame();
	}

	private void openMainFrame() {
		IndexerFrame mainDisplayFrame = new IndexerFrame(clientController);
		mainDisplayFrame.setVisible(true);
	}

//	TODO: Keep the listeners here so that they can interact with each other
//	public boolean isLoggedInHandler(ValidateUser_Result loginResult) {
//	}
}
