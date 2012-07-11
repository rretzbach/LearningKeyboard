package de.rretzbach.learningkeyboard;

public class ProbableWord {
	@Override
	public String toString() {
		return "ProbableWord [word=" + word + ", occurence=" + occurence + "]";
	}

	private String word;
	private Integer occurence;

	public ProbableWord(String word, int occurence) {
		this.word = word;
		this.occurence = occurence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProbableWord other = (ProbableWord) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getOccurence() {
		return occurence;
	}

	public void setOccurence(Integer occurence) {
		this.occurence = occurence;
	}
}
