package main.generation.features.custom.components;

import java.util.ArrayList;
import java.util.List;

import main.generation.biomes.custom.stone.StoneBiome;
import main.generation.features.ComponentActivation;
import main.generation.features.ComponentType;
import main.generation.features.GenerationComponent;
import main.world.PrimordialWorld;

public class SquareTower implements GenerationComponent {
	
	private StoneBiome biomeConfig;

	/**
	 * @param biomeConfig
	 */
	public SquareTower(StoneBiome biomeConfig) {
		this.biomeConfig = biomeConfig;
	}

	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMCHUNK, 0.1);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, int[][] height) {
		List<Short> blockIDs = new ArrayList<Short>();
		List<Integer> arrayX = new ArrayList<Integer>();
		List<Integer> arrayY = new ArrayList<Integer>();
		List<Integer> arrayZ = new ArrayList<Integer>();
		
		var blocks = this.biomeConfig.getTerrainBlocks();
		
		int size = (int) (Math.random() * 80);
		
		int chunkXMid = x * 16 + 8;
		int chunkZMid = z * 16 + 8;
		
		// Main column
		for (int posY = 0; posY < size; posY++) {
			for (int posX = -5; posX < 5; posX++)
				for (int posZ = -5; posZ < 5; posZ++) {
					
					blockIDs.add(blocks.get(0));
					arrayX.add(posX);
					arrayY.add(posY);
					arrayZ.add(posZ);
				}
		}
		
		// Set blocks
		world.setBlockGroup(chunkXMid, 0, chunkZMid, blockIDs, arrayX, arrayY, arrayZ);
	}
}