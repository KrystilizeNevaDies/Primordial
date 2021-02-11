package main.world;

import java.util.List;
import java.util.Random;

public interface PrimordialWorld {
	
	/**
	 * Sets a block at specified position (very slow)
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 * @param blockID the block's ID
	 */
	public void setBlock(int x, int y, int z, short blockID);
	
	/**
	 * Sets a group of blocks into the world (most efficient)
	 * 
	 * All block placements are absolute
	 * 
	 * @param x the orgin's x coord
	 * @param y the orgin's y coord
	 * @param z the orgin's z coord
	 * @param blockIDs the blocks IDs in an ordered array
	 * @param arrayX the block's x coordinations in an ordered array
	 * @param arrayY the block's y coordinations in an ordered array
	 * @param arrayZ the block's z coordinations in an ordered array
	 */
	public void setBlockGroup(Short[] blockIDs, Integer[] arrayX, Integer[] arrayY, Integer[] arrayZ);
	
	/**
	 * Sets a group of blocks into the world (most efficient)
	 * 
	 * All block placements are absolute
	 * 
	 * @param x the orgin's x coord
	 * @param y the orgin's y coord
	 * @param z the orgin's z coord
	 * @param blockIDs the blocks IDs in an ordered array
	 * @param arrayX the block's x coordinations in an ordered array
	 * @param arrayY the block's y coordinations in an ordered array
	 * @param arrayZ the block's z coordinations in an ordered array
	 */
	public default void setBlockGroup(List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		Short[] blockArray = blockIDs.toArray(new Short[blockIDs.size()]);
		Integer[] posXArray = arrayX.toArray(new Integer[arrayX.size()]);
		Integer[] posYArray = arrayY.toArray(new Integer[arrayY.size()]);
		Integer[] posZArray = arrayZ.toArray(new Integer[arrayZ.size()]);
		
		this.setBlockGroup(blockArray, posXArray, posYArray, posZArray);
	};
	
	/**
	 * Sets a group of blocks into the world (most efficient)
	 * 
	 * All block placements are relative
	 * 
	 * @param x the orgin's x coord
	 * @param y the orgin's y coord
	 * @param z the orgin's z coord
	 * @param blockIDs the blocks IDs in an ordered array
	 * @param arrayX the block's x coordinations in an ordered array
	 * @param arrayY the block's y coordinations in an ordered array
	 * @param arrayZ the block's z coordinations in an ordered array
	 */
	public void setBlockGroup(int x, int y, int z, Short[] blockIDs, Integer[] arrayX, Integer[] arrayY, Integer[] arrayZ);
	
	/**
	 * Sets a group of blocks into the world (most efficient)
	 * 
	 * All block placements are relative
	 * 
	 * @param x the orgin's x coord
	 * @param y the orgin's y coord
	 * @param z the orgin's z coord
	 * @param blockIDs the blocks IDs in an ordered array
	 * @param arrayX the block's x coordinations in an ordered array
	 * @param arrayY the block's y coordinations in an ordered array
	 * @param arrayZ the block's z coordinations in an ordered array
	 */
	public default void setBlockGroup(int x, int y, int z, List<Short> blockIDs, List<Integer> arrayX, List<Integer> arrayY, List<Integer> arrayZ) {
		
		Short[] blockArray = blockIDs.toArray(new Short[blockIDs.size()]);
		Integer[] posXArray = arrayX.toArray(new Integer[arrayX.size()]);
		Integer[] posYArray = arrayY.toArray(new Integer[arrayY.size()]);
		Integer[] posZArray = arrayZ.toArray(new Integer[arrayZ.size()]);
		
		this.setBlockGroup(x, y, z, blockArray, posXArray, posYArray, posZArray);
	};
	
	
	/**
	 * Gets the seed of this world
	 * @return int the seed
	 */
	public int getSeed();
	
	/**
	 * Gets the RNG of this world
	 * @return random the RNG
	 */
	public Random getRandom();
}
