package shared.communication.results;

/**
 * Gets a sample image for a specified project
 * User: mn263
 * Date: 10/12/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetSampleImage_Result {

	private String imageURL;

	/**
	 * the path for the image url
	 *
	 * @param imageURL imagePath
	 */
	public GetSampleImage_Result(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageURL() {
		return this.imageURL;
	}
}
