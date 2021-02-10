package main.minestom;

import main.generation.world.PrimordialChunk;
import net.minestom.server.instance.Chunk;

public class MinestomChunk implements PrimordialChunk {
	
	Chunk chunk;
	
	public MinestomChunk(Chunk chunk) {
		this.chunk = chunk;
	}
}
