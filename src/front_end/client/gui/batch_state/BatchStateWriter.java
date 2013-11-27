package front_end.client.gui.batch_state;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: matt
 * Date: 11/26/13
 * Time: 2:24 PM
 */
public class BatchStateWriter {

	private BatchState batchState;

	public BatchStateWriter(BatchState batchState) {
		this.batchState = batchState;
	}

	public void writeBatchState() {

		XStream xStream = new XStream(new DomDriver());
		try {

			//make directory for the files if one hasn't already been created
			File directory = new File("batch_state_files/");
			if (!directory.exists()) {
				directory.mkdir();
			}

			//Write the batchState file to that directory
			String batchStateXML = xStream.toXML(batchState);
			String userName = batchState.getUserName();
			File file = new File("batch_state_files/" + userName + "_batchState.xml");
			//this only creates if there is not already a file there
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(batchStateXML);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
