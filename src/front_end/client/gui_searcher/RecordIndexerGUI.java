package front_end.client.gui_searcher;

import java.awt.*;

public class RecordIndexerGUI {

	public static void main(String[] args) {
// 		ant client –Dhost=<HOST NAME> –Dport=<PORT NUMBER>
//		To make the client target work properly, you will need to modify the client target in your ANT build.xml file
// 		to specify the full package name of the Java class that implements your Client.

		String host = "localhost";
		String port = "39640";
		if (args.length == 2) {
			host = args[0];
			port = args[1];
		} else if (args.length == 1) {
//			TODO: this should maybe be checked if it is a host or a port
			host = args[0];
		}

//		BatchState batchState = BatchState.inst();
//		batchState.setHost(host);
//		batchState.setPort(port);

		final String finalHost = host;
		final String finalPort = port;
		EventQueue.invokeLater(

				new Runnable() {
					public void run() {
						RecordIndexerInitializer project2Runner = new RecordIndexerInitializer(finalHost, finalPort);
						project2Runner.startIndexer();
					}
				}
		);

	}
}
