package main.generation.features;

import main.world.PrimordialWorld;

@FunctionalInterface
public interface ComponentConsumer {
    void accept(PrimordialWorld world, int x, int y, int z, int[][] height);
}
