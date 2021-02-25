package main.config;

import java.util.HashMap;
import java.util.Map;

import main.config.world.WorldConfig;
import main.generation.biomes.BiomeConfig;
import main.generation.biomes.ExperimentalBiomes;

/**
 * The experimental configuration's purpose is to have fun
 * 
 * @author Krystilize
 */
public class ExperimentalConfig implements WorldConfig {
	
	public ExperimentalConfig(double chunksToGenerate) {}
	
	public ExperimentalConfig() {}
	
	@Override
	public Map<String, BiomeConfig> getBiomeConfigs() {
		// Create biome configs
		Map<String, BiomeConfig> biomes = new HashMap<String, BiomeConfig>();
		
		// Add custom biomes
		for (BiomeConfig biome : ExperimentalBiomes.getBiomes()) {
			biomes.put(biome.getName(), biome);
		}
		
		// return biomes
		return biomes;
	}

	@Override
	public String getWorldName() {
		return "ExperimentalWorld";
	}
}
