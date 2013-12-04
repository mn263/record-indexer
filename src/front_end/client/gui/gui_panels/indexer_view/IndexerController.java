package front_end.client.gui.gui_panels.indexer_view;

import front_end.client.gui.ClientController;
import front_end.client.gui.batch_state.BatchStateListener;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:28 PM
 */
public class IndexerController {

	private ClientController clientController;
	private IndexerFrame indexerFrame;

	public IndexerController(ClientController clientController, ActionListener restartListener) {
		this.clientController = clientController;
		clientController.getBatchState().addBSListener(bsListener);
		openMainFrame(restartListener);
	}

	private void openMainFrame(ActionListener restartListener) {
//		TODO: button bar should not be selectable until they have a batch
		indexerFrame = new IndexerFrame(clientController, restartListener);
		indexerFrame.setVisible(true);
	}

	public void dispose() {
		indexerFrame.dispose();
	}

	private BatchStateListener bsListener = new BatchStateListener() {
		@Override
		public void ImageURLChanged() {
			indexerFrame.updateImage();
//			TODO: enable/disable button-bar
//			TODO: disable/enable download batch
		}

		@Override
		public void RecordSelectionChanged(Point selectedCell) {
			//TODO: change the image in the imagePanel
			//TODO: update the image in the bottom-left panel
			//TODO: update the image in the bottom-right panel
		}

		@Override
		public void ZoomedChanged() {
			indexerFrame.updateZoom();
			//TODO: update the image-navigation panel
			//TODO: update the image in the bottom-right panel
		}
	};
}
