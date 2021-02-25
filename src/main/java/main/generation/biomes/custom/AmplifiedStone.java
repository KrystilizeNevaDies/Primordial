package main.generation.biomes.custom;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.custom.BedrockComponent;
import main.generation.components.custom.CaveCarverComponent;
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
		// Low elevation with extremely high range
		return new Pair<Double, Double>(0.1, 1.5);
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Zero vegetation
		return new Pair<Double, Double>(0.0, 0.0);
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
			new CaveCarverComponent(),
			new TerrainCubeComponent(7, 0.8,
				Block.STONE_BRICKS.getBlockId(),
				Block.COBBLESTONE.getBlockId(),
				Block.POLISHED_DIORITE.getBlockId(),
				Block.POLISHED_ANDESITE.getBlockId(),
				Block.POLISHED_GRANITE.getBlockId(),
				Block.GRAY_CONCRETE.getBlockId()
			),
			new UndergroundBlockPatchComponent(0.5, Block.DIORITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.ANDESITE.getBlockId()),
			new UndergroundBlockPatchComponent(0.5, Block.GRANITE.getBlockId()),
			new BedrockComponent()
		);
	}
}
