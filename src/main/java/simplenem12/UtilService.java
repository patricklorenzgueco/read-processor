// Copyright Red Energy Limited 2017

package simplenem12;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility Service for SimpleNem12Parser implementation
 */
public class UtilService {
	
	/**
	 * Convert date string with format yyyyMMdd to LocalDate
	 * @param dateString
	 * @return
	 */
	public static LocalDate StringToLocalDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		
		return localDate;
	}
	
	/**
	 * Read and convert CSV file to List<List<String>>
	 * @param simpleFile
	 * @return
	 */
	public static List<List<String>> ReadFile(File simpleFile){
		
		List<List<String>> records = new ArrayList<>();
		
		Scanner scanner;
		try {
			scanner = new Scanner(simpleFile);
		
		    while (scanner.hasNextLine()) {
		        List<String> row = getRecordFromLine(scanner.nextLine()); 
		    	
		    	records.add(row);
		           
		    }
	    
	    	scanner.close();
	    	
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
			return null;
			
		}
	    return records;
	}
	
	private static List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(",");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
}
