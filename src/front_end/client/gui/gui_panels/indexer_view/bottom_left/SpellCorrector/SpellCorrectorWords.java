package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class SpellCorrectorWords
		implements SpellCorrector {
	TrieWords word = new TrieWords();

	public SpellCorrectorWords() {
	}

	@Override
	public void useDictionary(HashSet<String> dictionary) throws IOException {
		for (String value : dictionary) {
			String wordValue = value.replaceAll("\\s+","");
			wordValue = wordValue.toLowerCase();
			word.add(wordValue);
		}
	}

	@Override
	public ArrayList<String> suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		if (word.find(inputWord) == null) {
			ArrayList<String> similarWords = word.getSimilarWord(inputWord);
			if (similarWords != null) {
				return similarWords;
			} else {
				throw new NoSimilarWordFoundException();
			}
		} else {
			return null;
		}
	}

}
