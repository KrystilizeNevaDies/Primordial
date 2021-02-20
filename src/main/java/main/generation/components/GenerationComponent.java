package main.generation.components;

import java.util.List;

import main.world.PrimordialWorld;

public interface GenerationComponent {
	public ComponentActivation getActivation();
	
	/**
	 * Applies this component to the specified world at the specified absolute coords.
	 * <br><br>
	 * The specified coords depend on the type of activation:
	 * <br>
	 * RANDOMCHUNK - chunkX, 0, chunkZ<br>
	 * RANDOMTERRAINHEIGHT - posX, posY, posZ<br>
	 * <br>
	 * ALLCHUNK - chunkX, 0, chunkZ<br>
	 */
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting, List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ);
}
