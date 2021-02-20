package main.minestom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.generation.CoreGenerator;
import main.generation.biomes.BiomeConfig;
import main.generation.biomes.BiomeSelector;
import main.thread.AsyncAction;
import main.thread.ThreadPools;
import main.util.Pair;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.Biome.Category;
import net.minestom.server.world.biomes.BiomeEffects;

public class MinestomTranslationLayer implements ChunkGenerator {
	
	private Map<BiomeConfig, Biome> biomeMapping = new HashMap<BiomeConfig, Biome>();
	private BiomeSelector selector;
	private MinestomInstance instance;
	
	
	public MinestomTranslationLayer(Map<String, BiomeConfig> biomeConfigs, MinestomInstance instance) {
		this.instance = instance;
		
		// Create selector used to determine biomes
		this.setSelector(new BiomeSelector(biomeConfigs, (int) (Math.random() * Integer.MAX_VALUE)));
		
		// Init generator
		CoreGenerator.init(instance, getSelector()); 
		
		// Generate minestom biomes
		biomeConfigs.forEach((name, biome) -> {
		    BiomeEffects effects = BiomeEffects.builder()
	            .fogColor(biome.getFogColor())
	            .skyColor(biome.getSkyColor())
	            .waterColor(biome.getWaterColor())
	            .waterFogColor(biome.getWaterFogColor())
	            .build();
		    
		    Biome curatedBiome = Biome.builder()
	            .category(Category.NONE)
	            .name(NamespaceID.from("primordial", name.toLowerCase()))
	            .temperature((float) biome.getAverageTemperature())
	            .downfall((float) biome.getAverageHumidity())
	            .depth((float) biome.getAverageElevation())
	            .scale(0.05F)
	            .effects(effects)
	            .build();
		    
		    biomeMapping.put(biome, curatedBiome);
		    MinecraftServer.getBiomeManager().addBiome(curatedBiome);
		});
	}
	
    @Override
    public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
    	// Schedule generation on next instance tick
    	instance.scheduleNextTick((instance) -> {
    		// Create Async task
    		// Runnable task = new AsyncAction<Integer, Integer>(chunkX, chunkZ, CoreGenerator::generateChunk);
    		Runnable task = new AsyncAction<Integer, Integer>(chunkX, chunkZ, (x, z) -> {
    			long time = System.currentTimeMillis();
    			CoreGenerator.generateChunk(x, z);
    			System.out.println("Chunk at (" + x + ", " + z + ") took " + ((System.currentTimeMillis() - time) / 4) + "ms");
    		});
    		
    		// Run task
    		ThreadPools.CHUNKGENERATION.execute(task);
    	});
    }
    
    @Override
    public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
    	// Get biome
    	Pair<double[], BiomeConfig[]> configs = getSelector().getBiome(chunkX, chunkZ);
    	
    	var biomeConfigs = configs.getSecond();
    	
    	// Fill array
    	Arrays.fill(biomes, biomeMapping.get(biomeConfigs[0]));
    }
    
    @Override
    public List<ChunkPopulator> getPopulators() {
        return null;
    }

	public BiomeSelector getSelector() {
		return selector;
	}

	private void setSelector(BiomeSelector selector) {
		this.selector = selector;
	}
}