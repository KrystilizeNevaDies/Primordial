package main.world;

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
}
