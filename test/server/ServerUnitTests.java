package back_end.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import back_end.server.database.*;
import shared.DataImporter;
import shared.communication.params.*;
import shared.communication.results.*;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;


public class ServerUnitTests {

    public static void main(String[] args) {

        String[] testClasses = new String[]{
                "test.back_end.server.ServerUnitTests"
        };
        org.junit.runner.JUnitCore.main(testClasses);
    }

    @Before
    public void setup() {
        String[] argsDataImporter = {"/users/guest/m/mn263/CS240/record-indexer/Records/Records.xml"};

        DataImporter.main(argsDataImporter);
        Database.startTransaction();
    }

    @After
    public void teardown() {
        Database.endTransaction(false);
    }

    @Test
    public void validateUserTEST() {
//   		 CHECK THAT GETTING A VALID USER WORKS
        ValidateUser_Result validateUser_result = Users.validateUser(new ValidateUser_Params("test1", "test1"));
        assertNotNull(validateUser_result);
        assertTrue(validateUser_result.isUser());
        assertEquals(validateUser_result.getUserNameFirst(), "Test");
        assertEquals(validateUser_result.getUserNameLast(), "One");
        assertEquals(validateUser_result.getRecordCount(), 0);

//   		 CHECK THAT GETTING AN INVALID USER COMES BACK RIGHT
        validateUser_result = Users.validateUser(new ValidateUser_Params("test1", ""));
        assertNull(validateUser_result);
    }

    @Test
    public void getProjectTEST() {
//   		 CHECK THAT GETTING A VALID PROJECT(S) WORKS
        GetProjects_Result getProjects_result = Projects.getProjects(new GetProjects_Params("test1", "test1"));
        assertNotNull(getProjects_result);
        assertNotNull(getProjects_result.getProjects());
        assertTrue(getProjects_result.getProjects().size() == 3);
        assertEquals(getProjects_result.getProjects().get(0).getProjectTitle(), "1890 Census");
        assertEquals(getProjects_result.getProjects().get(1).getProjectTitle(), "1900 Census");
        assertEquals(getProjects_result.getProjects().get(2).getProjectTitle(), "Draft Records");

//   		 CHECK THAT GETTING AN INVALID PROJECT WORKS
        getProjects_result = Projects.getProjects(new GetProjects_Params("test1", ""));
        assertNull(getProjects_result);
    }

    @Test
    public void getSampleImageTEST() {
//   		 CHECK THAT GETTING A VALID SAMPLE IMAGE WORKS
        GetSampleImage_Result getSampleImage_result = Batches.getSampleImage(new GetSampleImage_Params("test1", "test1", 1));
        assertNotNull(getSampleImage_result);
        assertTrue(getSampleImage_result.getImageURL().contains("images/1890_image0.png"));

//   		 CHECK THAT GETTING AN INVALID SAMPLE IMAGE WORKS
        getSampleImage_result = Batches.getSampleImage(new GetSampleImage_Params("test1", "test1", 12));
        assertNull(getSampleImage_result);
    }

    @Test
    public void downloadBatchTEST() {
//   		 CHECK THAT GETTING A VALID BATCH WORKS
        DownloadBatch_Result downloadBatch_result = Batches.downloadBatch(new DownloadBatch_Params("test1", "test1", 1));
        assertNotNull(downloadBatch_result);
        assertEquals(downloadBatch_result.getProjectId(), 1);
        assertEquals(downloadBatch_result.getBatchId(), 1);
        assertTrue(downloadBatch_result.getImageURL().contains("images/1890_image0.png"));

//   		 CHECK THAT GETTING AN INVALID BATCH WORKS
        downloadBatch_result = Batches.downloadBatch(new DownloadBatch_Params("test1", "test1", 1));
        assertNull(downloadBatch_result);
    }

