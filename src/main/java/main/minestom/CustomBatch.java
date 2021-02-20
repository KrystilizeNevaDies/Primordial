package main.minestom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minestom.server.data.Data;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.BatchOption;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.batch.InstanceBatch;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.utils.block.CustomBlockUtils;
import net.minestom.server.utils.chunk.ChunkCallback;
import net.minestom.server.utils.chunk.ChunkUtils;

/**
 * Used when the blocks you want to place need to be divided in multiple chunks,
 * use a {@link ChunkBatch} instead otherwise.
 * Can be created using {@link Instance#createBlockBatch()}, and executed with {@link #flush(Runnable)}.
 *
 * @see InstanceBatch
 */
public class CustomBatch implements InstanceBatch {

    private final InstanceContainer instance;
    private final BatchOption batchOption;

    private final Map<Chunk, List<BlockData>> data = new HashMap<>();

    public CustomBatch(@NotNull InstanceContainer instance, @NotNull BatchOption batchOption) {
        this.instance = instance;
        this.batchOption = batchOption;
    }

    public CustomBatch(@NotNull InstanceContainer instance) {
        this(instance, new BatchOption());
    }

    @Override
    public synchronized void setBlockStateId(int x, int y, int z, short blockStateId, @Nullable Data data) {
        addBlockData(x, y, z, blockStateId, (short) 0, data);
    }

    @Override
    public void setCustomBlock(int x, int y, int z, short customBlockId, @Nullable Data data) {
        final CustomBlock customBlock = BLOCK_MANAGER.getCustomBlock(customBlockId);
        addBlockData(x, y, z, customBlock.getDefaultBlockStateId(), customBlockId, data);
    }

    @Override
    public synchronized void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data) {
        addBlockData(x, y, z, blockStateId, customBlockId, data);
    }

    private void addBlockData(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data) {
    	final int chunkX = ChunkUtils.getChunkCoordinate((int) x);
        final int chunkZ = ChunkUtils.getChunkCoordinate((int) z);
        
        final Chunk possibleChunk = this.instance.getChunk(chunkX, chunkZ);
        
        CountDownLatch latch = new CountDownLatch(0);
        
        ChunkCallback task = (newChunk) -> {
			List<BlockData> blocksData = this.data.get(newChunk);
	        if (blocksData == null)
	            blocksData = new ArrayList<>();

	        BlockData blockData = new BlockData();
	        blockData.x = x;
	        blockData.y = y;
	        blockData.z = z;
	        blockData.blockStateId = blockStateId;
	        blockData.customBlockId = customBlockId;
	        blockData.data = data;

	        blocksData.add(blockData);

	        this.data.put(newChunk, blocksData);
	        
	        latch.countDown();
		};
        
        if (possibleChunk != null) {
        	task.accept(possibleChunk);
        }
        
        this.instance.loadChunk(chunkX, chunkZ, task);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public void flush(@Nullable Runnable callback) {
    	this.flush(callback, false);
    }
    
    public void flush(@Nullable Runnable callback, boolean shouldLoadChunks) {
        synchronized (data) {
    		flushBlockData(callback);
        }
    }
    
    private void flushBlockData(@Nullable Runnable callback) {
    	AtomicInteger counter = new AtomicInteger();
        for (Map.Entry<Chunk, List<BlockData>> entry : data.entrySet()) {
            final Chunk chunk = entry.getKey();
            final List<BlockData> dataList = entry.getValue();
            BLOCK_BATCH_POOL.execute(() -> {
                synchronized (chunk) {
                    if (!chunk.isLoaded())
                        return;

                    if (batchOption.isFullChunk()) {
                        chunk.reset();
                    }

                    for (BlockData data : dataList) {
                        data.apply(chunk);
                    }

                    // Refresh chunk for viewers
                    if (batchOption.isFullChunk()) {
                        chunk.sendChunk();
                    } else {
                        chunk.sendChunkUpdate();
                    }

                    final boolean isLast = counter.incrementAndGet() == data.size();

                    // Execute the callback if this was the last chunk to process
                    if (isLast) {
                        this.instance.refreshLastBlockChangeTime();
                        if (callback != null) {
                            this.instance.scheduleNextTick(inst -> callback.run());
                        }
                    }

                }
            });
        }
    }

    private static class BlockData {

        private int x, y, z;
        private short blockStateId;
        private short customBlockId;
        private Data data;

        public void apply(Chunk chunk) {
            chunk.UNSAFE_setBlock(x, y, z, blockStateId, customBlockId, data, CustomBlockUtils.hasUpdate(customBlockId));
        }

    }

}
