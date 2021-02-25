package main.generation.components.experimental;

import java.util.List;

import de.articdive.jnoise.JNoise;
import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.world.PrimordialWorld;
import net.minestom.server.instance.block.Block;

public class NoiseComponent implements GenerationComponent {
	
	private JNoise noise;
	
	public NoiseComponent() {
		noise = JNoise.newBuilder()
			.openSimplex()
			.setFrequency(0.1)
			.setSeed(123456789)
			.build();
	}
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.ALLCHUNK);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		int chunkMinX = x * 16;
		int chunkMinZ = z * 16;
		
		for (int posX = 0; posX < 16; posX++)
			for (int posZ = 0; posZ < 16; posZ++) {
				
				int height = (int) noiseMap[posX][1][posZ];
		
				for (int posY = 0; posY < height; posY++) {
					double noiseValue = noise.getNoise(chunkMinX + posX, posY, chunkMinZ + posZ);
					
					if (noiseValue > -0.1 + (1.0 - noiseMap[posX][0][posZ])) {
						blockIDs.add(Block.AIR.getBlockId());
						arrayX.add(chunkMinX + posX);
						arrayY.add(posY);
						arrayZ.add(chunkMinZ + posZ);
					}
				}
			}
	}
}