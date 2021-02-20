package main.generation.biomes.custom.stone;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.custom.TerrainCubeComponent;
import main.generation.components.custom.UndergroundBlockPatchComponent;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

/**
 * A biome consisting of types of stone.
 * 
 * @author Krystilize
 */
public class AmplifiedStone implements BiomeConfig {
	
	@Override
	public Pair<Double, Double> getTemperature() {
		// Stones don't require a specific temperature
		return new Pair<Double, Double>(0.0, 1.0);
	}

	@Override
	public Pair<Double, Double> getHumidity() {
		// Pure rock is pretty low humidity
		return new Pair<Double, Double>(0.0, 0.1);
	}

	@Override
	public Pair<Double, Double> getElevation() {
		// Medium elevation with high range
		return new Pair<Double, Double>(0.45, 0.8);
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Small vegetation as rock
		return new Pair<Double, Double>(0.0, 0.1);
	}

	@Override
	public Integer getRarity() {
		// Should not be very rare
		return 50;
	}

	@Override
	public Pair<double[], short[]> getTerrainBlocks() {
		// Only stones
		double[] weightings = {
			1.0,
			0.8
		};
		short[] blockIDs = {
			Block.STONE.getBlockId(),
			Block.COBBLESTONE.getBlockId()
		}; 
		return new Pair<double[], short[]>(weightings, blockIDs);
	}

	@Override
	public int getFogColor() {
		return 0xC0D8FF;
	}

	@Override
	public int getSkyColor() {
		return 0x78A7FF;
	}

	@Override
	public int getWaterColor() {
		return 0x3F76E4;
	}

	@Override
	public int getWaterFogColor() {
		return 0x50533;
	}

	@Override
	public List<GenerationComponent> getComponents() {
		return List.of(
			new TerrainCubeComponent(2, 0.005, Block.POLISHED_BLACKSTONE_BRICKS.getBlockId()),
			new UndergroundBlockPatchComponent(0.1, Block.DIORITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.1, Block.ANDESITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.1, Block.GRANITE.getBlockId())
		);
	}
}
