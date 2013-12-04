package front_end.client.gui.gui_panels.indexer_view.image_section;


import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;

import java.awt.*;
import java.net.URL;

/**
 * User: matt
 * Date: 11/14/13
 * Time: 10:58 PM
 */
public class ImagePanel extends BasePanel {

//	TODO: The Image Panel should highlight the currently-selected record field (if highlights are turned on).
//	TODO: The Image Panel should invert the image (if image inversion is turned on).
//	TODO: The user should be able to select a record field by clicking on it with their mouse.
//	TODO: The user should be able to zoom the image in and out with their mouse scroll wheel. Zooming should be done relative to the center point of the currently visible area. That is, as zooming occurs, the point at the center of the view should remain fixed.


	private DrawingComponent component;


	public ImagePanel(ClientController clientController) {
		super(clientController);
		setBackground(Color.GRAY);
		component = new DrawingComponent(getClientController());
		add(component);
//		add(component, BorderLayout.CENTER);
	}

	public void updateImage() {
		String imageURL = getClientController().getBatchState().getImageURL();
		try {
			component.changeImage(new URL(imageURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateZoom() {
		component.updateZoom();
	}
}
