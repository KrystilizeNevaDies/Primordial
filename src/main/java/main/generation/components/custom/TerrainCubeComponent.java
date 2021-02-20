package main.generation.components.custom;

import java.util.List;
import java.util.Random;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;

public class TerrainCubeComponent implements GenerationComponent {

	short[] blocks;
	double size;
	double rarity;
	
	public TerrainCubeComponent(double size, double rarity, short... blocks) {
		this.size = size;
		this.blocks = blocks;
		this.rarity = rarity;
	}
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMTERRAINHEIGHT, rarity);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		Random random = world.getRandom();
		
		// Get height
		for (int posX = (int) -size; posX < size + 1; posX++)
			for (int posZ = (int) -size; posZ < size + 1; posZ++)
				for (int posY = (int) -size; posY < size + 1; posY++) {
					blockIDs.add(blocks[random.nextInt(blocks.length)]);
					arrayX.add(posX + x);
					arrayY.add(posY + y);
					arrayZ.add(posZ + z);
				}

	}
}