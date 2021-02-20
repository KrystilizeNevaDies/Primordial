package main.generation.biomes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import main.noise.PrimordialNoise;
import main.util.MathUtils;
import main.util.Pair;
import net.minestom.server.utils.chunk.ChunkUtils;

public class BiomeSelector {
	private Map<UUID, BiomeConfig> biomes;
	private Map<Long, Pair<double[][], BiomeConfig[][]>> biomeMap;
	private int temperatureNoiseSeed;
	private int humidityNoiseSeed;
	private int elevationNoiseSeed;
	private int vegetationNoiseSeed;
	private static final float frequency = (float) 0.01;
	
	/**
	 * Creates a BiomeSelecter using the specified biomes
	 * @param biomes
	 */
	public BiomeSelector(Map<String, BiomeConfig> biomes, Integer seed) {
		// Temporary random used to generate seeds
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
		
		// set biomeMap
		biomeMap = new HashMap<Long, Pair<double[][], BiomeConfig[][]>>();
		
	}
	
	public Pair<double[][], BiomeConfig[][]> getBiomes(int chunkX, int chunkZ) {
		
		long chunkIndex = ChunkUtils.getChunkIndex(chunkX, chunkZ);
		
		Pair<double[][], BiomeConfig[][]> saved = biomeMap.get(chunkIndex);
		
		if (saved != null)
			return saved;
		
		// Get noises
		float[] temperature = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency, temperatureNoiseSeed);
		float[] humidity = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency, humidityNoiseSeed);
		float[] elevation = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency, elevationNoiseSeed);
		float[] vegetation = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency, vegetationNoiseSeed);
		
		int arraySize = biomes.size();
		
		// return array
		BiomeConfig[][] returnBiomeArray = new BiomeConfig[256][arraySize];
		double[][] returnWeightArray = new double[256][arraySize];
		
		// Specify comparator
		Comparator<Pair<Double, BiomeConfig>> comparator = (first, second) -> {
			return (int) Math.signum(first.getFirst() - second.getFirst());
		};
		
		for (int i = 0; i < returnBiomeArray.length; i++) {
			// Pair with match index and biomeconfig
			List<Pair<Double, BiomeConfig>> biomeList = new ArrayList<Pair<Double, BiomeConfig>>();
			
			// Add each biome
			for (BiomeConfig biome : biomes.values()) {
				
				// Fit the biome to the properties
				double biomeFit = matchBiome(biome, temperature[i], humidity[i], elevation[i], vegetation[i]);
				
				// Add to arraylist
				biomeList.add(new Pair<Double, BiomeConfig>(biomeFit, biome));
			}
			
			// Sort array
			biomeList.sort(comparator);
			
			// Add biomes
			for (int j = 0; j < arraySize; j++) {
				returnWeightArray[i][j] = biomeList.get(j).getFirst();
				returnBiomeArray[i][j] = biomeList.get(j).getSecond();
			}
			
			// Normalise weightings
			MathUtils.normaliseArray(returnWeightArray[i]);
		}
		
		// Construct pair
		var returnPair = new Pair<double[][], BiomeConfig[][]>(returnWeightArray, returnBiomeArray);
		
		// Save config
		biomeMap.put(chunkIndex, returnPair);
		
		return returnPair;
	}
	
	/**
	 * Gets the best fitting biome for this chunk
	 * 
	 * @param chunkX
	 * @param chunkZ
	 * @return
	 */
	public Pair<double[], BiomeConfig[]> getBiome(int chunkX, int chunkZ) {
		
		long chunkIndex = ChunkUtils.getChunkIndex(chunkX, chunkZ);
		
		Pair<double[][], BiomeConfig[][]> saved = biomeMap.get(chunkIndex);
		
		if (saved == null) {
			saved = getBiomes(chunkX, chunkZ);
		}
		
		// Create new pair
		Pair<double[], BiomeConfig[]> returnPair = new Pair<double[], BiomeConfig[]>(saved.getFirst()[0], saved.getSecond()[0]); 
		
		return returnPair;
	}
	
	/**
	 * Matches the biome with the specified properties and returns a double above 0
	 * 0 being best fit, infinity being worst
	 */
	public static double matchBiome(BiomeConfig config, double temp, double humid, double elev, double vege) {
		// Temperature
		double temperatureDiff = Math.abs(config.getAverageTemperature() - temp);
		
		// Humidity
		double humidityDiff = Math.abs(config.getAverageHumidity() - temp);
		
		// Elevation
		double elevationDiff = Math.abs(config.getAverageElevation() - temp);
		
		// Vegetation
		double vegetationDiff = Math.abs(config.getAverageVegetation() - temp);
		
		// Total
		double total = (temperatureDiff + humidityDiff + elevationDiff + vegetationDiff + ((double) config.getRarity() * 0.01)) / 5.0;
		
		return total;
	}
}
