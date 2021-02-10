package main.generation;

import java.util.ArrayList;

import ee.jjanno.libjsimplex.noise.cpu.SimplexNoiseCpu;
import main.generation.world.PrimordialWorld;
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
		
		for (int x = chunkXMin; x < chunkXMin + 16; x++)
				for (int z = chunkZMin; z < chunkZMin + 16; z++) {
					double noise = SimplexNoiseCpu.noise(x, z);
					
					noise *= 5;
					
					for (int y = 0; y < noise; y++) {
						blockIDs.add(Block.STONE.getBlockId());
						posX.add(x);
						posY.add(y);
						posZ.add(z);
					}
				}
		
		
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
