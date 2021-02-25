package main.generation.components.custom;

import java.util.List;
import java.util.Random;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;
import net.minestom.server.instance.block.Block;

public class BedrockComponent implements GenerationComponent {
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.ALLCHUNK);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		Random random = world.getRandom();
		
		int chunkMinX = x * 16;
		int chunkMinZ = z * 16;
		
		for (int posX = 0; posX < 16; posX++)
			for (int posZ = 0; posZ < 16; posZ++) {
				
				int height = random.nextInt(3) + 2;
		
				for (int posY = 0; posY < height; posY++) {
					blockIDs.add(Block.BEDROCK.getBlockId());
					arrayX.add(chunkMinX + posX);
					arrayY.add(posY);
					arrayZ.add(chunkMinZ + posZ);
				}
			}
	}
}
