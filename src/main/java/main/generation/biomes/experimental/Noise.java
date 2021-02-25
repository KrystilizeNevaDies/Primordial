package main.generation.biomes.experimental;

import java.util.List;

import main.generation.biomes.BiomeConfig;
import main.generation.components.GenerationComponent;
import main.generation.components.experimental.NoiseComponent;
import main.util.Pair;
import net.minestom.server.instance.block.Block;

public class Noise implements BiomeConfig {
	
	Pair<Double, Double> temp = new Pair<Double, Double>(Math.random(), Math.random());
	Pair<Double, Double> humid = new Pair<Double, Double>(Math.random(), Math.random());
	Pair<Double, Double> elev = new Pair<Double, Double>(1.0, 1.0);
	Pair<Double, Double> vege = new Pair<Double, Double>(Math.random(), Math.random());
	
	@Override
	public Pair<Double, Double> getTemperature() {
		return temp;
	}

	@Override
	public Pair<Double, Double> getHumidity() {
		return humid;
	}

	@Override
	public Pair<Double, Double> getElevation() {
		return elev;
	}

	@Override
	public Pair<Double, Double> getVegetation() {
		// Zero vegetation
		return vege;
	}

	@Override
	public Pair<double[], short[]> getTerrainBlocks() {
		// Stone
		double[] weightings = {
			1.0
		};
		
		short[] blockIDs = {
			Block.IRON_BLOCK.getBlockId()
		};
		
		return new Pair<double[], short[]>(weightings, blockIDs);
	}

	@Override
	public int getFogColor() {
		return 0xC0D8FF;
	}

	@Override
	public int getSkyColor() {
		return 0x78A7FF;
	}

	@Override
	public int getWaterColor() {
		return 0x3F76E4;
	}

	@Override
	public int getWaterFogColor() {
		return 0x50533;
	}

	@Override
	public List<GenerationComponent> getComponents() {
		return List.of(
			new NoiseComponent()
		);
	}
}
