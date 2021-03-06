package main.generation.biomes;

import java.util.List;

import main.generation.components.GenerationComponent;
import main.util.Pair;

public interface BiomeConfig {
	/**
	 * Gets the min and max temperature of this biome. (0 to 1)
	 * 
	 * @return Pair the min and max values of the temperature. First value is Min, second is Max.
	 */
	public Pair<Double, Double> getTemperature();
	
	/**
	 * Get the average temperature
	 * 
	 * @return double the average temperature between 0 and 1
	 */
	public default double getAverageTemperature() {
		Pair<Double, Double> temp = getTemperature();
		return (temp.getFirst() + temp.getSecond()) / 2;
	}
	
	/**
	 * Gets the min and max humidity of this biome. (0 to 1)
	 * 
	 * @return Pair the min and max values of the humidity. First value is Min, second is Max.
	 */
	public Pair<Double, Double> getHumidity();
	
	/**
	 * Get the average humidity
	 * 
	 * @return double the average humidity between 0 and 1
	 */
	public default double getAverageHumidity() {
		Pair<Double, Double> humid = getHumidity();
		return (humid.getFirst() + humid.getSecond()) / 2;
	}
	
	/**
	 * Gets the min and max elevation of this biome. (0 to 1)
	 * 
	 * @return Pair the min and max values of the elevation. First value is Min, second is Max.
	 */
	public Pair<Double, Double> getElevation();
	
	/**
	 * Get the average elevation
	 * 
	 * @return double the average elevation between 0 and 1
	 */
	public default double getAverageElevation() {
		Pair<Double, Double> elev = getElevation();
		return (elev.getFirst() + elev.getSecond()) / 2;
	}
	
	/**
	 * Gets the min and max vegetation of this biome. (0 to 1)
	 * 
	 * @return Pair the min and max values of the vegetation. First value is Min, second is Max.
	 */
	public Pair<Double, Double> getVegetation();
	
	/**
	 * Get the average vegetation
	 * 
	 * @return double the average vegetation between 0 and 1
	 */
	public default double getAverageVegetation() {
		Pair<Double, Double> vege = getVegetation();
		return (vege.getFirst() + vege.getSecond()) / 2;
	}
	
	/**
	 * Gets a list of all biome-specific components to apply when generating this biome.
	 * 
	 * @return List the list of components
	 */
	public List<GenerationComponent> getComponents();
	
	/**
	 * Gets the blocks that make up the terrain of this biome and their respective height deltas (0 and above).
	 * e.g:
	 * <br>
	 * double[] weightings = {0.5, 0.4};
	 * <br>
	 * short[] blockIDs = {Block.GRASS_BLOCK.getBlockId(), Block.STONE.getBlockId()};
	 * <br>
	 * new Pair{@literal <double[], short[]>}(weightings, blockIDs);
	 * 
	 * @return Pair<double[], short[]> deltas + blockIDs of the biome
	 */
	public Pair<double[], short[]> getTerrainBlocks();
	
	/**
	 * Gets the fog color of this biome.
	 * 
	 * @return Integer Hex RGB value e.g. 0xED04FA
	 */
	public int getFogColor();
	
	/**
	 * Gets the sky color of this biome.
	 * 
	 * @return Integer Hex RGB value e.g. 0xED04FA
	 */
	public int getSkyColor();
	
	/**
	 * Gets the water color of this biome.
	 * 
	 * @return Integer Hex RGB value e.g. 0xED04FA
	 */
	public int getWaterColor();
	
	/**
	 * Gets the water fog color of this biome.
	 * 
	 * @return Integer Hex RGB value e.g. 0xED04FA
	 */
	public int getWaterFogColor();
	
	/**
	 * Gets the name of this biome.
	 * 
	 * @return String The name e.g. Plains
	 */
	public default String getName() {
		return this.getClass().getSimpleName();
	};
}
