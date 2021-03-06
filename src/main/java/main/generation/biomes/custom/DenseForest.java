package main.generation.biomes.custom;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.custom.BedrockComponent;
import main.generation.components.custom.CaveCarverComponent;
import main.generation.components.custom.RandomTerrainHeightBlockComponent;
import main.generation.components.custom.SmallTreeComponent;
import main.generation.components.custom.UndergroundBlockPatchComponent;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

/**
 * A biome consisting of types of stone.
 * 
 * @author Krystilize
 */
public class DenseForest implements BiomeConfig {
	
	@Override
	public Pair<Double, Double> getTemperature() {
		// Dense trees need higher temps
		return new Pair<Double, Double>(0.50, 0.80);
	}

	@Override
	public Pair<Double, Double> getHumidity() {
		// Pretty high humidity
		return new Pair<Double, Double>(0.6, 0.9);
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
	public Pair<double[], short[]> getTerrainBlocks() {
		// A standard range of blocks
		double[] weightings = {
			1.0,
			0.95,
			0.90,
			0.85,
			0.80,
		};
		
		short[] blockIDs = {
			Block.GRASS_BLOCK.getBlockId(),
			Block.DIRT.getBlockId(),
			Block.STONE.getBlockId(),
			Block.BLACKSTONE.getBlockId(),
			Block.STONE.getBlockId(),
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
			new CaveCarverComponent(),
			new UndergroundBlockPatchComponent(0.5, Block.DIORITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.ANDESITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.GRANITE.getBlockId()),
			new RandomTerrainHeightBlockComponent(0, 0.5, Block.GRASS.getBlockId()),
			new RandomTerrainHeightBlockComponent(0, 0.5, Block.TALL_GRASS.getBlockId()),
			new RandomTerrainHeightBlockComponent(0, 0.5, Block.OAK_LEAVES.getBlockId()),
			new RandomTerrainHeightBlockComponent(0, 0.5, Block.ACACIA_WOOD.getBlockId()),
			new SmallTreeComponent(0.9, Block.BIRCH_LOG.getBlockId(), Block.BIRCH_LEAVES.getBlockId()),
			new SmallTreeComponent(0.9, Block.OAK_LOG.getBlockId(), Block.OAK_LEAVES.getBlockId()),
			new SmallTreeComponent(0.9, Block.DARK_OAK_LOG.getBlockId(), Block.DARK_OAK_LEAVES.getBlockId()),
			new SmallTreeComponent(0.9, Block.SPRUCE_LOG.getBlockId(), Block.SPRUCE_LEAVES.getBlockId()),
			new BedrockComponent()
		);
	}
}
