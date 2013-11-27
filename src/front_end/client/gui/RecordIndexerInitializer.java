package front_end.client.gui;

import front_end.client.gui.controllers.ClientController;
import front_end.client.gui.gui_panels.indexer_view.IndexerController;
import front_end.client.gui.gui_panels.login_view.LoginController;
import front_end.client.gui.gui_panels.login_view.LoginListener;
import shared.communication.results.ValidateUser_Result;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 10:35 PM
 */
public class RecordIndexerInitializer {

	private ClientController clientController;
	private LoginController loginController;
	private IndexerController indexerController;

	public RecordIndexerInitializer(ClientController clientController) {
		this.clientController = clientController;
	}

	public void startIndexer() {
		loginController = new LoginController(clientController, loginListener);
	}

	private void openRecordIndexerProgram() {
		indexerController = new IndexerController(clientController, restartListener);
	}

	private LoginListener loginListener = new LoginListener() {
		@Override
		public void loggedIn(ValidateUser_Result loginResult) {
			boolean isLoggedIn = loginController.isLoggedInHandler(loginResult);
			if (isLoggedIn) {
				clientController.readBatchState();
				openRecordIndexerProgram();
			}
		}
	};

	ActionListener restartListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//				TODO: on logout do the following
			clientController.saveBatchState();
			indexerController.dispose();
			startIndexer();
		}
	};
}
