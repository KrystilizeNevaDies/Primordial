package main.generation.biomes.custom.stone;

import java.util.Collection;
import java.util.List;

import main.config.biome.BiomeConfig;
import main.generation.features.GenerationComponent;
import main.generation.features.custom.components.BoulderComponent;
import main.generation.features.custom.components.SquareTower;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

/**
 * A biome consisting of types of stone.
 * 
 * @author Krystilize
 */
public class StoneBiome implements BiomeConfig {
	
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
		// Medium elevation with little range
		return new Pair<Double, Double>(0.45, 0.55);
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Zero vegetation
		return new Pair<Double, Double>(0.0, 0.0);
	}

	@Override
	public Integer getRarity() {
		// Should not be very rare
		return 60;
	}

	@Override
	public List<Short> getTerrainBlocks() {
		// Bunch of stones
		return List.of(
			Block.STONE.getBlockId(),
			Block.GRANITE.getBlockId(),
			Block.COBBLESTONE.getBlockId(),
			Block.STONE_BRICKS.getBlockId(),
			Block.ANDESITE.getBlockId(),
			Block.DIORITE.getBlockId()
		);
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
	public Collection<GenerationComponent> getComponents() {
		return List.of(new SquareTower(this), new BoulderComponent());
	}
}
