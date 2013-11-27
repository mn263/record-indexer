package front_end.client.gui.gui_panels.indexer_view;

import front_end.client.gui.controllers.ClientController;

import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:28 PM
 */
public class IndexerController {

	private ClientController clientController;
	private IndexerFrame mainDisplayFrame;

	public IndexerController(ClientController clientController, ActionListener restartListener) {
		this.clientController = clientController;
		openMainFrame(restartListener);
	}

	private void openMainFrame(ActionListener restartListener) {
		mainDisplayFrame = new IndexerFrame(clientController, restartListener);
		mainDisplayFrame.setVisible(true);
	}

	public void dispose() {
		mainDisplayFrame.dispose();
	}
}
