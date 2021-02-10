package main.generation.biomes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import de.articdive.jnoise.JNoise;
import main.config.biome.BiomeConfig;
import main.util.Pair;

public class BiomeSelector {
	private Map<UUID, BiomeConfig> biomes;
	private JNoise temperatureNoise;
	private JNoise humidityNoise;
	private JNoise elevationNoise;
	private JNoise vegetationNoise;
	
	/**
	 * Creates a BiomeSelecter using the specified biomes
	 * @param biomes
	 */
	public BiomeSelector(Map<String, BiomeConfig> biomes, Integer seed) {
		// Set noises
		
		// Temperary random used to generate seeds
		Random random = new Random();
		random.setSeed(seed);
		
		
		
		this.temperatureNoise = JNoise.newBuilder().openSimplex().setFrequency(0.5).setSeed(random.nextInt()).build();
		this.humidityNoise = JNoise.newBuilder().openSimplex().setFrequency(0.5).setSeed(random.nextInt()).build();
		this.elevationNoise = JNoise.newBuilder().openSimplex().setFrequency(0.5).setSeed(random.nextInt()).build();
		this.vegetationNoise = JNoise.newBuilder().openSimplex().setFrequency(0.5).setSeed(random.nextInt()).build();
		
		// Convert biomes, give random UUIDs
		Map<UUID, BiomeConfig> biomeList = new HashMap<UUID, BiomeConfig>();
		
		biomes.forEach((name, config) -> {
			UUID noiseUUID = new UUID(random.nextLong(), random.nextLong());
			biomeList.put(noiseUUID, config);
		});
		
		this.biomes = biomeList;
	}
	
	public BiomeConfig getBiome(int x, int y, int z) {
		// Get noises
		double temperature = temperatureNoise.getNoise(x, y, z);
		double humidity = humidityNoise.getNoise(x, y, z);
		double elevation = elevationNoise.getNoise(x, y, z);
		double vegetation = vegetationNoise.getNoise(x, y, z);
		
		// Pair with match index and biomeconfig
		var match = new Pair<Double, BiomeConfig>(1.0, null);
		
		// Find best biome
		biomes.forEach((uuid, biome) -> {
			
			// Fit the biome to the properties
			double biomeFit = matchBiome(biome, temperature, humidity, elevation, vegetation);
			
			// If biome is better then recorded, place into match
			if (biomeFit < match.getFirst() || match.getSecond() == null) {
				match.setFirst(biomeFit);
				match.setSecond(biome);
			}
		});
		
		// Return best biome
		return match.getSecond();
	}
	
	/**
	 * Matches the biome with the specified properties and returns a double between 0 and 1
	 * 0 being best fit, 1 being worst.
	 */
	public static double matchBiome(BiomeConfig config, double temp, double humid, double elev, double vege) {
		// Temperature
		Pair<Double, Double> temperatureRange = config.getTemperature();
		double temperatureMid = ((temperatureRange.getFirst() + temperatureRange.getSecond()) / 2); 
		double temperatureDiff = Math.abs(temperatureMid - temp);
		if (temp > temperatureRange.getFirst() && temp < temperatureRange.getSecond()) temperatureDiff = Math.pow(temperatureDiff, 0.5);
		
		// Humidity
		Pair<Double, Double> humidityRange = config.getTemperature();
		double humidityMid = ((humidityRange.getFirst() + humidityRange.getSecond()) / 2); 
		double humidityDiff = Math.abs(humidityMid - temp);
		if (temp > humidityRange.getFirst() && temp < humidityRange.getSecond()) humidityDiff = Math.pow(humidityDiff, 0.5);
		
		// Elevation
		Pair<Double, Double> elevationRange = config.getTemperature();
		double elevationMid = ((elevationRange.getFirst() + elevationRange.getSecond()) / 2); 
		double elevationDiff = Math.abs(elevationMid - temp);
		if (temp > elevationRange.getFirst() && temp < elevationRange.getSecond()) elevationDiff = Math.pow(elevationDiff, 0.5);
		
		// Vegetation
		Pair<Double, Double> vegetationRange = config.getTemperature();
		double vegetationMid = ((vegetationRange.getFirst() + vegetationRange.getSecond()) / 2); 
		double vegetationDiff = Math.abs(vegetationMid - temp);
		if (temp > vegetationRange.getFirst() && temp < vegetationRange.getSecond()) vegetationDiff = Math.pow(vegetationDiff, 0.5);
		
		// Average of all
		return (temperatureDiff + humidityDiff + elevationDiff + vegetationDiff) / 4;
	}
}
