package front_end.client.gui_searcher.views.gui_panels.main_panel;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * User: matt
 * Date: 11/14/13
 * Time: 10:58 PM
 */
public class ImagePanel extends BasePanel {

//	If no one is logged in, or no batch is currently being indexed by the logged in user, the Image Panel should be empty.
//	The Image Panel should highlight the currently-selected record field (if highlights are turned on).
//	The Image Panel should invert the image (if image inversion is turned on).
//	The user should be able to select a record field by clicking on it with their mouse.
//	The user should be able to move the image in the panel by dragging it with their mouse.
//	The user should be able to zoom the image in and out with their mouse scroll wheel
//		(just like the “Zoom In”and “Zoom Out”buttons in the button bar). Zooming should be done relative to the center point of the currently visible area. That is, as zooming occurs, the point at the center of the view should remain fixed.


	private JLabel imageLabel;

	public ImagePanel() {
		imageLabel = new JLabel();
		imageLabel.setSize(new Dimension(800, 600));
		add(imageLabel);
	}

	public void changeImage(URL imageURL) {
		try {

			BufferedImage image = ImageIO.read(imageURL);
			Image scaledImage = image.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
			imageLabel.setIcon(new ImageIcon(scaledImage));
		} catch (IOException ex) {
			System.out.println("Could not read image");
		}
	}

	public void clearImage() {
		imageLabel.setIcon(null);
	}
}
