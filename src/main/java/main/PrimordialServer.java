package main;

import java.io.File;
import java.io.IOException;

import maml.MAMLFile;
import maml.values.MAMLString;
import maml.values.MAMLTable;

public class PrimordialServer {
    public static void main(String[] args) {
    	
    	MAMLTable config = new MAMLTable();
    	
    	// Check args for config
    	for (int i = 0; i < args.length; i++) {
    		if (args[i].equals("-config")) {
    			try {
					config = MAMLFile.parse(new File(args[i + 1]));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    	// Ensure minimal config is implemented
    	
    	if (!config.exists(new MAMLString("Engine"))) {
    		config.set(new MAMLString("Engine"), new MAMLString("Minestom"));
    	}
    	
    	if (!config.exists(new MAMLString("Config"))) {
    		config.set(new MAMLString("Config"), new MAMLString("Default"));
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
