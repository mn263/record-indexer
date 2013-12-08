package front_end.client.gui.gui_panels.indexer_view.image_section;


import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;

import java.awt.*;

/**
 * User: matt
 * Date: 11/14/13
 * Time: 10:58 PM
 */
public class ImagePanel extends BasePanel {

	private DrawingComponent component;

	public ImagePanel(ClientController clientController) {
		super(clientController);
		setBackground(Color.GRAY);
		component = new DrawingComponent(getClientController());
		add(component);
	}

	public void reloadImage() {
		component.changeImage();
	}

	public void updateRecordSelected() {
		component.updateRecordSelected();
	}

	public void toggleHighlight() {
		component.repaint();
	}

	public void invertImage() {
		component.invertImage();
	}

	public void updateZoom() {
		component.updateZoom();
	}
}
