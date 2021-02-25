package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import main.minestom.MinestomServer;
import maml.MAMLFile;
import maml.values.MAMLString;
import maml.values.MAMLTable;

public class PrimordialServer {
    public static void main(String[] args) {
    	
    	MAMLTable config = new MAMLTable();
    	
    	// Check for config
    	File configFile = new File("Config.maml");
    	
    	if (configFile.isFile())
		try {
			config = MAMLFile.parse(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// Ensure minimal config is implemented
    	if (!config.exists(new MAMLString("Engine"))) {
    		config.set(new MAMLString("Engine"), new MAMLString("Minestom"));
    	}
    	
    	if (!config.exists(new MAMLString("Config"))) {
    		config.set(new MAMLString("Config"), new MAMLString("Default"));
    	}
    	
    	// Try to save config file
    	try {
    		configFile.createNewFile();
    		
			FileWriter writer = new FileWriter(configFile);
			
			writer.write(config.toFileString());
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	switch(config.getString("Engine")) {
    		case "Minestom":
    			// Start minestom server
    			MinestomServer.start(new String[] {config.toFileString()});
    			break;
    		default:
    			// Error, engine not found 
    			System.out.println("Unknown Engine: " + config.getString("Engine"));
    			break;
    	}
    }
}
