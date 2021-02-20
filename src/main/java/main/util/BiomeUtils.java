package main.util;

import java.util.Arrays;

import main.generation.biomes.BiomeConfig;

public class BiomeUtils {
	
	public static Pair<Double, Double> getElevation(Pair<double[], BiomeConfig[]> biomeConfigs) {
		double[] weights = biomeConfigs.getFirst();
		BiomeConfig[] biomes = biomeConfigs.getSecond();
		
		double[] fixedElevations = new double[weights.length];
		double[] variableElevations = new double[weights.length];
		
		for (int i = 0; i < weights.length; i++) {
			var pair = biomes[i].getElevation();
			fixedElevations[i] = pair.getFirst() * Math.pow(weights[i], 0.75);
			variableElevations[i] = pair.getSecond() * Math.pow(weights[i], 3);
		}
		
		double fixedSum = Arrays.stream(fixedElevations).sum();
		double varSum = Arrays.stream(variableElevations).sum();
		
		double weightsSum = Arrays.stream(weights).sum();
		
		return new Pair<Double, Double>(fixedSum / weightsSum, varSum / weightsSum);
	}
	
	public static Pair<Double, Double> getVegetation(Pair<double[], BiomeConfig[]> biomeConfigs) {
		double[] weights = biomeConfigs.getFirst();
		BiomeConfig[] biomes = biomeConfigs.getSecond();
		
		double[] fixedElevations = new double[weights.length];
		double[] variableElevations = new double[weights.length];
		
		for (int i = 0; i < weights.length; i++) {
			var pair = biomes[i].getVegetation();
			fixedElevations[i] = pair.getFirst() * Math.pow(weights[i], 0.75);
			variableElevations[i] = pair.getSecond() * Math.pow(weights[i], 0.75);
		}
		
		double fixedSum = Arrays.stream(fixedElevations).sum();
		double varSum = Arrays.stream(variableElevations).sum();
		
		double weightsSum = Arrays.stream(weights).sum();
		
		return new Pair<Double, Double>(fixedSum / weightsSum, varSum / weightsSum);
	}
	
	public static Pair<Double, Double> getHumidity(Pair<double[], BiomeConfig[]> biomeConfigs) {
		double[] weights = biomeConfigs.getFirst();
		BiomeConfig[] biomes = biomeConfigs.getSecond();
		
		double[] fixedElevations = new double[weights.length];
		double[] variableElevations = new double[weights.length];
		
		for (int i = 0; i < weights.length; i++) {
			var pair = biomes[i].getHumidity();
			fixedElevations[i] = pair.getFirst() * Math.pow(weights[i], 0.75);
			variableElevations[i] = pair.getSecond() * Math.pow(weights[i], 0.75);
		}
		
		double fixedSum = Arrays.stream(fixedElevations).sum();
		double varSum = Arrays.stream(variableElevations).sum();
		
		double weightsSum = Arrays.stream(weights).sum();
		
		return new Pair<Double, Double>(fixedSum / weightsSum, varSum / weightsSum);
	}
	
	public static Pair<Double, Double> getTemperature(Pair<double[], BiomeConfig[]> biomeConfigs) {
		double[] weights = biomeConfigs.getFirst();
		BiomeConfig[] biomes = biomeConfigs.getSecond();
		
		double[] fixedElevations = new double[weights.length];
		double[] variableElevations = new double[weights.length];
		
		for (int i = 0; i < weights.length; i++) {
			var pair = biomes[i].getTemperature();
			fixedElevations[i] = pair.getFirst() * Math.pow(weights[i], 0.75);
			variableElevations[i] = pair.getSecond() * Math.pow(weights[i], 0.75);
		}
		
		double fixedSum = Arrays.stream(fixedElevations).sum();
		double varSum = Arrays.stream(variableElevations).sum();
		
		double weightsSum = Arrays.stream(weights).sum();
		
		return new Pair<Double, Double>(fixedSum / weightsSum, varSum / weightsSum);
	}

	public static Pair<double[], BiomeConfig[]> getBiome(Pair<double[][], BiomeConfig[][]> allBiomeConfigs, int i) {
		return new Pair<double[], BiomeConfig[]>(allBiomeConfigs.getFirst()[i], allBiomeConfigs.getSecond()[i]);
	}

	public static double fitColumnBlock(Pair<double[], short[]> terrainBlocks, int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
