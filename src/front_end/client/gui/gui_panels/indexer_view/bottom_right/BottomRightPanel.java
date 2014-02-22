package front_end.client.gui.gui_panels.indexer_view.bottom_right;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:53 PM
 */
public class BottomRightPanel extends BasePanel {

	private FieldHelp fieldHelp;

	public BottomRightPanel(ClientController clientController) {
		super(clientController);

		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		fieldHelp = new FieldHelp(clientController);
		JScrollPane scrollPane = new JScrollPane(fieldHelp);

		ImageNavigation imageNavigation = new ImageNavigation(clientController);
		tabbedPane.addTab("Field Help", scrollPane);
		tabbedPane.addTab("Image Navigation", imageNavigation);
		add(tabbedPane, BorderLayout.CENTER);
	}

	public void updateTabs() {
		if (getClientController().getBatchState().hasDownloadedBatch()) {
			fieldHelp.changeFieldHelpImage();
		} else {
			fieldHelp.clear();
		}
	}
}
