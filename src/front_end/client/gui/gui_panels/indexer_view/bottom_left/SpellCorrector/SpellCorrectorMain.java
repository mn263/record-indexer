package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple main class for running the spelling corrector
 */
public class SpellCorrectorMain {

	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */

	public static ArrayList<String> suggestWord(HashSet<String> dictionaryWords, String findWord) throws SpellCorrector.NoSimilarWordFoundException, IOException {

		findWord.toLowerCase();
		SpellCorrector corrector = new SpellCorrectorWords();
		corrector.useDictionary(dictionaryWords);
		String searchWord = findWord.replaceAll("\\s+","");
		return corrector.suggestSimilarWord(searchWord);
	}

}

