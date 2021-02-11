package main.generation.biomes;

import main.config.biome.BiomeConfig;
import main.generation.biomes.custom.stone.StoneBiome;
/**
 * An enum containing all of our custom biomes.
 * 
 * @author Krystilize
 */
public enum CustomBiome {
	STONE(new StoneBiome());
	
	BiomeConfig config;
	
	CustomBiome(BiomeConfig config) {
		this.config = config;
	}
	
	public BiomeConfig getConfig() {
		return config;
	}
}
