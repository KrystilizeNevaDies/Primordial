package main.noise;

import java.util.HashMap;
import java.util.Map;

public class PrimordialNoise {
	
	private static Map<Integer, CloverNoise.Noise2D> cloverNoises = new HashMap<Integer, CloverNoise.Noise2D>();
	
	public static float[] getCloverNoise(double x, double z, int width, int height, float frequency, int seed) {
		CloverNoise.Noise2D noise = cloverNoises.get(seed);
		
		if (noise == null) {
			noise = new CloverNoise.Noise2D(seed);
			cloverNoises.put(seed, noise);
		}
		
		float[] returnArray = new float[height * width];
		
		for (int offX = 0; offX < width; offX++)
			for (int offZ = 0; offZ < height; offZ++) {
				returnArray[offX + offZ * height] = (float) getCloverNoise((x * width) + offX, (z * height) + offZ, frequency, noise);
			}
		
		return returnArray;
	}
	
	private static double getCloverNoise(double x, double z, double frequency, CloverNoise.Noise2D noise) {
		return noise.noise(x * frequency, z * frequency);
	}
	
	public static float[] getCloverFrostNoise(double x, double z, int width, int height, float frequency, int seed) {
		CloverNoise.Noise2D noise = cloverNoises.get(seed);
		
		if (noise == null) {
			noise = new CloverNoise.Noise2D(seed);
			cloverNoises.put(seed, noise);
		}
		
		float[] returnArray = new float[height * width];
		
		for (int offX = 0; offX < width; offX++)
			for (int offZ = 0; offZ < height; offZ++) {
				returnArray[offX + offZ * height] = (float) getCloverFrostNoise((x * width) + offX, (z * height) + offZ, frequency, noise);
			}
		
		return returnArray;
	}
	
	private static double getCloverFrostNoise(double x, double z, double frequency, CloverNoise.Noise2D noise) {
		return noise.frostNoise(x * frequency, z * frequency);
	}
	
	public static float[] getCloverFractalNoise(double x, double z, int width, int height, double frequency, int seed) {
		CloverNoise.Noise2D noise = cloverNoises.get(seed);
		
		if (noise == null) {
			noise = new CloverNoise.Noise2D(seed);
			cloverNoises.put(seed, noise);
		}
		
		float[] returnArray = new float[height * width];
		
		for (int offX = 0; offX < width; offX++)
			for (int offZ = 0; offZ < height; offZ++) {
				returnArray[offX + offZ * height] = (float) getCloverFractalNoise((x * width) + offX, (z * height) + offZ, frequency, noise);
			}
		
		return returnArray;
	}
	
	private static double getCloverFractalNoise(double x, double z, double frequency, CloverNoise.Noise2D noise) {
		return noise.fractalNoise(x * frequency, z * frequency, 4);
	}
	
	public static float[] getCloverMarbleNoise(double x, double z, int width, int height, float frequency, int seed) {
		CloverNoise.Noise2D noise = cloverNoises.get(seed);
		
		if (noise == null) {
			noise = new CloverNoise.Noise2D(seed);
			cloverNoises.put(seed, noise);
		}
		
		float[] returnArray = new float[height * width];
		
		for (int offX = 0; offX < width; offX++)
			for (int offZ = 0; offZ < height; offZ++) {
				returnArray[offX + offZ * height] = (float) getCloverMarbleNoise((x * width) + offX, (z * height) + offZ, frequency, noise);
			}
		
		return returnArray;
	}
	
	private static double getCloverMarbleNoise(double x, double z, double frequency, CloverNoise.Noise2D noise) {
		return noise.marbleNoise(x * frequency, z * frequency);
	}
}