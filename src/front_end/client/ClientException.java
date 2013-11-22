package front_end.client;

/**
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:34 AM
 */
@SuppressWarnings("serial")
public class ClientException extends Exception {

	public ClientException() {
		return;
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable throwable) {
		super(throwable);
	}

	public ClientException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
