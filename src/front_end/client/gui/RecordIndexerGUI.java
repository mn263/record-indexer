package front_end.client.gui;

import java.awt.*;

public class RecordIndexerGUI {

	public static void main(String[] args) {

		String host;
		String port;
		try {
			host = args[0];
			port = args[1];
		} catch (Exception e) {
			host = "localhost";
			port = "39640";
		}

		final ClientController clientController = new ClientController(host, port);

		EventQueue.invokeLater(

				new Runnable() {
					public void run() {
						RecordIndexerInitializer project2Runner = new RecordIndexerInitializer(clientController);
						project2Runner.startIndexer();
					}
				}
		);

	}
}

