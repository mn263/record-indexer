package front_end.client.gui.batch_state;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: matt
 * Date: 11/26/13
 * Time: 2:23 PM
 */
public class BatchStateReader {

	private BatchState batchState;

	public BatchStateReader(BatchState batchState) {
		this.batchState = batchState;
	}

	public void readBatchState() {

		XStream xStream = new XStream(new DomDriver());
		String userName = batchState.getUserName();
		BufferedReader br = null;

		try {

			File file = new File("batch_state_files/" + userName + "_batchState.xml");
			if (!file.exists()) {
//				TODO: figure out what to do here
				return;
//				file.createNewFile();
			}
			FileReader fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String batchStateXML = "";
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				batchStateXML += currentLine;
			}

			batchState = (BatchState) xStream.fromXML(batchStateXML);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
