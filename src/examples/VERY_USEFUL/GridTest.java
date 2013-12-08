package examples.VERY_USEFUL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Cay Horstmann
 * @version 1.33 2007-06-12
 */
public class GridTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ButtonFrame2 frame = new ButtonFrame2();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * A frame with a button panel
 */
class ButtonFrame2 extends JFrame {
	public ButtonFrame2() {
		setTitle("Grid Test");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// create buttons
		JLabel yellowButton = new JLabel("User:");
		JTextField blueButton = new JTextField("Submit");
		JButton redButton = new JButton("Red");
		JButton greenButton = new JButton("Green");
		JButton orangeButton = new JButton("Orange");
		JButton grayButton = new JButton("Gray");

		JPanel rowPanel = new JPanel();
//	   rowPanel.add()

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 2));

		// add buttons to panel
		rowPanel.add(yellowButton);
		rowPanel.add(blueButton);
		buttonPanel.add(rowPanel);
		buttonPanel.add(redButton);
		buttonPanel.add(greenButton);
		buttonPanel.add(orangeButton);
		buttonPanel.add(grayButton);

		// add panel to frame
		add(buttonPanel);

		// create button actions
		ColorAction yellowAction = new ColorAction(Color.YELLOW);
		ColorAction blueAction = new ColorAction(Color.BLUE);
		ColorAction redAction = new ColorAction(Color.RED);
		ColorAction greenAction = new ColorAction(Color.GREEN);
		ColorAction orangeAction = new ColorAction(Color.ORANGE);
		ColorAction grayAction = new ColorAction(Color.GRAY);

		// associate actions with buttons
//      yellowButton.addActionListener(yellowAction);
		blueButton.addActionListener(blueAction);
		redButton.addActionListener(redAction);
		greenButton.addActionListener(greenAction);
		orangeButton.addActionListener(orangeAction);
		grayButton.addActionListener(grayAction);
	}

	/**
	 * An action listener that sets the panel's background color.
	 */
	private class ColorAction implements ActionListener {
		public ColorAction(Color c) {
			backgroundColor = c;
		}

		public void actionPerformed(ActionEvent event) {
			buttonPanel.setBackground(backgroundColor);
		}

		private Color backgroundColor;
	}

	private JPanel buttonPanel;

	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 200;
}