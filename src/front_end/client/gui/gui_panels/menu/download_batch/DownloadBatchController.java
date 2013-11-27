package front_end.client.gui.gui_panels.menu.download_batch;

import front_end.client.gui.controllers.BaseController;
import front_end.client.gui.controllers.ClientController;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 11:22 PM
 */
public class DownloadBatchController extends BaseController {

	public DownloadBatchController(ClientController clientController) {
		super(clientController);
		openDownloadBatchDialog();
	}

	private void openDownloadBatchDialog() {
		DownloadBatchDialog downloadBatchDialog = new DownloadBatchDialog(clientController);
		downloadBatchDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		downloadBatchDialog.setVisible(true);
		downloadBatchDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
