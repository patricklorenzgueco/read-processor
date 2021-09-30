package com.energy.scheduler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.energy.service.MeterReaderService;

@Configuration
public class MeterReaderScheduler {

	@Autowired
	MeterReaderService meterReaderService;
	
	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskUsingCronExpression() {
	 
		try {
			meterReaderService.ConsumeMeterReaderFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}

}
