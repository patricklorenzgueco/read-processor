// Copyright Red Energy Limited 2017

package simplenem12;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SimpleNem12ParserImpl implements SimpleNem12Parser{
	
	private final String START_FILE_CODE = "100";
	private final String END_FILE_CODE = "900";
	private final String METER_BLOCK_CODE = "200";
	private final String VOLUME_CODE = "300";
	
	public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) {
		
		List<List<String>> records = UtilService.ReadFile(simpleNem12File);

		if(!isValidNEM12File(records)) {
			return null;
		}
		
		Collection<MeterRead> meterReadCollection = new ArrayList<MeterRead>();
		MeterRead currentMeter = null;
		
		for (List<String> rows : records) {
			if(rows == null || rows.size() < 1) {
				continue;
			}
			
			String recordType = rows.get(0);
			
			switch(recordType) {
				case START_FILE_CODE:
				case END_FILE_CODE: 
					continue;
				case METER_BLOCK_CODE:
					MeterRead mr = processMeterBlock(rows, meterReadCollection, currentMeter);
					currentMeter = mr != null ? mr : currentMeter;
					break;
				case VOLUME_CODE:
					processVolume(rows, currentMeter);
					break;
				default: break;
			}
			
		}
		
		printMeterReadCollection(meterReadCollection);
		
		return meterReadCollection;  
	}
	
	private void printMeterReadCollection(Collection<MeterRead> meterReadCollection) {
		for(MeterRead mr : meterReadCollection) {
			System.out.print(mr.getNmi());
			System.out.print(" ");
			System.out.println(mr.getEnergyUnit().toString());
			
			Iterator<LocalDate> iterator = mr.getVolumes().keySet().iterator();

			while(iterator.hasNext()) {
				LocalDate key   = (LocalDate) iterator.next();
				MeterVolume mv = (MeterVolume) mr.getVolumes().get(key);
				
				System.out.print(key + " ");
				System.out.print(mv.getVolume() + " ");
				System.out.println(mv.getQuality() + " ");
			}

		}
	}
	private void processVolume(List<String> rows, MeterRead currentMeter) {
		
		if(currentMeter == null || rows.size() < 4) {
			return;
		}
		
		LocalDate volumeDate = UtilService.StringToLocalDate(rows.get(1));
		BigDecimal volume = new BigDecimal(rows.get(2));
		Quality quality = Quality.valueOf(rows.get(3));
		
		
		SortedMap<LocalDate, MeterVolume> volumes = currentMeter.getVolumes();
		MeterVolume mv = new MeterVolume(volume, quality);
		
		volumes.put(volumeDate, mv);
	}
	private MeterRead processMeterBlock(List<String> rows, Collection<MeterRead> meterReadCollection, MeterRead currentMeter) {
		if(rows.size() < 3) {
			return null;
		}
		
		String nmi = rows.get(1);
		EnergyUnit energyUnit = EnergyUnit.valueOf(rows.get(2));
						
		currentMeter = new MeterRead(nmi, energyUnit);
		SortedMap<LocalDate, MeterVolume> volumes = new TreeMap<LocalDate, MeterVolume>();
		
		currentMeter.setVolumes(volumes);
		meterReadCollection.add(currentMeter);
		
		return currentMeter;
		
	}
	private Boolean isValidNEM12File(List<List<String>> records) {
		if(records == null || records.isEmpty()) {
			System.out.println("NEM12 File is empty.");
			return false;
		} else if(records.get(0) == null || 
				records.get(0).isEmpty() || 
				records.get(0).get(0) == null || 
				!records.get(0).get(0).equals(START_FILE_CODE)) {
			System.out.println("Invalid NEM12 File. Invalid first line.");
			return false;	
		} else {
			int lastIndex = records.size() - 1;
			if(records.get(lastIndex) == null || 
					records.get(lastIndex).isEmpty() || 
					records.get(lastIndex).get(0) == null || 
					!records.get(lastIndex).get(0).equals(END_FILE_CODE)) {
				System.out.println("Invalid NEM12 File. Invalid last line.");
				return false;	
			}
		}
		
		return true;
	}


}
