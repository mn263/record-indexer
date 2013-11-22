package front_end.client.gui_searcher.views.gui_panels.main_panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static front_end.client.gui_searcher.views.might_not_use.Constants.DOUBLE_HSPACE;
import static front_end.client.gui_searcher.views.might_not_use.Constants.SINGLE_HSPACE;

/**
 * User: matt
 * Date: 11/15/13
 * Time: 9:31 AM
 */
public class ImagesPanel extends BasePanel {

	private JComboBox<String> imagesComboBox;

	public ImagesPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("Images:"));
		add(Box.createRigidArea(SINGLE_HSPACE));

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		add(Box.createRigidArea(DOUBLE_HSPACE));

		imagesComboBox = new JComboBox<>();
		imagesComboBox.setMinimumSize(imagesComboBox.getPreferredSize());
		add(imagesComboBox);
		add(Box.createRigidArea(new Dimension(5 * 3, 0)));

		imagesComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = (String) imagesComboBox.getSelectedItem();
				if (selection != null && selection.length() > 2 && !selection.equalsIgnoreCase("FAILED\n")) {
//					getController().showImage(selection);
				}
			}
		});
	}

	public void setImages(ArrayList<String> values) {
		for (String value : values) {
			imagesComboBox.addItem(value);
		}
	}

	public void addImage(String value) {
		imagesComboBox.addItem(value);
	}

	public void clearImages() {
		imagesComboBox.removeAllItems();
	}
}