package front_end.client.gui.gui_panels.menu.download_batch.view_sample;

import front_end.client.gui.controllers.BaseController;
import front_end.client.gui.controllers.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matt
 * Date: 11/25/13
 * Time: 4:09 PM
 */
public class ViewSampleController extends BaseController {

	private JDialog sampleImageDialog;

	public ViewSampleController(ClientController clientController, String imageURL, String projectTitle) {
		super(clientController);
		openSampleImage(imageURL, projectTitle);
	}

	private void openSampleImage(String imageURL, String projectTitle) {
		sampleImageDialog = new JDialog();

		ViewSamplePanel sampleImagePanel = new ViewSamplePanel(clientController, imageURL);
		JPanel closeButtonPanel = new JPanel();
		JButton closeButton = new JButton("Close");
		closeButtonPanel.add(closeButton);
		closeButton.addActionListener(closeButtonListener);

		//Give dialog its settings
		sampleImageDialog.setTitle("Sample Image from " + projectTitle);
		sampleImageDialog.setLayout(new BorderLayout());
		sampleImageDialog.add(sampleImagePanel, BorderLayout.CENTER);
		sampleImageDialog.add(closeButtonPanel, BorderLayout.SOUTH);
		sampleImageDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		sampleImageDialog.setModal(true);
		sampleImageDialog.pack();
		sampleImageDialog.setVisible(true);
		sampleImageDialog.setLocation(clientController.getBatchState().getWindowPosition());
		sampleImageDialog.setResizable(false);
	}

	ActionListener closeButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			sampleImageDialog.dispose();
		}
	};
}
