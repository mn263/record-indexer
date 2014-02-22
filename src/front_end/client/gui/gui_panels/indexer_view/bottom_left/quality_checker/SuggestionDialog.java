package front_end.client.gui.gui_panels.indexer_view.bottom_left.quality_checker;

import front_end.client.gui.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * User: matt
 * Date: 12/10/13
 * Time: 3:06 PM
 */
public class SuggestionDialog extends JDialog {

	private static final int LOGIN_WIDTH = 300;
	private static final int LOGIN_HEIGHT = 250;
	private ClientController clientController;
	private JList<String> suggList;
	private int column;
	private int row;

	public SuggestionDialog(ClientController clientController, ArrayList<String> suggestedWords, int column, int row) {
		this.clientController = clientController;
		this.column = column;
		this.row = row;

		getContentPane().setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
		setPreferredSize(new Dimension(LOGIN_WIDTH, LOGIN_HEIGHT));
		setLocation(clientController.getBatchState().getWindowPosition());

		setTitle("Suggestions");
		SuggListModel suggListModel = new SuggListModel();
		for (String suggestedWord : suggestedWords) {
			suggListModel.add(suggestedWord.toLowerCase());
		}
		suggList = new JList<>(suggListModel);
		JScrollPane suggScrollPane = new JScrollPane(suggList);
		suggScrollPane.setPreferredSize(new Dimension(100, 170));
		JButton cancelButton = new JButton("Cancel");
		JButton useSelectionButton = new JButton("Use Suggestion");
		cancelButton.addActionListener(cancelButtonListener);
		useSelectionButton.addActionListener(useSuggestionButtonLister);
		cancelButton.setMaximumSize(new Dimension(100, 50));
		useSelectionButton.setMaximumSize(new Dimension(100, 50));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(useSelectionButton);

		if (suggestedWords.isEmpty()) {
			useSelectionButton.setEnabled(false);
		}

		this.setLayout(new BorderLayout());
		this.add(suggScrollPane, BorderLayout.NORTH);
		this.add(buttonsPanel, BorderLayout.CENTER);
		pack();
		setResizable(false);
	}

	ActionListener cancelButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	ActionListener useSuggestionButtonLister = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String value = suggList.getSelectedValue();
			if (value == null || value.isEmpty()) {
				suggList.setSelectionInterval(0, 0);
				value = suggList.getSelectedValue();
			}
			clientController.getBatchState().setRecordValue(value, column, row);
			dispose();
		}
	};
}
