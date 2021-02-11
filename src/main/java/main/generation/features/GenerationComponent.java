package main.generation.features;

import main.world.PrimordialWorld;

public interface GenerationComponent {
	public ComponentActivation getActivation();
	
	/**
	 * Applies this component to the specified world at the specified absolute coords.
	 * <br><br>
	 * The specified coords depend on the type of activation:
	 * <br>
	 * RANDOMCHUNK - chunkX, 0, chunkZ<br>
	 * RANDOMPOSITION - posX, posY, posZ<br>
	 * RANDOMTERRAINHEIGHT - posX, posY, posZ<br>
	 * <br>
	 * ALLCHUNK - chunkX, 0, chunkZ<br>
	 * ALLPOSITION - posX, posY, posZ<br>
	 * ALLTERRAINHEIGHT - posX, posY, posZ<br>
	 * 
	 * @param world
	 * @param coords
	 */
	public void applyComponent(PrimordialWorld world, int x, int y, int z, int[][] height);
}
