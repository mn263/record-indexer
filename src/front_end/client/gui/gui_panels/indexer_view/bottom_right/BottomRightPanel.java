package front_end.client.gui.gui_panels.indexer_view.bottom_right;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.FormEntry;
import front_end.client.gui.gui_panels.indexer_view.bottom_left.TableEntry;

import javax.swing.*;
import java.awt.*;

/**
 * User: matt
 * Date: 11/22/13
 * Time: 7:53 PM
 */
public class BottomRightPanel extends BasePanel {

	public BottomRightPanel(ClientController clientController) {
		super(clientController);

		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		TableEntry tableEntry = new TableEntry(clientController);
		FormEntry formEntry = new FormEntry(clientController);
		tabbedPane.addTab("File Help", tableEntry);
		tabbedPane.addTab("Image Navigation", formEntry);
		add(tabbedPane, BorderLayout.CENTER);
	}
}
