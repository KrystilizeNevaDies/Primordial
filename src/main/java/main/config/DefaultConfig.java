package main.config;

import java.util.HashMap;
import java.util.Map;

import main.config.world.WorldConfig;
import main.generation.biomes.BiomeConfig;
import main.generation.biomes.CustomBiomes;

/**
 * The default configuration's purpose is to generate a world with a balanced diet consisting of all of Primordial's main features.
 * 
 * 
 * 
 * @author Krystilize
 */
public class DefaultConfig implements WorldConfig {
	
	public DefaultConfig(double chunksToGenerate) {}
	
	public DefaultConfig() {}
	
	@Override
	public Map<String, BiomeConfig> getBiomeConfigs() {
		// Create biome configs
		Map<String, BiomeConfig> biomes = new HashMap<String, BiomeConfig>();
		
		// Add custom biomes
		for (BiomeConfig biome : CustomBiomes.getBiomes()) {
			biomes.put(biome.getName(), biome);
		}
		
		// return biomes
		return biomes;
	}

	@Override
	public String getWorldName() {
		return "DefaultWorld";
	}
}
