package main.generation.biomes;

import java.util.List;

import main.generation.biomes.experimental.BlankBiome;
import main.generation.biomes.experimental.Noise;

/**
 * All our custom biomes.
 * 
 * @author Krystilize
 */
public class ExperimentalBiomes {
	private static List<BiomeConfig> biomes = List.of(
		new Noise(),
		new BlankBiome()
	);

	public static List<BiomeConfig> getBiomes() {
		return biomes;
	}
}

