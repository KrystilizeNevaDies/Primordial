package main.util;

public class MathUtils {
	public static void clampHighest(double[] array) {
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
	
	public static double[] normalize3D(double[] array) {
		return normalize3D(array[0], array[1], array[2]);
	}
	
	public static double[] normalize3D(double x, double y, double z) {
		// Get length
		double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		
		// Return all values divided by length
		return new double[]{x / length, y / length, z / length};
	}

	public static float weightedAverage(float[] array, float[] weights, int length) {
		double total = 0;
		for (int i = 0; i < length; i++) {
			total += weights[i] * array[i];
		}
		return (float) (total / (double) length);
	}
	
	public static double weightedAverage(double[] array, double[] weights, int length) {
		double total = 0;
		for (int i = 0; i < length; i++) {
			total += weights[i] * array[i];
		}
		return (total / (double) length);
	}

	public static double squareLength(double... values) {
		return powLength(2, values);
	}
	
	public static double powLength(double pow, double... values) {
		// Get squared length
		double total = 0;
		for (double num : values)
			total += Math.pow(Math.abs(num), pow);
		
		// Get root of squared length
		return Math.pow(total, 1.0 / pow);
	}
}
