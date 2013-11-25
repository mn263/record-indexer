package front_end.client.gui.gui_panels.download_batch;

import front_end.client.gui.BatchStateListener;
import front_end.client.gui.controllers.ClientController;
import shared.communication.results.GetProjects_Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:24 PM
 */
public class DownloadBatchDialog extends JDialog {

	private DownloadBatchPanel downloadBatchPanel;
	private JButton cancelButton;

	public static final int LOGIN_WIDTH = 500;
	public static final int LOGIN_HEIGHT = 145;
	private ClientController clientController;

	public DownloadBatchDialog(ClientController clientController) {
		this.clientController = clientController;
		clientController.getBatchState().addBSListener(this.bsListener);

		getContentPane().setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
		setPreferredSize(new Dimension(LOGIN_WIDTH, LOGIN_HEIGHT));

		setTitle("Download Batch");

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//		TODO: get available projects for the person
		GetProjects_Result projects = clientController.getProjects();

		downloadBatchPanel = new DownloadBatchPanel(projects.getProjects());
		add(downloadBatchPanel);

		JButton viewSampleButton = downloadBatchPanel.getViewSampleButton();
		cancelButton = downloadBatchPanel.getCancelButton();
		JButton downloadButton = downloadBatchPanel.getDownloadButton();

		viewSampleButton.addActionListener(viewSampleButtonLister);
		cancelButton.addActionListener(cancelButtonListener);
		downloadButton.addActionListener(downloadButtonLister);

		pack();
		setMinimumSize(getPreferredSize());
	}

	ActionListener cancelButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	ActionListener viewSampleButtonLister = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//				TODO: show sample in new window

		}
	};

	ActionListener downloadButtonLister = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
//				TODO: download the image
//				TODO: close the downloadBatchFrame
//				TODO: add the image to the background in the index frame
//				TODO: add the chosen project to the batchState
//				bsListener.ImageURLChanged(); <-- add image url
		}
	};

	private BatchStateListener bsListener = new BatchStateListener() {
		@Override
		public void ImageURLChanged(String newURL) {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void RecordValueChanged(String recordValue) {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void WindowPositionChanged(Point newPosition) {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void WindowDimensionsChanged(Point newDimensions) {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void DividerPositionsChanged(Point newPositions) {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void ZoomedIn() {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void ZoomedOut() {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void ScrollPositionChanged(double newPosition) {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void HighlightIsToggled() {
			//To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void InvertIsToggled() {
			//To change body of implemented methods use File | Settings | File Templates.
		}
	};
}
