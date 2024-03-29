package front_end.client.gui.gui_panels.menu.download_batch;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BaseController;

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
		if (clientController.getBatchState().hasDownloadedBatch()) {
			downloadBatchDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			downloadBatchDialog.setVisible(false);
			downloadBatchDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			JOptionPane.showMessageDialog(downloadBatchDialog.getContentPane(), "Only 1 batch at a time is allowed", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			downloadBatchDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			downloadBatchDialog.setVisible(true);
			downloadBatchDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}
