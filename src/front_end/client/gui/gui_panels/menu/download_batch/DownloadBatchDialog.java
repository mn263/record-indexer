package front_end.client.gui.gui_panels.menu.download_batch;

import front_end.client.gui.controllers.ClientController;
import front_end.client.gui.gui_panels.menu.download_batch.view_sample.ViewSampleController;
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

	private static final int LOGIN_WIDTH = 350;
	private static final int LOGIN_HEIGHT = 100;
	private ClientController clientController;
	private GetProjects_Result projects;

	public DownloadBatchDialog(ClientController clientController) {
		this.clientController = clientController;

		getContentPane().setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
		setPreferredSize(new Dimension(LOGIN_WIDTH, LOGIN_HEIGHT));
		setLocation(clientController.getBatchState().getWindowPosition());

		//Get available projects for user
		projects = clientController.getProjects();
		//Create dialog
		setTitle("Download Batch");
		downloadBatchPanel = new DownloadBatchPanel(projects.getProjects());
		add(downloadBatchPanel);
		JButton viewSampleButton = downloadBatchPanel.getViewSampleButton();
		JButton cancelButton = downloadBatchPanel.getCancelButton();
		JButton downloadButton = downloadBatchPanel.getDownloadButton();
		//Setup button listeners
		viewSampleButton.addActionListener(viewSampleButtonLister);
		cancelButton.addActionListener(cancelButtonListener);
		downloadButton.addActionListener(downloadButtonLister);

		pack();
		setResizable(false);
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
			String imageUrl = "";
			String selectedProject;
			selectedProject = (String) downloadBatchPanel.getComboBox().getSelectedItem();
			for (GetProjects_Result.ProjectInfo project : projects.getProjects()) {
				if (selectedProject.equalsIgnoreCase(project.getProjectTitle())) {
					imageUrl = clientController.getSampleImage(project.getProjectId());
				}
			}
			new ViewSampleController(clientController, imageUrl, selectedProject);
		}
	};

	ActionListener downloadButtonLister = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
//				TODO: close the downloadBatchFrame
			dispose();
//				TODO: download the image
//				TODO: add the image to the background in the index frame
			String imageUrl = "";
			String selectedProject;
			selectedProject = (String) downloadBatchPanel.getComboBox().getSelectedItem();
			for (GetProjects_Result.ProjectInfo project : projects.getProjects()) {
				if (selectedProject.equalsIgnoreCase(project.getProjectTitle())) {
					imageUrl = clientController.getSampleImage(project.getProjectId());
				}
			}
			new ViewSampleController(clientController, imageUrl, selectedProject);
//				TODO: add the chosen project to the batchState
			clientController.getBatchState();
//				bsListener.ImageURLChanged(); <-- add image url
		}
	};
}