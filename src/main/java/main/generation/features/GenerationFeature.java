package main.generation.features;

import main.generation.world.PrimordialWorld;

public interface GenerationFeature {
	/**
	 * Called when a chunk has been scheduled to generate
	 * @param world the world to generate in 
	 * @param chunkX the chunk that has been generated
	 * @param chunkZ the chunk that has been generated
	 */
	public void onNewChunk(PrimordialWorld world, int chunkX, int chunkZ);
}
