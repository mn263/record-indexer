package shared;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads in all of the data from a specified path location
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class DataImporter {

	public static void main(String[] args) {

		String xmlFilePath = args[0];
		File file = new File(xmlFilePath);
		File dest = new File("Records");

		File emptydb = new File("emptydb.sqlite");
		File currentdb = new File("recordindexer.sqlite");


		try {
			if (!file.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
				FileUtils.deleteDirectory(dest);

			FileUtils.copyDirectory(file.getParentFile(), dest);

			//	Overwrite my existing *.sqlite database with an empty one.  Now, my
			//	database is completelty empty and ready to load with data.
			FileUtils.copyFile(emptydb, currentdb);

		} catch (IOException e) {
			e.printStackTrace();
		}

		File parsefile = new File(dest.getPath() + File.separator + file.getName());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null; //Can use URI instead of xmlFile
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(parsefile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert doc != null;
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		new IndexerData(root);
	}

	public static ArrayList<Element> getChildElements(Node node) {
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				result.add((Element) child);
			}
		}
		return result;
	}

	public static String getValue(Element element) {
		String result;
		if (element == null) {
			return null;
		}
		Node child = element.getFirstChild();
		result = child.getNodeValue();
		return result;
	}
}
