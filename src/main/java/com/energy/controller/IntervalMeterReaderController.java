package com.energy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class IntervalMeterReaderController {

	@GetMapping("/processNEM12File")
	public void processNEM12File() {
		
	}
}
