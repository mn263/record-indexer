package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

import java.util.Collections;
import java.util.ArrayList;

public class TrieWords 
implements Trie
{
	private TrieNode[] rootNode;
	private int total_nodes;
	private int total_words;
	
	public TrieWords()
	{
		rootNode = new TrieNode[26];
		total_nodes = 1;
		total_words = 0;
	}

	@Override
	public void add(String word) 
	{
		int placement_value = word.charAt(0) - 97;
		char letter = word.charAt(0);

		if (rootNode[placement_value] == null)
		{
			total_nodes++;
			rootNode[placement_value] = new TrieNode(0, letter);
		}

		if (word.length() > 1)
		{
			total_nodes = rootNode[placement_value].insert(word.substring(1), total_nodes);
		}
		if(word.length() == 1)
		{
			rootNode[placement_value].count();
		}
		total_words++;
		return;
	}
	
	@Override
	public Trie.Node find(String word)
	{
		String x = word.toLowerCase();
		if(x.length() < 1)
		{
			return null;
		}
		int placement_value = x.charAt(0) - 97;
		if(rootNode[placement_value] != null)
		{
			TrieNode node = rootNode[placement_value].find(x.substring(1));
			return node;
		}
		return null;
	}
	
	public int findWordCount(String word)
	{
		int wordCount = 0;
		int placement_value = word.charAt(0) - 97;
		if(rootNode[placement_value] != null)
		{
			wordCount = rootNode[placement_value].findWordCount(word.substring(1));
			return wordCount;
		}
		return 0;
	}

	@Override
	public int getWordCount()
	{
		
		return this.total_words;
	}

	@Override
	public int getNodeCount()
	{

		return this.total_nodes;
	}

	public void deletion(String word, ArrayList<String> D_list, ArrayList<String> similar_words, int distance)
	{
		String d_word;
		String temp;
		for(int i = 1; i < word.length(); i++)
		{
			temp = word.substring(0, i-1);
			d_word = temp + word.substring(i, word.length());
			if(distance != 1)
			{
				D_list.add(d_word);
			}
			if(find(d_word) != null)
			{
				similar_words.add(d_word);
			}
		}
		if(word.length() > 0)
		{
			d_word = word.substring(0, word.length()-1);	
			if(distance != 1)
			{
				D_list.add(d_word);
			}
			if(find(d_word) != null)
			{
				similar_words.add(d_word);
			}			
		}

	}
	public void transportation(String word, ArrayList<String> T_list, ArrayList<String> similar_words, int distance)
	{
		String d_word;
		String front = "";
		String back = "";
		char swap1 = 0;
		char swap2 = 0;
		for(int i = 1; i < word.length(); i++)
		{
			if(i > 1)
			{
				front = word.substring(0, i-1);	
			}
			swap2 = word.charAt(i-1);
			swap1 = word.charAt(i);
			if(i + 1 < word.length())
			{
				back = word.substring(i+1, word.length());	
				d_word = front + swap1 + swap2 +  back;
			}
			else
			{
				d_word = front + swap1 + swap2;
			}
			if(distance != 1)
			{
				T_list.add(d_word);
			}
			if(find(d_word) != null)
			{
				similar_words.add(d_word);
			}	
		}
	}
	public void alteration(String word, ArrayList<String> A_list, ArrayList<String> similar_words, int distance)
	{
		String d_word = "";
		for(int i = 1; i < word.length(); i++)
		{
			for(int c = 97; c < 123; c++)
			{
				d_word = "";
				d_word = word.substring(0, i-1);
				char letter = (char) c;
				d_word = d_word + letter + word.substring(i);
				if(distance != 1)
				{
					A_list.add(d_word);
				}
				if(find(d_word) != null)
				{
					similar_words.add(d_word);
				}
			}
		}
		for(int c = 97; c < 123; c++)
		{
			d_word = "";
			if(word.length() > 0)
			{
				d_word = word.substring(0, word.length()-1);
			}
			char letter = (char) c;
			d_word = d_word + letter;
			if(distance != 1)
			{
				A_list.add(d_word);
			}
			if(find(d_word) != null)
			{
				similar_words.add(d_word);
			}
		}
	}
	public void insertion(String word, ArrayList<String> I_list, ArrayList<String> similar_words, int distance)
	{
		String d_word = "";
		String temp1 = "";
		for(int i = 0; i < word.length(); i++)
		{
			for(int c = 97; c < 123; c++)
			{
				d_word = "";
				if(i > 0)
				{
					temp1 = word.substring(0, i); 
				}
				char letter = (char) c;
				d_word = temp1 + letter + word.substring(i, word.length());	
				if(distance != 1)
				{
					I_list.add(d_word);
				}
				if(find(d_word) != null)
				{
					similar_words.add(d_word);
				}
			}
		}
		for(int c = 97; c < 123; c++)
		{
			char letter = (char) c;
			d_word = word + letter;	
			if(distance != 1)
			{
				I_list.add(d_word);
			}
			if(find(d_word) != null)
			{
				similar_words.add(d_word);
			}	
		}
	}
	public String chooseWord(String word, ArrayList<String> similar_words)
	{
		Collections.sort(similar_words);
		String most_similar = similar_words.get(0);
		int similarWordCount = findWordCount(most_similar);
		
		for(int i = 0; i < similar_words.size(); i++)
		{
			int wordCount = findWordCount(similar_words.get(i));
			if(wordCount > similarWordCount)
			{
				similarWordCount = wordCount;
				most_similar = similar_words.get(i);
			}
		}
		return most_similar;
	}
	public String similar(String word)
	{
		ArrayList<String> similar_list = new ArrayList<String>();
		ArrayList<String> D_list = new ArrayList<String>();
		ArrayList<String> T_list = new ArrayList<String>();
		ArrayList<String> A_list = new ArrayList<String>();
		ArrayList<String> I_list = new ArrayList<String>();
		
		deletion(word, D_list, similar_list, 0);
		transportation(word, T_list, similar_list, 0);
		alteration(word, A_list, similar_list, 0);
		insertion(word, I_list, similar_list, 0);
		if(similar_list.size() > 0)
		{
			String similarWord = chooseWord(word, similar_list);	
			return similarWord;
		}
		else
		{
			ArrayList<String> combined_list = new ArrayList<String>();
			combined_list.addAll(D_list);
			combined_list.	addAll(T_list);
			combined_list.addAll(A_list);
			combined_list.addAll(I_list);
	
			for(int i = 0; i < combined_list.size(); i++)
			{
				String currentWord = combined_list.get(i);

				deletion(currentWord, combined_list, similar_list, 1);
				transportation(currentWord, combined_list, similar_list, 1);
				alteration(currentWord, combined_list, similar_list, 1);
				insertion(currentWord, combined_list, similar_list, 1);
			}
			if(similar_list.size() > 0)
			{
				String similarWord = chooseWord(word, similar_list);	
				return similarWord;
			}
		}
		
		return null;
	}
	
	@Override
 	public String toString()
	{
		String output = "";
		for(int i = 0; i < 26; i++)
		{
			if(rootNode[i] != null)
			{
				output = rootNode[i].toString(output);
			}
		}
		return "";
	}
	
	@Override
	public int hashCode()
	{
		int result = total_nodes*13 + total_words*27;
		for(int i = 0; i < 26; i++)
		{
			result = result + 31 * rootNode[i].getWordCount();
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}


