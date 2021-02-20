package main.generation.components.custom;

import java.util.List;
import java.util.Random;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;

public class SquareTowerComponent implements GenerationComponent {
	short[] blocks;
	
	/**
	 * @param config
	 */
	public SquareTowerComponent(short... blocks) {
		this.blocks = blocks;
	}

	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMCHUNK, 0.1);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		Random random = world.getRandom();
		
		int size = (int) (64 * random.nextDouble());
		
		int chunkXMid = (int) (x * 16 + (16 * random.nextDouble()));
		int chunkZMid = (int) (z * 16 + (16 * random.nextDouble()));
		
		// Main column
		for (int posY = 0; posY < size; posY++) {
			for (int posX = -5; posX < 5; posX++)
				for (int posZ = -5; posZ < 5; posZ++) {
					
					blockIDs.add(blocks[0]);
					arrayX.add(chunkXMid + posX);
					arrayY.add(posY);
					arrayZ.add(chunkZMid + posZ);
				}
		}
	}
}