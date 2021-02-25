package main.generation.biomes.custom;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.custom.BedrockComponent;
import main.generation.components.custom.CaveCarverComponent;
import main.generation.components.custom.SquareTowerComponent;
import main.generation.components.custom.UndergroundBlockPatchComponent;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

/**
 * A biome consisting of types of stone.
 * 
 * @author Krystilize
 */
public class Stone implements BiomeConfig {
	
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
		// High elevation with extremely small range
		return new Pair<Double, Double>(0.9, 0.05);
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Zero vegetation
		return new Pair<Double, Double>(0.0, 0.0);
	}

	@Override
	public Pair<double[], short[]> getTerrainBlocks() {
		// Bunch of stones
		double[] weightings = {
			1.0,
			0.8,
			0.6,
			0.4,
			0.2
		};
		
		short[] blockIDs = {
			Block.STONE.getBlockId(),
			Block.GRANITE.getBlockId(),
			Block.ANDESITE.getBlockId(),
			Block.DIORITE.getBlockId(),
			Block.BLACKSTONE.getBlockId()
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
			new SquareTowerComponent(Block.STONE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.DIORITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.ANDESITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.GRANITE.getBlockId()),
			new BedrockComponent()
		);
	}
}
