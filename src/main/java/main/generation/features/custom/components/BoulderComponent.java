package main.generation.features.custom.components;

import main.generation.features.ComponentActivation;
import main.generation.features.ComponentType;
import main.generation.features.GenerationComponent;
import main.world.PrimordialWorld;
import net.minestom.server.instance.block.Block;

public class BoulderComponent implements GenerationComponent {

	@Override
	public ComponentActivation getActivation() {
		return ComponentActivation.of(ComponentType.RANDOMTERRAINHEIGHT, 0.03);
	}

	@Override
	public void applyComponent(PrimordialWorld world, int x, int y, int z, int[][] height) {
		world.setBlock(x, y, z, Block.DIAMOND_BLOCK.getBlockId());
	}
}