package front_end.client.gui.gui_panels.download_batch;

import front_end.client.gui.controllers.BaseController;
import front_end.client.gui.controllers.ClientController;
import shared.communication.results.ValidateUser_Result;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 11:22 PM
 */
public class DownloadBatchController extends BaseController {

	private DownloadBatchDialog downloadBatchDialog;

	public DownloadBatchController(ClientController clientController) {
		super(clientController);
		openDownloadBatchDialog();
	}

	private void openDownloadBatchDialog() {
		downloadBatchDialog = new DownloadBatchDialog(clientController);
		downloadBatchDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		downloadBatchDialog.setVisible(true);
		downloadBatchDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public boolean isLoggedInHandler(ValidateUser_Result result) {
		if (!result.isUser()) {
			JOptionPane.showMessageDialog(downloadBatchDialog.getContentPane(), "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			String title = "Welcome to Indexer";
			String loginMessage = getLoginMessage(result);
			JOptionPane.showMessageDialog(downloadBatchDialog.getContentPane(), loginMessage, title, JOptionPane.INFORMATION_MESSAGE);

//			TODO: get batchState for the person and set it in the clientController
//			clientController.getBatchState().set...
			downloadBatchDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			downloadBatchDialog.dispose();
			return true;
		}
	}

	private String getLoginMessage(ValidateUser_Result loginResult) {
		String userName = loginResult.getUserNameFirst() + " " + loginResult.getUserNameLast() + ".\n";
		return "Welcome " + userName + "You have indexed " + loginResult.getRecordCount() + " records.";
	}


}
