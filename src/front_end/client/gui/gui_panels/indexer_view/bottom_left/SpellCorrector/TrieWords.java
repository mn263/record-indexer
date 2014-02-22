package front_end.client.gui.gui_panels.indexer_view.bottom_left.SpellCorrector;


import java.util.ArrayList;
import java.util.Collections;

public class TrieWords implements Trie {
	private TrieNode[] rootNode;
	public int totalNodes;
	public int totalWords;

	public TrieWords() {
		rootNode = new TrieNode[26];
		totalNodes = 1;
		totalWords = 0;
	}

	@Override
	public void add(String word) {
		word = word.toLowerCase();
		int placement_value = word.charAt(0) - 97;
		char letter = word.charAt(0);
		if (placement_value == 65182) {
			word = word.substring(1, word.length());
			String top = "stop";
			letter = word.charAt(0);
			placement_value = word.charAt(0) - 97;
		}
		if (rootNode[placement_value] == null) {
			totalNodes++;
			rootNode[placement_value] = new TrieNode(letter);
		}
		if (word.length() > 1) {
			totalNodes = rootNode[placement_value].insert(word.substring(1), totalNodes);
		} else {
			rootNode[placement_value].wordCount = rootNode[placement_value].getValue() + 1;
		}
		totalWords++;
	}

	@Override
	public Trie.Node find(String word) {
		word = word.toLowerCase();
		if (word.isEmpty()) {
			return null;
		}
		int placement_value = word.charAt(0) - 97;
		if (rootNode[placement_value] == null) {
			return null;
		} else {
			return rootNode[placement_value].find(word.substring(1));
		}
	}

	@Override
	public int getWordCount() {
		return this.totalWords;
	}

	@Override
	public int getNodeCount() {
		return this.totalNodes;
	}

	public void processWord(ArrayList<String> letterList, ArrayList<String> eventList, ArrayList<String> similarWords, int distance) {
		String processedWord = "";
		for (String letter : letterList) {
			processedWord = processedWord + letter;
		}
		if (distance == 0) {
			eventList.add(processedWord);
		}
		if (find(processedWord) != null) {
			similarWords.add(processedWord);
		}
	}

	public void deletion(ArrayList<String> letterList, ArrayList<String> deleteList, ArrayList<String> similarWords, int distance) {
		ArrayList<String> letterListCopy = new ArrayList<>();
		for (int index = 0; index < letterList.size(); index++) {
			letterListCopy.clear();
			letterListCopy.addAll(letterList);
			letterListCopy.remove(index);
			processWord(letterListCopy, deleteList, similarWords, distance);
		}
	}

	public void transportation(ArrayList<String> letterList, ArrayList<String> transformationList, ArrayList<String> similarWords, int distance) {
		ArrayList<String> letterListCopy = new ArrayList<>();
		for (int i = 0; i < letterList.size() - 1; i++) {
			for (int j = 1; j < letterList.size(); j++) {
				letterListCopy.clear();
				letterListCopy.addAll(letterList);
				Collections.swap(letterListCopy, i, j);
				processWord(letterListCopy, transformationList, similarWords, distance);
			}
		}
	}

	public void alteration(ArrayList<String> letterList, ArrayList<String> alterationList, ArrayList<String> similarWords, int distance) {
		ArrayList<String> letterListCopy = new ArrayList<>();
		for (int index = 0; index < letterList.size(); index++) {
			for (int intChar = 97; intChar < 123; intChar++) {
				letterListCopy.clear();
				letterListCopy.addAll(letterList);
				char letter = (char) intChar;
				letterListCopy.add(index, Character.toString(letter));
				letterListCopy.remove(index + 1);
				processWord(letterListCopy, alterationList, similarWords, distance);
			}
		}
	}

	public void insertion(ArrayList<String> letterList, ArrayList<String> insertionList, ArrayList<String> similarWords, int distance) {
		ArrayList<String> letterListCopy = new ArrayList<>();
		for (int index = 0; index < letterList.size() + 1; index++) {
			for (int intChar = 97; intChar < 123; intChar++) {
				letterListCopy.clear();
				letterListCopy.addAll(letterList);
				char letter = (char) intChar;
				letterListCopy.add(index, Character.toString(letter));
				processWord(letterListCopy, insertionList, similarWords, distance);
			}
		}
	}

	public ArrayList<String> getSimilarWord(String word) {
		ArrayList<String> similarList = new ArrayList<String>();
		ArrayList<String> processedList = new ArrayList<String>();
		ArrayList<String> letterList = new ArrayList<>();
		for (char letter : word.toCharArray()) {
			letterList.add(Character.toString(letter));
		}
		performEdits(letterList, processedList, similarList, 0);

		for (String currentWord : processedList) {
			letterList.clear();
			for (char letter : currentWord.toCharArray()) {
				letterList.add(Character.toString(letter));
			}
			deletion(letterList, processedList, similarList, 1);
			transportation(letterList, processedList, similarList, 1);
			alteration(letterList, processedList, similarList, 1);
			insertion(letterList, processedList, similarList, 1);
		}
		if (!similarList.isEmpty()) {
			return similarList;
		} else {
			return null;
		}
	}

	private void performEdits(ArrayList<String> letterList, ArrayList<String> processedList,
							  ArrayList<String> similarList, int distance) {
		deletion(letterList, processedList, similarList, distance);
		transportation(letterList, processedList, similarList, distance);
		alteration(letterList, processedList, similarList, distance);
		insertion(letterList, processedList, similarList, distance);
	}

	public String chooseWord(String word, ArrayList<String> similar_words) {
		Collections.sort(similar_words);
		String most_similar = similar_words.get(0);
		Trie.Node node = find(most_similar);
		int similarWordCount = 0;
		if (node != null) {
			similarWordCount = node.getValue();
		}
		for (String similarWord : similar_words) {
			Trie.Node nodeSimilar = find(similarWord);
			int wordCount = 0;
			if (nodeSimilar != null) {
				wordCount = nodeSimilar.getValue();
			}
			if (wordCount > similarWordCount) {
				similarWordCount = wordCount;
				most_similar = similarWord;
			}
		}
		return most_similar;
	}

	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < 26; i++) {
			if (rootNode[i] != null) {
				output = buildOutput(rootNode[i], output);
			}
		}
		return "";
	}

	public String buildOutput(TrieNode node, String output) {
		output = output + Character.toString(node.letter);
		for (int i = 0; i < 26; i++) {
			if (node.N[i] != null) {
				output = buildOutput(node.N[i], output);
			}
		}
		if (output.length() > 0) {
			output = output.substring(0, output.length() - 1);
		}
		return output;
	}

	@Override
	public int hashCode() {
		int result = totalNodes * 13 + totalWords * 27;
		for (int i = 0; i < 26; i++) {
			result = result + 31 * rootNode[i].getValue();
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return false;
		}
	}
}

