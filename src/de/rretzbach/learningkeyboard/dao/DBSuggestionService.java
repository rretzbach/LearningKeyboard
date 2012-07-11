package de.rretzbach.learningkeyboard.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import de.rretzbach.learningkeyboard.ISuggestionService;
import de.rretzbach.learningkeyboard.ProbableWord;

public class DBSuggestionService implements ISuggestionService {

	private int maximumSuggestions = 5;
	private final FollowersDAO followersDAO;

	public DBSuggestionService(FollowersDAO followersDAO) {
		this.followersDAO = followersDAO;
	}

	@Override
	public List<String> suggestNextWords(String lastWord,
			final String partialInput) {
		List<ProbableWord> suggestions = new ArrayList<ProbableWord>();
		List<ProbableWord> followers = getFollowers(lastWord);
		if (followers != null) {
			suggestions.addAll(followers);
		}
		for (ProbableWord completingWord : getCompletions(partialInput)) {
			if (!suggestions.contains(completingWord)) {
				suggestions.add(completingWord);
			}
		}
		if (!suggestions.isEmpty()) {
			Collections.sort(suggestions, new Comparator<ProbableWord>() {
				@Override
				public int compare(ProbableWord lhs, ProbableWord rhs) {
					boolean lhsMatches = startsWithCaseInsensitive(
							lhs.getWord(), partialInput);
					boolean rhsMatches = startsWithCaseInsensitive(
							rhs.getWord(), partialInput);
					if (lhsMatches && rhsMatches) {
						return -1
								* lhs.getOccurence().compareTo(
										rhs.getOccurence());
					}
					if (!lhsMatches && rhsMatches) {
						return 1;
					}
					if (lhsMatches && !rhsMatches) {
						return -1;
					}
					if (!lhsMatches && !rhsMatches) {
						return -1
								* lhs.getOccurence().compareTo(
										rhs.getOccurence());
					}
					return 0;
				}
			});

			if (suggestions.size() > maximumSuggestions) {
				suggestions = suggestions.subList(0, maximumSuggestions);
			}
		}

		List<String> simpleSuggestions = new ArrayList<String>();
		for (ProbableWord probableWord : suggestions) {
			simpleSuggestions.add(probableWord.getWord());
		}
		return simpleSuggestions;
	}

	protected List<ProbableWord> getCompletions(String partialInput) {
		List<String> completions = followersDAO.findPartialWord(partialInput);
		List<ProbableWord> probableCompletions = new ArrayList<ProbableWord>();
		if (completions != null && !completions.isEmpty()) {
			for (String word : completions) {
				probableCompletions.add(new ProbableWord(word, 0));
			}
		}
		return probableCompletions;
	}

	protected List<ProbableWord> getFollowers(String lastWord) {
		return followersDAO.find(lastWord);
	}

	protected boolean startsWithCaseInsensitive(String charSequence,
			String partialInput) {
		Pattern partialWordPattern = Pattern.compile("^(?i)"
				+ Pattern.quote(partialInput));
		Matcher m = partialWordPattern.matcher(charSequence);
		if (m.find()) {
			return true;
		}

		return false;
	}
}
