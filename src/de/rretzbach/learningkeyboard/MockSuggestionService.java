package de.rretzbach.learningkeyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rretzbach.learningkeyboard.dao.DBSuggestionService;
import de.rretzbach.learningkeyboard.dao.FollowersDAO;

public class MockSuggestionService extends DBSuggestionService {
	
	private Map<String, List<String>> mockedSuggestions;
	
	public MockSuggestionService(FollowersDAO followersDAO) {
		super(followersDAO);
		mockedSuggestions = new HashMap<String, List<String>>();
		mockedSuggestions.put("Ich", Arrays.asList("will", "meine", "habe", "kann"));
		mockedSuggestions.put("will", Arrays.asList("lieber", "heute", "eher", "schlafen"));
		mockedSuggestions.put("heute", Arrays.asList("klettern", "Abend", "nicht", "das"));
		mockedSuggestions.put("habe", Arrays.asList("dazu", "das", "ich", "wieder"));
		mockedSuggestions.put("das", Arrays.asList("Bier", "vergessen", "sieht", "weiﬂ"));
	}
	
	public MockSuggestionService() {
		super(null);
	}

	@Override
	protected List<ProbableWord> getFollowers(String lastWord) {
		List<ProbableWord> probableWordList = new ArrayList<ProbableWord>();
		List<String> list = mockedSuggestions.get(lastWord);
		for (String string :  list) {
			probableWordList.add(new ProbableWord(string, list.size() - list.indexOf(string)));
		}
		return probableWordList;
	}
}
