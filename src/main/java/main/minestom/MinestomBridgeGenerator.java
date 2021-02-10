package main.minestom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.config.biome.BiomeConfig;
import main.generation.CoreGenerator;
import main.generation.biomes.BiomeSelector;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.Biome.Category;
import net.minestom.server.world.biomes.BiomeEffects;

public class MinestomBridgeGenerator implements ChunkGenerator {
	
	private Map<BiomeConfig, Biome> biomeMapping = new HashMap<BiomeConfig, Biome>();
	
	private BiomeSelector selector;
	
	public MinestomBridgeGenerator(Map<String, BiomeConfig> biomeConfigs) {
		// Create selector used to determine biomes
		this.selector = new BiomeSelector(biomeConfigs, (int) (Math.random() * Integer.MAX_VALUE));
		
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
    	// Generate chunk + schedule placement
		CoreGenerator.generateChunk(chunkX, chunkZ);
    }
    
    @Override
    public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; y < 64; y++)
    			for (int z = 0; z < 4; z++) {
    				// 1024 biome IDs, ordered by x then z then y, in 4󫶘 blocks
    				int posX = x * 4;
    				int posY = y * 4;
    				int posZ = z * 4;
    				int i = x + z*4 + y*16;
    				
    				biomes[i] = biomeMapping.get(selector.getBiome(posX, posY, posZ));
    			}
    }
    
    @Override
    public List<ChunkPopulator> getPopulators() {
        return null;
    }
}