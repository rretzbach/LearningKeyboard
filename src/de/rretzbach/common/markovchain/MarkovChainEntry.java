package de.rretzbach.common.markovchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MarkovChainEntry {
	public Map<String, Integer> getOccurences() {
		return occurences;
	}

	private Map<String, Integer> occurences;

	public void addOccurence(String word) {
		if (occurences == null) {
			occurences = new HashMap<String, Integer>();
		}
		
		if (!occurences.containsKey(word)) {
			occurences.put(word, 0);
		}
		
		occurences.put(word, occurences.get(word) + 1);
	}

	public List<String> getSortedOccurences() {
		List<Entry<String, Integer>> foo = new ArrayList<Map.Entry<String,Integer>>();
		for (Entry<String, Integer> entry : occurences.entrySet()) {
			foo.add(entry);
		}
		
		Collections.sort(foo, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		List<String> sortedOccurences = new ArrayList<String>();
		for (Entry<String,Integer> entry : foo) {
			sortedOccurences.add(entry.getKey());
		}
		
		return sortedOccurences;
	}
}
