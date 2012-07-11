package de.rretzbach.common.markovchain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkovChainService {
	private Map<String, Set<Map<String, Integer>>> suggestions;
	private MarkovChainRegistry registry;

	public void parseInput(String input) {
		if (registry == null) {
			registry = new MarkovChainRegistry();
		}
		Pattern wordPattern = Pattern.compile("(\\p{L}+)|([^\\s\\p{L}]+)");
		Matcher m = wordPattern.matcher(input);
		
		String lastWord = null;
		
		while (m.find()) {
			String word = m.group(1);			
			String punctuation = m.group(2);
			
			if (word != null) {
				registry.addOccurence(lastWord, word);
			}
			lastWord = word;
		}
	}

	public List<String> suggestNextWords(String string) {
		return registry.suggestNextWords(string);
	}

	public Map<String, MarkovChainEntry> getEntries() {
		return registry.getEntries();
	}

}
