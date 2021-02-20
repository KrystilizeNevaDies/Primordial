package main.minestom;

import java.util.Random;
import java.util.UUID;

import main.world.PrimordialWorld;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;

public class MinestomInstance extends InstanceContainer implements PrimordialWorld {
	
	private int seed;
	private Random random;
	
	public MinestomInstance(UUID uniqueId, DimensionType dimensionType, int seed) {
		super(uniqueId, dimensionType, null);
		this.seed = seed;
		this.random = new Random();
		
		random.setSeed(seed);
	}
	
	@Override
	public int getSeed() {
		return seed;
	}
	
	@Override
	public void setBlock(int x, int y, int z, short blockID) {
		setBlock(x, y, z, Block.fromStateId(blockID));
	}
	
	@Override
	public void setBlockGroup(int x, int y, int z, short[] blockIDs, int[] arrayX, int[] arrayY, int[] arrayZ) {
		// Create BlockBatch
		CustomBatch batch = new CustomBatch(this);
		
		// Check for zero blocks
		if (blockIDs.length == 0)
			return;
		
		// Iterate through blocks and add to block batch
		for (int i = 0; i < blockIDs.length; i++) {
			batch.setBlockStateId(arrayX[i] + x, arrayY[i] + y, arrayZ[i] + z, blockIDs[i]);
		}
		
		// Apply all blocks
		batch.flush(null);
	}

	@Override
	public void setBlockGroup(short[] blockIDs, int[] arrayX, int[] arrayY, int[] arrayZ) {
		// Create BlockBatch
		CustomBatch batch = new CustomBatch(this);
		
		// Check for zero blocks
		if (blockIDs.length == 0)
			return;
		
		// Iterate through blocks and add to block batch
		for (int i = 0; i < blockIDs.length; i++) {
			batch.setBlockStateId(arrayX[i], arrayY[i], arrayZ[i], blockIDs[i]);
		}
		
		// Apply all blocks
		batch.flush(null);
	}

	@Override
	public Random getRandom() {
		return random;
	}
}