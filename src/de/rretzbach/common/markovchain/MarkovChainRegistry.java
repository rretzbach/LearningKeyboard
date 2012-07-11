package de.rretzbach.common.markovchain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkovChainRegistry {
	
	private Map<String, MarkovChainEntry> entries;

	public Map<String, MarkovChainEntry> getEntries() {
		return entries;
	}

	public void addOccurence(String lastWord, String word) {
		String lastWordLowercase = null;
		if ( lastWord != null ) {
			lastWordLowercase = lastWord.toLowerCase();
		}
		addRawOccurence(lastWordLowercase, word);
	}

	private void addRawOccurence(String lastWord, String word) {
		if (entries == null) {
			entries = new HashMap<String, MarkovChainEntry>();
		}
		if (!entries.containsKey(lastWord)) {
			entries.put(lastWord, new MarkovChainEntry());
		}
		MarkovChainEntry entry = entries.get(lastWord);
		entry.addOccurence(word);
	}

	public List<String> suggestNextWords(String previousWord) {
		String previousWordLowercase = null;
		if ( previousWord != null ) {
			previousWordLowercase = previousWord.toLowerCase();
		}
		return suggestNextWordsRaw(previousWordLowercase);
	}

	private List<String> suggestNextWordsRaw(String string) {
		if (!entries.containsKey(string)) {
			return Collections.emptyList();
		}
		
		MarkovChainEntry markovChainEntry = entries.get(string);
		if (markovChainEntry == null) {
			return Collections.emptyList();
		}
		
		return markovChainEntry.getSortedOccurences();
	}

}
