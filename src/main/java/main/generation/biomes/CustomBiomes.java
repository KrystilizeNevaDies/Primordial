package main.generation.biomes;

import java.util.List;

import main.generation.biomes.custom.AmplifiedStone;
import main.generation.biomes.custom.DenseForest;
import main.generation.biomes.custom.GiantForest;
import main.generation.biomes.custom.ScatteredForest;
import main.generation.biomes.custom.Stone;

/**
 * All our custom biomes.
 * 
 * @author Krystilize
 */
public class CustomBiomes {
	private static List<BiomeConfig> biomes = List.of(
		new AmplifiedStone(),
		new DenseForest(),
		new GiantForest(),
		new ScatteredForest(),
		new Stone()
	);

	public static List<BiomeConfig> getBiomes() {
		return biomes;
	}
}

