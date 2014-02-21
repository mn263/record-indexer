package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;


public class TrieNode implements Trie.Node {
	
	private TrieNode[] N;
	private int wordCount = 0;
	private char letter;
	
	public TrieNode(int Count, char character) 
	{
		N = new TrieNode[26];
		this.setWordCount(Count);
		this.letter = character;
	}
	
    protected int insert(String word, int node_count)
    {
    	int node_placement = word.charAt(0) - 97;
    	char letter = word.charAt(0);
        if (N[node_placement] == null) 
        {
            N[node_placement] = new TrieNode(0, letter);
            node_count++;
        }
        if (word.length() > 1) 
        {
           node_count = N[node_placement].insert(word.substring(1), node_count);
        }
        else 
        {
        	N[node_placement].setWordCount(N[node_placement].getWordCount() + 1);
        }
        return node_count;
    }
	
	public void count() 
	{
	   	setWordCount(getWordCount() + 1);
	}

	public TrieNode find(String word)
	{
	  	if(word.length() == 0)
	  	{
	  		if(wordCount > 0)
	  		{
	  			return this;
	  		}
	  	}
	  	if(word.length() > 0)
	  	{
		  	int node_placement = word.charAt(0) - 97;
	        if (N[node_placement] == null) 
	        {
	        	return null;
	        }
	        else
	        {
	        	if(N[node_placement].find(word.substring(1)) != null)
	        	{
	        		return this;
	        	}
	        }
	  	}

		return null;
	}
	
	public int findWordCount(String word)
	{
	  	if(word.length() == 0)
	  	{
	  		return wordCount;
	  	}
	  	int node_placement = word.charAt(0) - 97;
        if (N[node_placement] == null) 
        {
        	return 0;
        }
        else
        {
        	int count = N[node_placement].findWordCount(word.substring(1));
        	return count;
        }
	}
	
	public String toString(String output)
	{ 
		output = output + Character.toString(letter);
		for(int i = 0; i < 26; i++)
		{
			if(getWordCount() > 0 && i == 0)
			{
				System.out.println(output + " " + getWordCount());
			}
			if(N[i] != null)
			{
				output = N[i].toString(output);
			}

		}
		if(output.length() > 0)
		{
			output = output.substring(0, output.length() - 1);
		}
		return output;
	}
	
	@Override
	public int getValue() {

		return 0;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
}
