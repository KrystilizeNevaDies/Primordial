package main.generation.biomes.custom.stone;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.custom.RandomTerrainHeightBlocksComponent;
import main.generation.components.custom.SmallTreeComponent;
import main.generation.components.custom.UndergroundBlockPatchComponent;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

/**
 * A biome consisting of types of stone.
 * 
 * @author Krystilize
 */
public class ScatteredForest implements BiomeConfig {
	
	@Override
	public Pair<Double, Double> getTemperature() {
		// Trees need average temps
		return new Pair<Double, Double>(0.25, 0.75);
	}

	@Override
	public Pair<Double, Double> getHumidity() {
		// Decently low
		return new Pair<Double, Double>(0.3, 0.6);
	}

	@Override
	public Pair<Double, Double> getElevation() {
		// Medium elevation with little range
		return new Pair<Double, Double>(0.45, 0.1);
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Medium Vegetation
		return new Pair<Double, Double>(0.4, 0.6);
	}

	@Override
	public Integer getRarity() {
		// Pretty common
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
			0.06,
			0.05,
			0.04,
			0.03,
		};
		
		short[] blockIDs = {
			Block.GRASS_BLOCK.getBlockId(),
			Block.DIRT.getBlockId(),
			Block.STONE.getBlockId(),
			Block.BLACKSTONE.getBlockId(),
			Block.STONE.getBlockId(),
			Block.STONE.getBlockId(),
			Block.IRON_ORE.getBlockId(),
			Block.GOLD_ORE.getBlockId(),
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
			new SmallTreeComponent(0.001, Block.BIRCH_LOG.getBlockId(), Block.BIRCH_LEAVES.getBlockId()),
			new UndergroundBlockPatchComponent(0.05, Block.DIORITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.05, Block.ANDESITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.05, Block.GRANITE.getBlockId()),
			new RandomTerrainHeightBlocksComponent(0, 0.1, Block.GRASS.getBlockId())
		);
	}
}
