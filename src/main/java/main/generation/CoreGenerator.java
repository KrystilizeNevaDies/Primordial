package main.generation;

import java.util.ArrayList;
import java.util.Random;

import main.generation.biomes.BiomeConfig;
import main.generation.biomes.BiomeSelector;
import main.noise.PrimordialNoise;
import main.util.BiomeUtils;
import main.util.MathUtils;
import main.util.Pair;
import main.world.PrimordialWorld;

/*
 * The main generation procedure
 * 
 * All methods are static for speed
 */
public class CoreGenerator {
	
	private static PrimordialWorld world;
	private static BiomeSelector selector;
	private static Random random;
	private static int seedHash;
	private static double frequency = 0.0005;
	
	public static void init(PrimordialWorld world, BiomeSelector selector) {
		CoreGenerator.world = world;
		CoreGenerator.selector = selector;
		CoreGenerator.random = new Random();
		CoreGenerator.seedHash = ((Integer) world.getSeed()).hashCode();
		random.setSeed(world.getSeed());
	}
	
	public static void generateChunk(int chunkX, int chunkZ) {
		// Create block group
		ArrayList<Short> blockIDs = new ArrayList<Short>();
		ArrayList<Integer> arrayX = new ArrayList<Integer>();
		ArrayList<Integer> arrayY = new ArrayList<Integer>();
		ArrayList<Integer> arrayZ = new ArrayList<Integer>();
		
		// Generate biomes
		Pair<double[][], BiomeConfig[][]> allBiomeConfigs = selector.getBiomes(chunkX, chunkZ);
		Pair<double[], BiomeConfig[]> biomeConfigs = selector.getBiome(chunkX, chunkZ);
		
		BiomeConfig primaryBiome = biomeConfigs.getSecond()[0];
		BiomeConfig secondaryBiome = biomeConfigs.getSecond()[1];
		double secondaryWeighting = biomeConfigs.getFirst()[1];
		
		// Prepare for main terrain generation
		int chunkXMin =  chunkX * 16;
		int chunkZMin =  chunkZ * 16;
		
		// Generate noise
		float[] primaryNoise = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency, seedHash);
		float[] secondaryNoise = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency * 2, seedHash * 2);
		float[] tertiaryNoise = PrimordialNoise.getCloverFractalNoise(chunkX, chunkZ, 16, 16, frequency * 4, seedHash * 3);
		double[][][] noiseMap = new double[16][2][16];
		
		// Main terrain generation
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				
				// Get biome info for this column
				int columnIndex = x + z * 16;
				
				Pair<double[], BiomeConfig[]> biomeMatch = BiomeUtils.getBiome(allBiomeConfigs, columnIndex);
				
				// Get elevation
				Pair<Double, Double> elevationFit = BiomeUtils.getElevation(biomeMatch);
				
				// Get height
				double[] noiseCollection = {primaryNoise[columnIndex], secondaryNoise[columnIndex], tertiaryNoise[columnIndex]};
				float noise = (float) MathUtils.weightedAverage(noiseCollection, new double[] {1.333, 1.0, 0.666}, 3);
				int height = heightAt(noise, elevationFit);
				
				int absoluteX = x + chunkXMin;
				int absoluteZ = z + chunkZMin;
				
				// Get column blocks
				short[] columnBlocks = CoreGenerator.blocksAt(columnIndex, allBiomeConfigs, height);
				
				// Insert column blocks
				for (int posY = 0; posY < columnBlocks.length; posY++) {
					
					blockIDs.add(columnBlocks[posY]);
					arrayX.add(absoluteX);
					arrayY.add(posY);
					arrayZ.add(absoluteZ);
				}
				
				// Set height in noiseMap
				noiseMap[x][1][z] = height;
				
				// Set noise for column
				noiseMap[x][0][z] = noise;
			}
		}
		                                                                                  
		// Do biome features
		primaryBiome.getComponents().forEach((component) -> {
			component.getActivation().activate(world, chunkX, chunkZ, noiseMap, 1.0, blockIDs, arrayX, arrayY, arrayZ, component::applyComponent);
		});
		
		if (secondaryWeighting > 0.8)
		secondaryBiome.getComponents().forEach((component) -> {
			component.getActivation().activate(world, chunkX, chunkZ, noiseMap, secondaryWeighting, blockIDs, arrayX, arrayY, arrayZ, component::applyComponent);
		});
		
		world.setBlockGroup(blockIDs, arrayX, arrayY, arrayZ);
	}
	
	private static int heightAt(float noise, Pair<Double, Double> elevation) {
		
		double height = elevation.getFirst() * 128;
		
		height += (noise * elevation.getSecond() * 256);
		
		return (int) height;
	}
	
	private static short[] blocksAt(int index, Pair<double[][], BiomeConfig[][]> allBiomeConfigs, double height) {
		
		double[][] fits = allBiomeConfigs.getFirst();
		BiomeConfig[][] configs = allBiomeConfigs.getSecond();
		
		short[] returnArray = new short[(int) height];
		
		for (int y = 0; y < height; y++) {
			// Set default block
			short block = configs[0][0].getTerrainBlocks().getSecond()[0];
			double fit = Integer.MAX_VALUE;
			
			// Iterate over all biomes and try to find better fit
			for (int i = 0; i < fits[0].length; i++) {
				
				// Get biome block data
				double biomeFit = fits[index][i];
				BiomeConfig biomeConfig = configs[index][i];
				Pair<double[], short[]> blocks = biomeConfig.getTerrainBlocks();
				double[] blockHeights = blocks.getFirst();
				short[] blockIDs = blocks.getSecond();
				
				// Iterate over all blocks and fit them to the height
				for (int j = 0; j < blockHeights.length; j++) {
					double blockHeight = blockHeights[j] * height;
					short blockID = blockIDs[j];
					
					// Fit block to current block
					double blockFit = (Math.abs(blockHeight - y) / height);
					if (blockFit < fit * biomeFit) {
						fit = blockFit;
						block = blockID;
					}
				}
			}
			
			// set block
			returnArray[y] = block;
		}
		
		return returnArray;
	}

	public PrimordialWorld getWorld() {
		return world;
	}

	public BiomeSelector getSelector() {
		return selector;
	}
}
