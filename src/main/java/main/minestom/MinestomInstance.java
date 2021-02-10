package main.minestom;

import java.util.UUID;

import main.generation.world.PrimordialChunk;
import main.generation.world.PrimordialWorld;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.BlockBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;

public class MinestomInstance extends InstanceContainer implements PrimordialWorld {
	
	public MinestomInstance(UUID uniqueId, DimensionType dimensionType) {
		super(uniqueId, dimensionType, null);
	}
	
	public PrimordialChunk getPrimordialChunk(int x, int z) {
		return new MinestomChunk(getChunk(x, z));
	}
	
	@Override
	public void setBlock(int x, int y, int z, short blockID) {
		setBlock(x, y, z, Block.fromStateId(blockID));
	}
	
	@Override
	public void setBlockGroup(int x, int y, int z, Short[] blockIDs, Integer[] arrayX, Integer[] arrayY, Integer[] arrayZ) {
		// Create BlockBatch
		BlockBatch batch = createBlockBatch();
		
		// Iterate through blocks and add to block batch
		for (int i = 0; i < blockIDs.length; i++) {
			batch.setBlockStateId(x + arrayX[i], y + arrayY[i], z + arrayZ[i], blockIDs[i]);
		}
		
		// Schedule flush block batch
		this.scheduleNextTick((instance) -> {
			batch.flush(null);
		});
	}

	@Override
	public void setBlockGroup(Short[] blockIDs, Integer[] arrayX, Integer[] arrayY, Integer[] arrayZ) {
		// Schedule everything to be done next tick
		this.scheduleNextTick((instance) -> {
			
			// Create BlockBatch
			BlockBatch batch = instance.createBlockBatch();
			
			int minX = arrayX[0];
			int minZ = arrayZ[0];
			int maxX = arrayX[0];
			int maxZ = arrayZ[0];
			
			// Iterate through blocks and add to block batch
			for (int i = 0; i < blockIDs.length; i++) {
				int x = arrayX[i];
				int y = arrayY[i];
				int z = arrayZ[i];
				
				// Get (min/max)s
				if (x < minX) minX = x;
				if (z < minZ) minZ = z;
				if (x > maxX) maxX = x;
				if (z > maxZ) maxZ = z;
				
				batch.setBlockStateId(x, y, z, blockIDs[i]);
			}
			
			System.out.println("DEBUG");
			
			// Ensure that necessary chunks are loaded:
			for (int chunkX = minX; chunkX < maxX; chunkX += 16)
				for (int chunkZ = minZ; chunkZ < maxZ; chunkZ += 16) {
					
					System.out.println(chunkX / 16 + ":" + chunkZ / 16);
					
					if (this.getChunk(chunkX / 16, chunkZ / 16) == null)
						this.loadChunk(chunkX / 16, chunkZ / 16, null);
				}
			
			// Flush
			batch.flush(null);
		});
	}
}