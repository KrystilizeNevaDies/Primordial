package main.generation;

import java.util.ArrayList;

import ee.jjanno.libjsimplex.noise.gpu.SimplexNoiseGpu2D;
import main.generation.biomes.BiomeSelector;
import main.world.PrimordialWorld;
import net.minestom.server.instance.block.Block;

public class CoreGenerator {
	
	private PrimordialWorld world;
	private BiomeSelector selector;
	
	public CoreGenerator(PrimordialWorld world, BiomeSelector selector) {
		this.world = world;
		this.selector = selector;
	}
	
	public void generateChunk(int chunkX, int chunkZ) {
		ArrayList<Short> blockIDs = new ArrayList<Short>();
		ArrayList<Integer> posX = new ArrayList<Integer>();
		ArrayList<Integer> posY = new ArrayList<Integer>();
		ArrayList<Integer> posZ = new ArrayList<Integer>();
		
		int chunkXMin =  chunkX * 16;
		int chunkZMin =  chunkZ * 16;
		
		
		float frequency = (float) 0.003;
		
		float[] noise = SimplexNoiseGpu2D.calculate(chunkXMin * frequency, chunkZMin * frequency, 16, 16, frequency);
		
		int[][] height = new int[16][16];
		
		for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					
					int i = x + z*16;
					
					noise[i] += 1;
					noise[i] *= 64;
					
					for (int y = 0; y < noise[i]; y++) {
						blockIDs.add(Block.STONE.getBlockId());
						posX.add(x + chunkXMin);
						posY.add(y);
						posZ.add(z + chunkZMin);
					}
					
					height[x][z] = (int) noise[i];
				}
		}
		
		world.setBlockGroup(blockIDs, posX, posY, posZ);
		
		// Do biome features
		selector.getBiome(chunkX, chunkZ).getComponents().forEach((component) -> {
			component.getActivation().activate(world, chunkX, chunkZ, height, component::applyComponent);
		});
	}

	public PrimordialWorld getWorld() {
		return world;
	}

	public BiomeSelector getSelector() {
		return selector;
	}
}
