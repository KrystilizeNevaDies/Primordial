package main.generation.biomes;

import main.generation.biomes.custom.stone.AmplifiedStone;
import main.generation.biomes.custom.stone.DenseForest;
import main.generation.biomes.custom.stone.GiantForest;
import main.generation.biomes.custom.stone.ScatteredForest;
import main.generation.biomes.custom.stone.Stone;
/**
 * An enum containing all of our custom biomes.
 * 
 * @author Krystilize
 */
public enum CustomBiome {
	STONE(new Stone()),
	AMPLIFIED_STONE(new AmplifiedStone()),
	SCATTERED_FOREST(new ScatteredForest()),
	DENSE_FOREST(new DenseForest()),
	GIANT_FOREST(new GiantForest());
	
	BiomeConfig config;
	
	CustomBiome(BiomeConfig config) {
		this.config = config;
	}
	
	public BiomeConfig getConfig() {
		return config;
	}
}
