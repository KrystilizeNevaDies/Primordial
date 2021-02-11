package main.generation.features;

import java.util.Random;

import main.world.PrimordialWorld;

public class ComponentActivation {
	private ComponentType type;
	private float chance;
	
	private ComponentActivation(ComponentType type, float chance) {
		setType(type);
		setChance(chance);
	}
	
	/**
	 * Create a component activation using the specified type and change
	 * @param type
	 * @param d
	 * @return component activation
	 */
	public static ComponentActivation of(ComponentType type, double d) {
		return new ComponentActivation(type, (float) d);
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

	public float getChance() {
		return chance;
	}

	private void setChance(float chance) {
		this.chance = chance;
	}

	public void activate(PrimordialWorld world, int chunkX, int chunkZ, int[][] height, ComponentConsumer consumer) {
		int chunkXMin = chunkX * 16;
		int chunkZMin = chunkZ * 16;
		
		Random random = world.getRandom(); 
		
		switch(type) {
		case ALLCHUNK:
			consumer.accept(world, chunkX, 0, chunkZ, height);
			break;
		case ALLPOSITION:
			for (int x = 0; x < 16; x++)
				for (int y = 0; y < 256; y++)
					for (int z = 0; z < 16; z++)
						consumer.accept(world, chunkXMin + x, y, chunkZMin + z, height);
			break;
		case ALLTERRAINHEIGHT:
			for (int x = 0; x < 16; x++)
					for (int z = 0; z < 16; z++) {
						consumer.accept(world, chunkXMin + x, height[x][z], chunkZMin + z, height);
					}
			break;
		case RANDOMCHUNK:
			if (random.nextDouble() < getChance())
				consumer.accept(world, chunkX, 0, chunkZ, height);
			break;
		case RANDOMPOSITION:
			for (int x = 0; x < 16; x++)
				for (int y = 0; y < 256; y++)
					for (int z = 0; z < 16; z++)
						if (random.nextDouble() < getChance())
							consumer.accept(world, chunkXMin + x, y, chunkZMin + z, height);
			break;
		case RANDOMTERRAINHEIGHT:
			for (int x = 0; x < 16; x++)
				for (int z = 0; z < 16; z++) {
					if (random.nextDouble() < getChance())
						consumer.accept(world, chunkXMin + x, height[x][z], chunkZMin + z, height);
				}
			break;
		default:
			break;
		
		}
	}
}
