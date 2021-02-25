package main.generation.components.custom;

import java.util.List;
import java.util.Random;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;

/**
 * This component is used for applications such as ore spawning and granite/andesite/diorite spawning
 * @author Krystilize
 *
 */
public class UndergroundBlockPatchComponent implements GenerationComponent {
	short[] blocks;
	double maxSize;
	
	public UndergroundBlockPatchComponent(double maxSize, short... blocks) {
		this.blocks = blocks;
		this.maxSize = maxSize;
	}

	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMTERRAINHEIGHT);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		Random random = world.getRandom();
		
		int chunkX = Math.abs(x % 16);
		int chunkZ = Math.abs(z % 16);
		
		double size = maxSize * random.nextDouble();
		
		int offsetY = (int) (noiseMap[chunkX][1][chunkZ] * random.nextDouble());
		
		short block = blocks[random.nextInt(blocks.length)];
		
		for (int posX = (int) -size; posX < size + 1.0; posX++)
			for (int posZ = (int) -size; posZ < size + 1.0; posZ++) {
				for (int posY = (int) -size; posY < size + 1.0; posY++) {
					blockIDs.add(block);
					arrayX.add(x + posX);
					arrayY.add(offsetY + posY);
					arrayZ.add(z + posZ);
				}
			}
	}
}
