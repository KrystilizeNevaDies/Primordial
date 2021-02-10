package main;

import java.io.IOException;
import java.util.UUID;

import main.config.DefaultConfig;
import main.config.world.WorldConfig;
import main.generation.CoreGenerator;
import main.minestom.MinestomTranslationLayer;
import main.minestom.MinestomInstance;
import maml.MAMLFile;
import maml.values.MAMLTable;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.world.DimensionType;

public class MinestomServer {
	
	private static WorldConfig config;
	
    public static void start(String[] args) {
    	
    	// Get config
    	MAMLTable config = new MAMLTable();
		try {
			config = MAMLFile.parse(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();
        
        // Create the instance
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        MinestomInstance instance = new MinestomInstance(UUID.randomUUID(), DimensionType.OVERWORLD);
        
        // Register instance
        // Minestom
        instanceManager.registerInstance(instance);
        // Primordial
        CoreGenerator.setWorld(instance);
        
        // Enable the auto chunk loading (when players come close)
        instance.enableAutoChunkLoad(true);

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addEventCallback(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Position(0, 2, 0));
        });
        globalEventHandler.addEventCallback(PlayerSpawnEvent.class, event -> {
        	Player player = event.getPlayer();
        	player.setGameMode(GameMode.CREATIVE);
        	
        	Position playerPos = player.getPosition();
        	
        	while(player.getInstance().getBlock(playerPos.toBlockPosition()) != Block.AIR) {
        		player.getPosition().add(0, 1, 0);
        	}
        	
        });
        
        
        // Register config
        switch (config.getString("Config")) {
        	case "Default":
        		MinestomServer.config = new DefaultConfig();
        		break;
        	// TODO: create other configs
        }
        
        // Set the ChunkGenerator
        ChunkGenerator generator = new MinestomTranslationLayer(MinestomServer.config.getBiomeConfigs(), instance);
        instance.setChunkGenerator(generator);
        
        // Start the server on port 25565
        minecraftServer.start("localhost", 25565);
    }
}