package shared;

import back_end.server.database.Batches;
import back_end.server.database.Database;
import back_end.server.database.Users;
import org.w3c.dom.Element;
import shared.model.*;

import java.util.List;

/**
 * User: home
 * Date: 10/18/13
 * Time: 9:48 PM
 */
public class IndexerData {

	private Database database;

	/**
	 * This reads in the contents of a specified .xml file
	 *
	 * @param root The root Element in the Records.xml file (or any .xml file that you read in)
	 */
	public IndexerData(Element root) {
		this.database = new Database();
		Database.startTransaction();
		try {
			List<Element> rootElements = DataImporter.getChildElements(root);
			List<Element> userElements = DataImporter.getChildElements(rootElements.get(0));
			List<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));

			for (Element userElement : userElements) {
				UserDTO userDTO = new UserDTO(userElement);
				Users.add(userDTO);
			}
			for (Element projectElement : projectElements) {
				ProjectDTO projectDTO = new ProjectDTO(projectElement);
				database.projects.add(projectDTO);
				List<Element> projectChildElements = DataImporter.getChildElements(projectElement);
				addFields(projectDTO.getId(), projectChildElements);
				addBatches(projectDTO.getId(), projectChildElements);
			}
			Database.endTransaction(true);
		} catch (Exception e) {
			e.printStackTrace();
			if (Database.connection != null) {
				Database.endTransaction(false);
			}
		}
	}

	private void addBatches(int projectId, List<Element> projectChildElements) throws Exception {
		List<Element> batchElements = DataImporter.getChildElements(projectChildElements.get(5));
		for (Element batchElement : batchElements) {
			BatchDTO batchDTO = new BatchDTO(projectId, batchElement);
			database.batches.add(batchDTO);
			List<Element> batchChildElements = DataImporter.getChildElements(batchElement);
			if (batchChildElements.size() == 2) {
				addRecords(batchDTO.getId(), batchChildElements);
			}
		}
	}

	private void addFields(int projectId, List<Element> projectChildElements) throws Exception {
		List<Element> fieldElements = DataImporter.getChildElements(projectChildElements.get(4));
		int position = 0;
		for (Element fieldElement : fieldElements) {
			position++;
			FieldDTO fieldDTO = new FieldDTO(position, projectId, fieldElement);
			database.fields.add(fieldDTO);
		}
	}

	private void addRecords(int batchDTOId, List<Element> batchChildElements) throws Exception {
		List<Element> recordElements = DataImporter.getChildElements(batchChildElements.get(1));
		int recNum = 0;
		for (Element recordElement : recordElements) {
			recNum++;
			List<Element> valueElements = DataImporter.getChildElements(recordElement);
			for (Element valueElement : valueElements) {
				List<Element> values = DataImporter.getChildElements(valueElement);
				int position = 0;
				for (Element value : values) {
					position++;
					RecordDTO recordDTO = new RecordDTO(batchDTOId, value, position, recNum);
					database.records.add(recordDTO);
					if (recordDTO.getValue() != null) {
						Batches.markBatchAsIndexed(batchDTOId);
					}
				}
			}
		}
	}
}
