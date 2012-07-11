package de.rretzbach.learningkeyboard.dao;

public class Follower {
	private String lastword;
	private String follower;
	private Integer frequency;

	public Follower(String lastword, String follower, Integer frequency) {
		this.lastword = lastword;
		this.follower = follower;
		this.frequency = frequency;
	}

	public String getLastword() {
		return lastword;
	}

	public void setLastword(String lastword) {
		this.lastword = lastword;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((follower == null) ? 0 : follower.hashCode());
		result = prime * result
				+ ((lastword == null) ? 0 : lastword.hashCode());
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
		Follower other = (Follower) obj;
		if (follower == null) {
			if (other.follower != null)
				return false;
		} else if (!follower.equals(other.follower))
			return false;
		if (lastword == null) {
			if (other.lastword != null)
				return false;
		} else if (!lastword.equals(other.lastword))
			return false;
		return true;
	}
}
