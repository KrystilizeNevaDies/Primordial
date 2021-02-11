package main.minestom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.config.biome.BiomeConfig;
import main.generation.CoreGenerator;
import main.generation.biomes.BiomeSelector;
import main.thread.AsyncAction;
import main.thread.GenerationThreadPool;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.Biome.Category;
import net.minestom.server.world.biomes.BiomeEffects;

public class MinestomTranslationLayer implements ChunkGenerator {
	
	private Map<BiomeConfig, Biome> biomeMapping = new HashMap<BiomeConfig, Biome>();
	private BiomeSelector selector;
	private InstanceContainer instance;
	private CoreGenerator generator;
	
	
	public MinestomTranslationLayer(Map<String, BiomeConfig> biomeConfigs, MinestomInstance instance) {
		this.instance = instance;
		
		// Create selector used to determine biomes
		this.selector = new BiomeSelector(biomeConfigs, (int) (Math.random() * Integer.MAX_VALUE));
		
		// Create generator
		this.generator = new CoreGenerator(instance, selector); 
		
		// Generate minestom biomes
		biomeConfigs.forEach((name, biome) -> {
		    BiomeEffects effects = BiomeEffects.builder()
	            .fogColor(0xC0D8FF)
	            .skyColor(0x78A7FF)
	            .waterColor(0x3F76E4)
	            .waterFogColor(0x50533)
	            .build();
		    
		    Biome curatedBiome = Biome.builder()
	            .category(Category.NONE)
	            .name(NamespaceID.from("minecraft:plains"))
	            .temperature((float) biome.getAverageTemperature())
	            .downfall((float) biome.getAverageHumidity())
	            .depth((float) biome.getAverageElevation())
	            .scale(0.05F)
	            .effects(effects)
	            .build();
		    
		    biomeMapping.put(biome, curatedBiome);
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
    			generator.generateChunk(x, z);
    			System.out.println("Chunk at (" + x + ", " + z + ") took " + (System.currentTimeMillis() - time) + "ms");
    		});
    		
    		// Run task
    		GenerationThreadPool.EXECUTOR.execute(task);
    	});
    }
    
    @Override
    public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
    	BiomeConfig[] configs = selector.getBiomes(chunkX, chunkZ);
    	for (int i = 0; i < biomes.length; i++) {
    		biomes[i] = biomeMapping.get(configs[i]);
    	}
    }
    
    @Override
    public List<ChunkPopulator> getPopulators() {
        return null;
    }
}