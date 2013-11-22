package front_end.client.gui_searcher.views.gui_panels.main_panel;

import front_end.client.gui_searcher.controllers.BaseController;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.*;

import static front_end.client.gui_searcher.views.might_not_use.Constants.*;

@SuppressWarnings("serial")
public class BasePanel extends JPanel {

	protected static GridBagConstraints _gbc = new GridBagConstraints();
	private BaseController _controller;

	public BasePanel() {
		super();
	}

	public BaseController getController() {
		return _controller;
	}

	public void setController(BaseController value) {
		_controller = value;
	}

	public void addSingleSpace() {
		add(Box.createRigidArea(SINGLE_HSPACE));
	}

	public void addDoubleSpace() {
		add(Box.createRigidArea(DOUBLE_HSPACE));
	}


	public void addTripleSpace() {
		add(Box.createRigidArea(TRIPLE_HSPACE));
	}

	void initGbc(GridBagConstraints gbc) {
		gbc.gridx = _gbc.gridx;
		gbc.gridy = _gbc.gridy;
		gbc.gridwidth = _gbc.gridwidth;
		gbc.gridheight = _gbc.gridheight;
		gbc.weightx = _gbc.weightx;
		gbc.weighty = _gbc.weighty;
		gbc.anchor = _gbc.anchor;
		gbc.fill = _gbc.fill;
		gbc.insets = _gbc.insets;
		gbc.ipadx = _gbc.ipadx;
		gbc.ipady = _gbc.ipady;
	}

	protected void setGbc(GridBagConstraints gbc, int gridx, int gridy,
						  int gridwidth, int gridheight) {
		initGbc(gbc);
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
	}

	protected void setGbcWeight(GridBagConstraints gbc, int weightx, int weighty) {
		gbc.weightx = weightx;
		gbc.weighty = weighty;
	}

	protected void setGbcFillAnchor(GridBagConstraints gbc, int fill, int anchor) {
		gbc.fill = fill;
		gbc.anchor = anchor;
	}

	void setGbcPadding(GridBagConstraints gbc, int top, int left,
					   int bottom, int right, int ipadx, int ipady) {
		gbc.insets = new Insets(top, left, bottom, right);
		gbc.ipadx = ipadx;
		gbc.ipady = ipady;
	}

	WindowAdapter windowAdapter = new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};

	ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

		}
	};

	HyperlinkListener hyperlinkListener = new HyperlinkListener() {

		public void hyperlinkUpdate(HyperlinkEvent e) {

			System.out.println("hyperlink");

			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//				historyLoadPage(e.getURL().toString());
			}
		}
	};

	MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
//			if (e.isPopupTrigger()) {
//				if (e.getSource() == favList) {
//					favPopupMenu.show(e.getComponent(),
//							e.getX(), e.getY());
//				}
//				else if (e.getSource() == htmlViewer) {
//					htmlPopupMenu.show(e.getComponent(),
//							e.getX(), e.getY());
//				}
//			}
		}
	};
}

