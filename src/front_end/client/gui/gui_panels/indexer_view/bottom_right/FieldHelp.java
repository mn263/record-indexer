package front_end.client.gui.gui_panels.indexer_view.bottom_right;

import front_end.client.gui.ClientController;
import org.apache.commons.io.IOUtils;
import shared.communication.results.DownloadBatch_Result;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: matt
 * Date: 11/19/13
 * Time: 8:37 PM
 */
public class FieldHelp extends JEditorPane {

	private ClientController clientController;
	private JEditorPane jEditorPane;

	public FieldHelp(ClientController clientController) {
		this.clientController = clientController;

		setEditable(false);
		HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
		setEditorKit(htmlEditorKit);
		JScrollPane scrollPane = new JScrollPane(jEditorPane);
		add(scrollPane);
		Document doc = htmlEditorKit.createDefaultDocument();
		setDocument(doc);
		changeFieldHelpImage();
	}

	public void changeFieldHelpImage() {
		if (clientController.getBatchState().hasDownloadedBatch()) {
			DownloadBatch_Result result = clientController.getBatchState().getDownloadBatchResult();
			List<DownloadBatch_Result.FieldInformation> fieldList = result.getFieldInformationList();
			int selectedColumn = clientController.getBatchState().getSelectedColumn();
			String imageURL = fieldList.get(selectedColumn).getHelpURL();

			try {
				setFieldHelp(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setFieldHelp(String imageURL) throws IOException {
		setText("");
		if (clientController.getBatchState().hasDownloadedBatch()) {
			imageURL = clientController.getHostAndPort() + imageURL;
			URL url = new URL(imageURL);
			String html = IOUtils.toString(url);
			setText(html);
		}
	}

	public void clear() {
		setText("");
	}
}
