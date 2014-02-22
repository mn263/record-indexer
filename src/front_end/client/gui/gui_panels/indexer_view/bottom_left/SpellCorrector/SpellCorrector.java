package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public interface SpellCorrector {

	@SuppressWarnings("serial")
	public static class NoSimilarWordFoundException extends Exception {
	}

	public void useDictionary(HashSet<String> dictionary) throws IOException;

	public ArrayList<String> suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException;

}

