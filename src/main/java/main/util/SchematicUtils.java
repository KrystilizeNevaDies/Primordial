package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTInt;
import org.jglrxavpok.hephaistos.nbt.NBTReader;

import net.minestom.server.instance.block.Block;

public class SchematicUtils {
	
	private List<Short> blockIDs;
	private List<Integer> arrayX;
	private List<Integer> arrayY;
	private List<Integer> arrayZ;
	
	SchematicUtils() {
		this.blockIDs = new ArrayList<Short>();
		this.arrayX = new ArrayList<Integer>();
		this.arrayY = new ArrayList<Integer>();
		this.arrayZ = new ArrayList<Integer>();
	}
	
	public static SchematicUtils read(String name) {
		
		SchematicUtils schematic = new SchematicUtils();
		
		try {   
            InputStream fis = new FileInputStream(new File(name));
            
            NBTReader input = new NBTReader(fis);
            
            NBTCompound nbtdata = (NBTCompound) input.read();
 
            Map<Integer, String> palette = new HashMap<Integer, String>();
            
            short width = nbtdata.getAsShort("Width");
            short height = nbtdata.getAsShort("Height");
            short length = nbtdata.getAsShort("Length");
            
 
        	// Get blocks
            byte[] blockData = nbtdata.getByteArray("BlockData");

            NBTCompound NBTBlockIDs = nbtdata.getCompound("Palette");
            
            NBTBlockIDs.iterator().forEachRemaining((pair) -> {
            	String string = pair.getFirst();
            	NBT tag = pair.getSecond();
            	palette.put(((NBTInt) tag).getValue(), string);
            });
            
            fis.close();
            input.close();
            
            // Loop through blocks and add them to block list
            for (int X = 0; X < width; X++)
            	for (int Y = 0; Y < height; Y++)
            		for (int Z = 0; Z < length; Z++) {
            			int i = (Y * length + Z) * width + X;
        			// Get namespace
            			String namespace = palette.get((int) blockData[i]);
            			
            			// Insert block
            			if (!namespace.equals("minecraft:air")) {
            				schematic.getBlockIDs().add(getNamespace(namespace));
            				schematic.getArrayX().add(X);
            				schematic.getArrayY().add(Y);
            				schematic.getArrayZ().add(Z);
            				
            			}
            			
            		}
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return schematic;
	}
	
	private static short getNamespace(String namespace) {
		/**
		 * Examples:
		 * 
		 * minecraft:dark_oak_fence[east=true,north=false,south=false,waterlogged=false,west=true]
		 * minecraft:grass_block[snowy=false]
		 * minecraft:warped_planks
		 */
		
		// minecraft:grass_block[snowy=false]
		
		String name = namespace.replaceAll("minecraft:", "");
		
		// grass_block[snowy=false]
		
		String[] split = name.split("\\[");
		
		// 0 | grass_block
		// 1 | [snowy=false]
		if (split.length > 1) {
			String state = split[1];
			
			state = state.replace("[", "");
			state = state.replace("]", "");
			
			String[] propertiesSplit = state.split(",");
			
			Block target = Block.valueOf(split[0].toUpperCase());
			return target.withProperties(propertiesSplit);
		} else {
			return Block.valueOf(split[0].toUpperCase()).getBlockId();
		}
	}

	public List<Short> getBlockIDs() {
		return blockIDs;
	}

	public List<Integer> getArrayX() {
		return arrayX;
	}
	
	public List<Integer> getArrayY() {
		return arrayY;
	}
	
	public List<Integer> getArrayZ() {
		return arrayZ;
	}
	
	public List<Integer> getArrayX(int offset) {
		List<Integer> newArray = new ArrayList<Integer>();
		
		for (Integer pos : arrayX) {
			newArray.add(pos + offset);
		}
		
		return newArray;
	}

	public List<Integer> getArrayY(int offset) {
		List<Integer> newArray = new ArrayList<Integer>();
		
		for (Integer pos : arrayY) {
			newArray.add(pos + offset);
		}
		
		return newArray;
	}

	public List<Integer> getArrayZ(int offset) {
		List<Integer> newArray = new ArrayList<Integer>();
		
		for (Integer pos : arrayZ) {
			newArray.add(pos + offset);
		}
		
		return newArray;
	}
}
