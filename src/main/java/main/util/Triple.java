package main.util;

public class Triple<A, B, C> {
	A first;
	B second;
	private C third;
	
	public Triple(A first, B second, C third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public A getFirst() {
		return first;
	}
	
	public B getSecond() {
		return second;
	}

	public void setFirst(A newValue) {
		this.first = newValue;
	}

	public void setSecond(B newValue) {
		this.second = newValue;
	}

	public C getThird() {
		return third;
	}

	public void setThird(C third) {
		this.third = third;
	}
}
