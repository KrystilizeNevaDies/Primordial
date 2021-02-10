package main.generation.biomes.custom;

import java.util.Collection;
import java.util.List;

import main.config.biome.BiomeConfig;
import main.generation.features.GenerationFeature;
import main.util.Pair;

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
	public Collection<GenerationFeature> getFeatures() {
		// TODO: Features for stone biome
		return List.of();
	}

	@Override
	public Integer getRarity() {
		// Should not be very rare
		return 60;
	}

	@Override
	public Collection<String> getTerrainBlocks() {
		// Bunch of stones
		return List.of("STONE", "GRANITE", "COBBLESTONE", "STONE_BRICKS", "ANDESITE", "DIORITE");
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
}
