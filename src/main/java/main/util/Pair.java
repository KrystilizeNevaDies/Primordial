package main.util;

public class Pair<F, S> {
	F first;
	S second;
	
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}
	
	public F getFirst() {
		return first;
	}
	
	public S getSecond() {
		return second;
	}

	public void setFirst(F newValue) {
		this.first = newValue;
	}

	public void setSecond(S newValue) {
		this.second = newValue;
	}
}
