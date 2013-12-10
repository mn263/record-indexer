package front_end.client.gui.gui_panels.indexer_view;

import front_end.client.gui.ClientController;
import front_end.client.gui.batch_state.BatchStateListener;

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
		BatchStateListener bsListener = new BatchStateListener() {
			@Override
			public void BatchDownloaded() {
				indexerFrame.updateForNewBatchStatus();
				indexerFrame.getToolBarPanel().updateToolBar();
			}

			@Override
			public void RecordSelectionChanged(int row, int column) {
				indexerFrame.updateForRecordSelectionChange(row, column);
			}

			@Override
			public void ZoomedChanged() {
				indexerFrame.updateZoom();
			}

			@Override
			public void highlightToggled() {
				indexerFrame.highlightToggled();
			}

			@Override
			public void invertImageToggled() {
				indexerFrame.invertImageToggled();
			}

			@Override
			public void RecordValuesChanged() {
				indexerFrame.updateRecordValues();
			}
		};
		clientController.getBatchState().addBSListener(bsListener);
		openMainFrame(restartListener);
	}

	private void openMainFrame(ActionListener restartListener) {
		indexerFrame = new IndexerFrame(clientController, restartListener);
		indexerFrame.setVisible(true);
	}

	public void dispose() {
		indexerFrame.dispose();
	}

}
