package main.generation.components.custom;

import java.util.List;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.util.MathUtils;
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
		
		double size = 3 + (world.getRandom().nextDouble() * 2);
		
		// Leaves
		for (int posX = (int) -size; posX < size; posX++)
			for (int posZ = (int) -size; posZ < size; posZ++)
				for (int posY = (int) -size; posY < size * 1.5; posY++) {
					if (MathUtils.powLength(2, posX, posY, posZ) < size) {
						blockIDs.add(leaves);
						arrayX.add(x + posX);
						arrayY.add((int) (y + posY + size * 2));
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
