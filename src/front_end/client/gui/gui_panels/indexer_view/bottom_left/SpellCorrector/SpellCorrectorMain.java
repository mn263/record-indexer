package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

import java.io.IOException;

import spell.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class SpellCorrectorMain {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */

	public static void main(String[] args) throws SpellCorrector.NoSimilarWordFoundException, IOException
	{
		
		String dictionaryFileName = args[0];
		String findWord = args[1];
		findWord.toLowerCase();
		
		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector corrector = new SpellCorrectorWords();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(findWord);
		
		System.out.println("Suggestion is: " + suggestion);
	}

}

