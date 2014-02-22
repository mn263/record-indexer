package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;

public class TrieNode implements Trie.Node {

	public TrieNode[] N;
	public int wordCount = 0;
	public char letter;

	public TrieNode(char character) {
		N = new TrieNode[27];
		this.letter = character;
	}

	@Override
	public int getValue() {
		return wordCount;
	}

	public int insert(String word, int nodeCount) {
		int nodePlacement = word.charAt(0) - 97;
		char letter = word.charAt(0);
		if (nodePlacement == -87) {
			String stop = "Stop";
			return 1;
		}
		if (N[nodePlacement] == null) {
			N[nodePlacement] = new TrieNode(letter);
			nodeCount++;
		}
		if (word.length() > 1) {
			nodeCount = N[nodePlacement].insert(word.substring(1), nodeCount);
		} else {
			N[nodePlacement].wordCount = N[nodePlacement].getValue() + 1;
		}
		return nodeCount;
	}

	public TrieNode find(String word) {
		if (!word.isEmpty()) {
			int node_placement = word.charAt(0) - 97;

			if (N[node_placement] == null) {
				return null;
			} else {
				if (N[node_placement].find(word.substring(1)) != null) {
					return this;
				}
			}
		} else if (wordCount > 0) {
			return this;
		}
		return null;
	}
}