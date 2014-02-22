package front_end.client.gui.gui_panels.menu.download_batch;

import shared.communication.results.GetProjects_Result;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 10:04 PM
 */
public class DownloadBatchPanel extends JPanel {

	private JComboBox<String> comboBox;
	private JButton viewSampleButton;
	private JButton cancelButton;
	private JButton downloadButton;

	public DownloadBatchPanel(List<GetProjects_Result.ProjectInfo> projectNames) {
		super();

		JLabel projectLabel = new JLabel("Project:");

		comboBox = new JComboBox<>();
		for (GetProjects_Result.ProjectInfo project : projectNames) {
			comboBox.addItem(project.getProjectTitle());
		}

		// create buttons
		viewSampleButton = new JButton("View Sample");
		cancelButton = new JButton("Cancel");
		downloadButton = new JButton("Download");


		setLayout(new GridLayout(2, 1));
		// add 2 rows to DownloadBatchPanel
		JPanel rowOnePanel = new JPanel();
		rowOnePanel.add(projectLabel);
		rowOnePanel.add(comboBox);
		rowOnePanel.add(viewSampleButton);
		add(rowOnePanel);

		JPanel rowTwoPanel = new JPanel();
		rowTwoPanel.add(cancelButton);
		rowTwoPanel.add(downloadButton);
		rowTwoPanel.setAlignmentX(CENTER_ALIGNMENT);
		add(rowTwoPanel);
	}

	public JButton getViewSampleButton() {
		return viewSampleButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getDownloadButton() {
		return downloadButton;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}
}
