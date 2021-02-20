package main.generation.components.custom;

import java.util.List;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;

public class SmallTreeComponent implements GenerationComponent {
	short trunk;
	short leaves;
	double rarity;
	
	public SmallTreeComponent(double rarity, short trunk, short leaves) {
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
		
		double size = 3;
		
		// Leaves
		for (int posX = (int) (-size * 1.5); posX < size * 1.5; posX++)
			for (int posZ = (int) (-size * 1.5); posZ < size * 1.5; posZ++)
				for (int posY = (int) size; posY < size * 3; posY++) {
					if ((Math.hypot(posX, posZ) + Math.hypot(posZ, posY) + Math.hypot(posY, posX)) / 3.0 < size * 1.5) {
						blockIDs.add(leaves);
						arrayX.add(x + posX);
						arrayY.add(y + posY);
						arrayZ.add(z + posZ);
					}
				}
		
		
		// Stem
		for (int posY = 0; posY < size * 1.5; posY++) {
			blockIDs.add(trunk);
			arrayX.add(x);
			arrayY.add(y + posY);
			arrayZ.add(z);
		}
	}
}
