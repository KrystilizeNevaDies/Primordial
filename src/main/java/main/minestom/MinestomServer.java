package main.minestom;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import main.config.DefaultConfig;
import main.config.world.WorldConfig;
import maml.MAMLFile;
import maml.values.MAMLTable;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.location.RelativeBlockPosition;
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
        // TODO: Extract seed to config
        MinestomInstance instance = new MinestomInstance(UUID.randomUUID(), DimensionType.OVERWORLD, new Random().nextInt());
        
        // Register instance
        instanceManager.registerInstance(instance);
        
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
        	
        	int y = 0;
        	
        	while(player.getInstance().getBlock(playerPos.clone().add(0, y, 0).toBlockPosition()) != Block.AIR) {
        		y++;
        	}
        	
        	playerPos.add(0, y, 0);
        	
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
        
        // Load some chunks
        int size = 10;
        for (int x = -size; x < size + 1; x++)
        	for (int z = -size; z < size + 1; z++) {
        		instance.loadChunk(x, z);
        	}
        
        // Register commands
        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new TeleportCommand());
        
        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);
    }
    
    private static class TeleportCommand extends Command {

		public TeleportCommand() {
			super("tp");
			
			ArgumentRelativeBlockPosition position = ArgumentType.RelativeBlockPosition("position");
			
			this.addSyntax(this::execute, position);
		}
		
		private void execute(CommandSender player, Arguments arguments) {
			
			RelativeBlockPosition blockPosition = arguments.get(ArgumentType.RelativeBlockPosition("position"));
			
			player.asPlayer().teleport(blockPosition.fromRelativePosition(player.asPlayer()).toPosition());
		}
    }
}