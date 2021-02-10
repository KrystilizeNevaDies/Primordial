package main.config;

import java.util.HashMap;
import java.util.Map;

import main.config.biome.BiomeConfig;
import main.config.world.WorldConfig;
import main.generation.biomes.CustomBiome;

/**
 * The default configuration's purpose is to generate a world with a balanced diet consisting of all of Primordial's main features.
 * 
 * 
 * 
 * @author Krystilize
 */
public class DefaultConfig implements WorldConfig {
	
	@Override
	public Map<String, BiomeConfig> getBiomeConfigs() {
		// Create biome configs
		Map<String, BiomeConfig> biomes = new HashMap<String, BiomeConfig>();
		
		// Add custom biomes
		for (CustomBiome biome : CustomBiome.values()) {
			biomes.put(biome.name(), biome.getConfig());
		}
		
		// return biomes
		return biomes;
	}

	@Override
	public String getWorldName() {
		return "DefaultWorld";
	}
	
}
