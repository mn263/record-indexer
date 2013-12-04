package front_end.client.gui.gui_panels.menu.download_batch.view_sample;

import front_end.client.gui.ClientController;
import front_end.client.gui.base_classes.BasePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * User: matt
 * Date: 11/25/13
 * Time: 4:12 PM
 */
public class ViewSamplePanel extends BasePanel {

	private JLabel imageLabel = new JLabel();

	public ViewSamplePanel(ClientController clientController, String imageURL) {
		super(clientController);

		try {
			changeImage(new URL(imageURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
		add(imageLabel);
	}

	public void changeImage(URL imageURL) throws IOException {
		BufferedImage image = ImageIO.read(imageURL);
		Image scaledImage = image.getScaledInstance(400, -1, Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(scaledImage));
	}
}
