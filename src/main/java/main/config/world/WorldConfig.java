package main.config.world;

import java.util.Map;

import main.config.biome.BiomeConfig;

public interface WorldConfig {
	/**
	 * Get a mapping containing all biomes and their configs.
	 * 
	 * @return Map a map of all added BiomeConfigs indexed by their name
	 */
	public Map<String, BiomeConfig> getBiomeConfigs();
	
	/**
	 * Gets the name of this world
	 */
	public String getWorldName();
}
