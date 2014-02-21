package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

public interface Trie {

	public void add(String word);
	
	public Node find(String word);
	
	public int getWordCount();
	
	public int getNodeCount();
	
	@Override
	public String toString();
	
	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object o);

	
	
	public interface Node
	{
		public int getValue();
	}

}


