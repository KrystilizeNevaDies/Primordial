package main.generation.components;

import java.util.ArrayList;
import java.util.Random;

import main.world.PrimordialWorld;

public class ComponentActivation {
	private ComponentType type;
	private double chance;
	
	private ComponentActivation(ComponentType type, double chance) {
		setType(type);
		setChance(chance);
	}
	
	/**
	 * Create a component activation using the specified type and chance
	 * @param type
	 * @param chance
	 * @return component activation
	 */
	public static ComponentActivation of(ComponentType type, double chance) {
		return new ComponentActivation(type, chance);
	}
	
	/**
	 * Create a component activation using the specified type
	 * @param type
	 * @return component activation
	 */
	public static ComponentActivation of(ComponentType type) {
		return new ComponentActivation(type, 1);
	}

	public ComponentType getType() {
		return type;
	}

	private void setType(ComponentType type) {
		this.type = type;
	}

	public double getChance() {
		return chance;
	}

	private void setChance(double chance) {
		this.chance = chance;
	}

	public void activate(PrimordialWorld world, int chunkX, int chunkZ, double[][][] noiseMap, double weighting,
			ArrayList<Short> blockIDs, ArrayList<Integer> arrayX, ArrayList<Integer> arrayY, ArrayList<Integer> arrayZ,
			ComponentConsumer consumer) {
		int chunkXMin = chunkX * 16;
		int chunkZMin = chunkZ * 16;
		
		Random random = world.getRandom(); 
		
		switch(type) {
			case ALLCHUNK:
				consumer.accept(world, chunkX, 0, chunkZ, noiseMap, weighting, blockIDs, arrayX, arrayY, arrayZ);
				break;
			case RANDOMCHUNK:
				if (random.nextDouble() < getChance())
					consumer.accept(world, chunkX, 0, chunkZ, noiseMap, weighting, blockIDs, arrayX, arrayY, arrayZ);
				break;
			case RANDOMTERRAINHEIGHT:
				while (random.nextDouble() > Math.pow(chance, 2.0)) {
					int z = random.nextInt(16);
					int x = random.nextInt(16);
					consumer.accept(world, chunkXMin + x, (int) noiseMap[x][1][z], chunkZMin + z, noiseMap, weighting, blockIDs, arrayX, arrayY, arrayZ);
				}
				break;
		}
	}
}
