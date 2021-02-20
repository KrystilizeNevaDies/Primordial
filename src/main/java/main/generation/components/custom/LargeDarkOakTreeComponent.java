package main.generation.components.custom;

import java.util.List;
import java.util.Random;

import main.generation.components.ComponentActivation;
import main.generation.components.ComponentType;
import main.generation.components.GenerationComponent;
import main.util.SchematicUtils;
import main.world.PrimordialWorld;

public class LargeDarkOakTreeComponent implements GenerationComponent {

	public static SchematicUtils schematic = SchematicUtils.read("Schematics/LargeDarkOakTree.schem");
	
	private double rarity;

	public LargeDarkOakTreeComponent(double rarity) {
		this.rarity = rarity;
	}
	
	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMCHUNK, rarity);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting,
			List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		int chunkMinX = x * 16;
		int chunkMinZ = z * 16;
		
		Random random = world.getRandom();
		
		int offY = 20 + random.nextInt(16);
				
		// TODO: Tree offset
		
		// Insert tree
		for (Short id : schematic.getBlockIDs()) {
			blockIDs.add(id);
		}
		
		for (Integer posX : schematic.getArrayX(chunkMinX)) {
			arrayX.add(posX);
		}
		
		for (Integer posY : schematic.getArrayY()) {
			arrayY.add(posY + (int) noiseMap[0][1][0] - offY);
		}
		
		for (Integer posZ : schematic.getArrayZ(chunkMinZ)) {
			arrayZ.add(posZ);
		}
	}

}
