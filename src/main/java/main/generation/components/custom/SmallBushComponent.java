package main.generation.components.custom;

import java.util.List;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;

public class SmallBushComponent implements GenerationComponent {
	short trunk;
	short leaves;
	double rarity;
	
	public SmallBushComponent(double rarity, short trunk, short leaves) {
		this.trunk = trunk;
		this.leaves = leaves;
		this.rarity = rarity;
	}
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMTERRAINHEIGHT, rarity);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		// Leaves
		for (int posX = -2; posX < 3; posX++)
			for (int posZ = -2; posZ < 3; posZ++)
				for (int posY = 0; posY < 4; posY++) {
					if ((Math.hypot(posX, posZ) + Math.hypot(posZ, posY) + Math.hypot(posY, posX)) / 3.0 < 2) {
						blockIDs.add(leaves);
						arrayX.add(x + posX);
						arrayY.add(y + posY);
						arrayZ.add(z + posZ);
					}
				}
		
		
		// Stem
		for (int posY = 0; posY < 2; posY++) {
			blockIDs.add(trunk);
			arrayX.add(x);
			arrayY.add(y + posY);
			arrayZ.add(z);
		}
	}
}
