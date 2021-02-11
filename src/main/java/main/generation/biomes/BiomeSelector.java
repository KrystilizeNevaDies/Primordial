package main.generation.biomes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import ee.jjanno.libjsimplex.noise.gpu.SimplexNoiseGpu3D;
import main.config.biome.BiomeConfig;
import main.util.Pair;

public class BiomeSelector {
	private Map<UUID, BiomeConfig> biomes;
	private int temperatureNoiseSeed;
	private int humidityNoiseSeed;
	private int elevationNoiseSeed;
	private int vegetationNoiseSeed;
	
	/**
	 * Creates a BiomeSelecter using the specified biomes
	 * @param biomes
	 */
	public BiomeSelector(Map<String, BiomeConfig> biomes, Integer seed) {
		// Set noises
		
		// Temperary random used to generate seeds
		Random random = new Random();
		random.setSeed(seed);
		
		// Generate noise seeds
		this.temperatureNoiseSeed = random.nextInt();
		this.humidityNoiseSeed = random.nextInt();
		this.elevationNoiseSeed = random.nextInt();
		this.vegetationNoiseSeed = random.nextInt();
		
		// Convert biomes, give random UUIDs
		Map<UUID, BiomeConfig> biomeList = new HashMap<UUID, BiomeConfig>();
		
		biomes.forEach((name, config) -> {
			UUID noiseUUID = new UUID(random.nextLong(), random.nextLong());
			biomeList.put(noiseUUID, config);
		});
		
		this.biomes = biomeList;
	}
	
	public BiomeConfig[] getBiomes(int chunkX, int chunkZ) {
		
		float frequency = (float) 0.03;
		
		// Get noises
		float[] temperature = SimplexNoiseGpu3D.calculate(temperatureNoiseSeed + chunkX * frequency, 0, temperatureNoiseSeed + chunkZ * frequency, 4, 4, 64, frequency);
		float[] humidity = SimplexNoiseGpu3D.calculate(humidityNoiseSeed + chunkX * frequency, 0, humidityNoiseSeed + chunkZ * frequency, 4, 4, 64, frequency);
		float[] elevation = SimplexNoiseGpu3D.calculate(elevationNoiseSeed + chunkX * frequency, 0, elevationNoiseSeed + chunkZ * frequency, 4, 4, 64, frequency);
		float[] vegetation = SimplexNoiseGpu3D.calculate(vegetationNoiseSeed + chunkX * frequency, 0, vegetationNoiseSeed + chunkZ * frequency, 4, 4, 64, frequency);
		
		// return array
		BiomeConfig[] returnArray = new BiomeConfig[1024];
		
		for (int i = 0; i < returnArray.length; i++) {
			// Pair with match index and biomeconfig
			var match = new Pair<Double, BiomeConfig>(1.0, null);
			
			// Test each biome
			for (BiomeConfig biome : biomes.values()) {
				
				// Fit the biome to the properties
				double biomeFit = matchBiome(biome, temperature[i], humidity[i], elevation[i], vegetation[i]);
				
				// If biome is better then recorded, place into match
				if (biomeFit < match.getFirst() || match.getSecond() == null) {
					match.setFirst(biomeFit);
					match.setSecond(biome);
				}
			}
			
			// Set best match to array
			returnArray[i] = match.getSecond();
		}
		
		return returnArray;
	}
	/**
	 * Gets the best fitting biome for this chunk (slow)
	 * 
	 * @param chunkX
	 * @param chunkZ
	 * @return
	 */
	public BiomeConfig getBiome(int chunkX, int chunkZ) {
		
		float frequency = (float) 0.03;
		
		// Get noises
		float temperature = SimplexNoiseGpu3D.calculate(temperatureNoiseSeed + chunkX * frequency, 0, temperatureNoiseSeed + chunkZ * frequency, 1, 1, 1, frequency)[0];
		float humidity = SimplexNoiseGpu3D.calculate(humidityNoiseSeed + chunkX * frequency, 0, humidityNoiseSeed + chunkZ * frequency, 1, 1, 1, frequency)[0];
		float elevation = SimplexNoiseGpu3D.calculate(elevationNoiseSeed + chunkX * frequency, 0, elevationNoiseSeed + chunkZ * frequency, 1, 1, 1, frequency)[0];
		float vegetation = SimplexNoiseGpu3D.calculate(vegetationNoiseSeed + chunkX * frequency, 0, vegetationNoiseSeed + chunkZ * frequency, 1, 1, 1, frequency)[0];
		
		// Pair with match index and biomeconfig
		var match = new Pair<Double, BiomeConfig>(1.0, null);
		
		// Test each biome
		for (BiomeConfig biome : biomes.values()) {
			
			// Fit the biome to the properties
			double biomeFit = matchBiome(biome, temperature, humidity, elevation, vegetation);
			
			// If biome is better then recorded, place into match
			if (biomeFit < match.getFirst() || match.getSecond() == null) {
				match.setFirst(biomeFit);
				match.setSecond(biome);
			}
		}
		
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
