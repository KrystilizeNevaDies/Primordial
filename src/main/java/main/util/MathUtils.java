package main.util;

public class MathUtils {
	public static void normaliseArray(double[] array) {
		// Find highest
		double highest = 0;
		
		for (double num : array)
			if (num > highest)
				highest = num;
		
		// Divide all by highest
		for (int i = 0; i < array.length; i++) {
			array[i] /= highest;
		}
	}
}