    @Test
    public void submitBatchTEST() {
        List<String> recordValues = new ArrayList<String>();
        recordValues.add("1");
        recordValues.add("2");
        recordValues.add("3");
        recordValues.add("4");
        SubmitBatch_Params submitBatch_params = new SubmitBatch_Params("test1", "test1", 43);
        submitBatch_params.addRecordToField(recordValues);
        submitBatch_params.addRecordToField(recordValues);
        submitBatch_params.addRecordToField(recordValues);
        submitBatch_params.addRecordToField(recordValues);
        submitBatch_params.addRecordToField(recordValues);
        submitBatch_params.addRecordToField(recordValues);
        submitBatch_params.addRecordToField(recordValues);
        try {
            Batches.assignBatchToUser("test1", 43);
        } catch (Exception e) {
            e.printStackTrace();
        }
//   	 CHECK THAT SUBMITTING A VALID BATCH WORKS
        assertTrue(Batches.submitBatch(submitBatch_params)); //Returns TRUE if the batch successfully committed

//   	 CHECK THAT SUBMITTING AN INVALID BATCH WORKS
        submitBatch_params.addRecordToField(recordValues);
        assertFalse(Batches.submitBatch(submitBatch_params)); //Returns FALSE if the batch fails to commit
    }

    @Test
    public void getFieldsTEST() {
//   		 CHECK THAT GETTING A VALID FIELD WORKS
        GetFields_Result getFields_result = Fields.getFields(new GetFields_Params("test1", "test1", 1));
        assertNotNull(getFields_result);
        assertEquals(getFields_result.getFields().get(0).getProjectId(), 1);
        assertEquals(getFields_result.getFields().get(0).getFieldId(), 1);
        assertTrue(getFields_result.getFields().get(0).getFieldTitle().equalsIgnoreCase("Last Name"));

//   		 CHECK THAT GETTING AN INVALID FIELD WORKS
        getFields_result = Fields.getFields(new GetFields_Params("test1", "test1", 11));
        assertNull(getFields_result);
    }

    @Test
    public void searchTEST() {
        List<String> fieldIds = new ArrayList<String>();
        fieldIds.add("10");
        List<String> searchValues = new ArrayList<String>();
        searchValues.add("fox");
//        CHECK THAT A VALID SEARCH WORKS
        Search_Result search_result = Projects.search(new Search_Params("test1", "test1", fieldIds, searchValues));
        assertNotNull(search_result);
        assertEquals(search_result.getSearchResults().get(0).getBatchId(), 41);
        assertTrue(search_result.getSearchResults().get(0).getImageURL().contains("images/draft_image0.png"));
        assertEquals(search_result.getSearchResults().get(0).getRecordCount(), 1);
        assertEquals(search_result.getSearchResults().get(0).getFieldId(), 10);

//        CHECK THAT AN INVALID SEARCH WORKS
        List<String> fieldIdsInvalid = new ArrayList<String>();
        fieldIds.add("1");
        search_result = Projects.search(new Search_Params("test1", "test1", fieldIdsInvalid, searchValues));
        assertNull(search_result);
    }

    @Test
    public void downloadFileTEST() {
//    	byte[] buffer = new byte[1024];
//    	String classPath = exchange.getRequestURI().getPath();
//    	String path = getFileName(classPath);
//    	String absolutePath = "/users/guest/m/mn263/CS240/record-indexer/Records/images/" + path;
//    	File file = new File(absolutePath);
//    	String filePath = file.getAbsolutePath();
//    	InputStream fileToSend = new FileInputStream(filePath);
//    	try {
//        	int bytes;
//        	exchange.sendResponseHeaders(200, 0);
//        	while ((bytes = fileToSend.read(buffer)) >= 0) {
//            	exchange.getResponseBody().write(buffer, 0, bytes);
//        	}
//        	exchange.close();
//    	}
//    	catch (IOException e) {
//        	e.printStackTrace();
//    	}
//    	finally {
//        	fileToSend.close();
//    	}
//    	assertFalse(validateUser_result.isUser());
//   	 assertNull(validateUser_result.getUserNameFirst());
//   	 assertNull(validateUser_result.getUserNameLast());
//   	 assertEquals(validateUser_result.getRecordCount(), 0);
    }

}
 


