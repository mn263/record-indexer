package shared.communication.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Searches the indexed records for the specified strings
 * User: mn263
 * Date: 10/12/13
 * Time: 4:37 PM
 */
public class Search_Result {

	private List<SearchResult> searchResults = new ArrayList<SearchResult>();

	public List<SearchResult> getSearchResults() {
		return this.searchResults;
	}

	public void setSearchResults(List<SearchResult> searchResult) {
		this.searchResults = searchResult;
	}

	public void addSearchResult(int batchId, String imageURL, int recordCount, int fieldId) {
		SearchResult searchResult = new SearchResult(batchId, imageURL, recordCount, fieldId);
		searchResults.add(searchResult);
	}

	public class SearchResult {

		private int batchId;
		private String imageURL;
		private int recordCount;
		private int fieldId;

		/**
		 * individual search results
		 *
		 * @param batchId     int
		 * @param imageURL    String
		 * @param recordCount int
		 * @param fieldId     int
		 */
		public SearchResult(int batchId, String imageURL, int recordCount, int fieldId) {
			this.batchId = batchId;
			this.imageURL = imageURL;
			this.recordCount = recordCount;
			this.fieldId = fieldId;
		}

		public int getBatchId() {
			return batchId;
		}

		public String getImageURL() {
			return imageURL;
		}

		public int getRecordCount() {
			return recordCount;
		}

		public int getFieldId() {
			return fieldId;
		}
	}
}
