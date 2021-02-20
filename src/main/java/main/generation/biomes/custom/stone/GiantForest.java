package main.generation.biomes.custom.stone;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.custom.LargeDarkOakTreeComponent;
import main.generation.components.custom.SmallBushComponent;
import main.generation.components.custom.UndergroundBlockPatchComponent;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

/**
 * A biome consisting of types of stone.
 * 
 * @author Krystilize
 */
public class GiantForest implements BiomeConfig {
	
	@Override
	public Pair<Double, Double> getTemperature() {
		// Large trees don't require specific temps
		return new Pair<Double, Double>(0.0, 1.0);
	}

	@Override
	public Pair<Double, Double> getHumidity() {
		// Pretty high humidity
		return new Pair<Double, Double>(0.6, 0.9);
	}

	@Override
	public Pair<Double, Double> getElevation() {
		// Medium elevation with high range
		return new Pair<Double, Double>(0.45, 0.9);
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Medium Vegetation
		return new Pair<Double, Double>(0.4, 0.6);
	}

	@Override
	public Integer getRarity() {
		// Decently rare
		return 50;
	}

	@Override
	public Pair<double[], short[]> getTerrainBlocks() {
		// A standard range of blocks
		double[] weightings = {
			1.0,
			0.95,
			0.90,
			0.85,
			0.80,
			0.0,
		};
		
		short[] blockIDs = {
			Block.GRASS_BLOCK.getBlockId(),
			Block.DIRT.getBlockId(),
			Block.STONE.getBlockId(),
			Block.BLACKSTONE.getBlockId(),
			Block.STONE.getBlockId(),
			Block.BEDROCK.getBlockId(),
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
			new LargeDarkOakTreeComponent(0.1),
			new SmallBushComponent(0.01, Block.BIRCH_WOOD.getBlockId(), Block.JUNGLE_LEAVES.getBlockId()),
			new UndergroundBlockPatchComponent(0.05, Block.DIORITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.05, Block.ANDESITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.05, Block.GRANITE.getBlockId())
		);
	}
}
