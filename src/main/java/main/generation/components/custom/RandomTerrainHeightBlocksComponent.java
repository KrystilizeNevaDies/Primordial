package main.generation.components.custom;

import java.util.List;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;

public class RandomTerrainHeightBlocksComponent implements GenerationComponent {
	short block;
	int offset;
	double rarity;
	
	public RandomTerrainHeightBlocksComponent(int offset, double rarity, short block) {
		this.block = block;
		this.offset = offset;
		this.rarity = rarity;
	}
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMTERRAINHEIGHT, rarity);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		blockIDs.add(block);
		arrayX.add(x);
		arrayY.add(y);
		arrayZ.add(z);
	}
}
