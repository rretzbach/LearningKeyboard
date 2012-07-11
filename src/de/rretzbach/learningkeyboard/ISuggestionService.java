package de.rretzbach.learningkeyboard;

import java.util.List;

public interface ISuggestionService {

	public abstract List<String> suggestNextWords(String lastWord,
			String partialWord);

}