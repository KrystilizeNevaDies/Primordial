package main.generation.components;

import java.util.List;

import main.world.PrimordialWorld;

@FunctionalInterface
public interface ComponentConsumer {
    void accept(PrimordialWorld world, int x, int y, int z, double[][][] noiseMap, double weighting, List<Short> blockList, List<Integer> listPosX, List<Integer> listPosY, List<Integer> listPosZ);
}
