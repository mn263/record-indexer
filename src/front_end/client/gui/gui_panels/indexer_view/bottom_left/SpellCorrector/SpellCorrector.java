package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

import java.io.IOException;

public interface SpellCorrector {
	
	@SuppressWarnings("serial")
	public static class NoSimilarWordFoundException extends Exception 
	{
		
	}
	public void useDictionary(String dictionaryFileName) throws IOException;

	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException;

}

