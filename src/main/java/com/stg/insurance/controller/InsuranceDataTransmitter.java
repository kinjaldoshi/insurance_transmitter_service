/**
 * 
 */
package com.stg.insurance.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kinjaldoshi
 *
 */

@RestController
@CrossOrigin
public class InsuranceDataTransmitter {
	
	@PostMapping ("/ediPlatform/transmitData")
	public void transmitData(@RequestBody String insuranceData) {
		
		
		
	}

}
