package main.generation;

import java.util.ArrayList;

import ee.jjanno.libjsimplex.noise.cpu.SimplexNoiseCpu;
import main.world.PrimordialWorld;
import net.minestom.server.instance.block.Block;

public class CoreGenerator {
	
	private static PrimordialWorld world;
	
	public static void generateChunk(int chunkX, int chunkZ) {
		ArrayList<Short> blockIDs = new ArrayList<Short>();
		ArrayList<Integer> posX = new ArrayList<Integer>();
		ArrayList<Integer> posY = new ArrayList<Integer>();
		ArrayList<Integer> posZ = new ArrayList<Integer>();
		
		int chunkXMin =  chunkX * 16;
		int chunkZMin =  chunkZ * 16;
		
		
		float rarity = (float) 0.003;
		
		/// CPU:
		for (int x = chunkXMin; x < chunkXMin + 16; x++)
			for (int z = chunkZMin; z < chunkZMin + 16; z++) {
				
				float noise = (float) SimplexNoiseCpu.noise(x * rarity, z * rarity) + 1;
				
				noise *= 64;
				
				for (int y = 0; y < noise; y++) {
					blockIDs.add(Block.STONE.getBlockId());
					posX.add(x);
					posY.add(y);
					posZ.add(z );
				}
			}
		//*/
		
		/*// GPU:
		
		float[] noise = SimplexNoiseGpu2D.calculate(chunkXMin * rarity, chunkZMin * rarity, 16, 16, rarity);
		
		for (int x = 0; x < 16; x++)
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
				}
		//*/
		
		Short[] blockArray = blockIDs.toArray(new Short[blockIDs.size()]);
		Integer[] posXArray = posX.toArray(new Integer[posX.size()]);
		Integer[] posYArray = posY.toArray(new Integer[posY.size()]);
		Integer[] posZArray = posZ.toArray(new Integer[posZ.size()]);
		
		world.setBlockGroup(blockArray, posXArray, posYArray, posZArray);
	}

	public static PrimordialWorld getWorld() {
		return world;
	}

	public static void setWorld(PrimordialWorld world) {
		CoreGenerator.world = world;
	}
}
