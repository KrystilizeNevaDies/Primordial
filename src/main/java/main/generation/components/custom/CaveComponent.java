package main.generation.components.custom;

import java.util.List;
import java.util.Random;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;
import net.minestom.server.instance.block.Block;

public class CaveComponent implements GenerationComponent {
	double rarity;
	double caveSize;
	
	public CaveComponent(double rarity, double caveSize) {
		this.rarity = rarity;
		this.caveSize = caveSize;
	}
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMCHUNK, rarity);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		Random random = world.getRandom();
		/**
		int chunkMinX = x * 16;
		int chunkMinZ = z * 16;
		
		
		int initialX = chunkMinX + random.nextInt(16);
		int initialY = random.nextInt(256);
		int initialZ = chunkMinZ + random.nextInt(16);
		
		double vectorX = random.nextDouble() * 2.0 - 1.0;
		double vectorY = random.nextDouble() * 2.0 - 1.0;
		double vectorZ = random.nextDouble() * 2.0 - 1.0;
		
		while (random.nextDouble() > 0.05) {
			blockIDs.add(Block.AIR.getBlockId());
			arrayX.add(initialX);
			arrayY.add(initialY);
			arrayZ.add(initialZ);
		}
		
		
		blockIDs.add(Block.AIR.getBlockId());
		arrayX.add(chunkMinX + posX);
		arrayY.add((int) (noiseMap[posX][2][posZ] + offset));
		arrayZ.add(chunkMinZ + posZ);
		*/
	}
}
