package com.energy.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import simplenem12.MeterRead;
import simplenem12.SimpleNem12ParserImpl;

/**
 * Meter Reader File Consumption Service
 * @author Patrick Gueco
 *
 */
@Service
public class MeterReaderService {

    private String dirLocation = "./Dropbox";
    private String processedDirLocation = "./Dropbox/Processed";

    public void ConsumeMeterReaderFile() throws IOException {
    	
    	checkAndCreateDirectory();
    	
    	try {
    		
    		List<File> files = Files.list(Paths.get(dirLocation))
                      .map(Path::toFile)
                      .collect(Collectors.toList());
    		files.forEach(f -> { processFile(f); });
    		
    	} catch (IOException e) {
    		System.out.println("Error while reading the directory.");
    	}
    }
    
    private void checkAndCreateDirectory() throws IOException {
    	Files.createDirectories(Paths.get(dirLocation));
    	Files.createDirectories(Paths.get(processedDirLocation));
    }
    
    private void processFile(File f) {
    	Collection<MeterRead> meterReads = new SimpleNem12ParserImpl().parseSimpleNem12(f);
    	
    	if(meterReads == null) { 
  		  return;
  	  	}
    	
    	moveFilesToProcessedFolder(f.getName());
    }
    
    private void moveFilesToProcessedFolder(String fileName) {
		Path temp = null;
		
		try {
			temp = Files.move
			        (Paths.get(dirLocation + "/" + fileName), 
			        Paths.get(processedDirLocation + "/" + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(temp != null) {
			System.out.println("File renamed and moved successfully");
		} else { 
			System.out.println("Failed to move the file");
		}
    }
    

}
