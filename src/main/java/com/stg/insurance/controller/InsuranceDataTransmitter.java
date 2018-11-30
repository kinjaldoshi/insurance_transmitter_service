/**
 * 
 */
package com.stg.insurance.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author kinjaldoshi
 *
 */

@RestController
@CrossOrigin
public class InsuranceDataTransmitter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceDataTransmitter.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@PostMapping ("/ediPlatform/transmitData")
    public void transmitData(@RequestHeader ("fileName") String fileName, 
    		@RequestParam(name="file") MultipartFile insuranceData) throws IOException {
		System.out.println("File Name = " + fileName);
        LOGGER.info("Transmission request received...");
        String ediEncrypted = new String(insuranceData.getBytes());
        LOGGER.info("Encrypted contents of the file : \n{}\nTransmitting data to the agency...", ediEncrypted);
        byte[] fileData = insuranceData.getBytes();
        Path path = Files.createTempFile(fileName, "");
        Files.write(path, fileData);
        
        System.out.println("PATH = " + path.getFileName().toString());
        
        
        
        PutObjectRequest request = new PutObjectRequest("insurance-edi-files", "edi-files/kinjal.txt",
        		path.toFile());
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.addUserMetadata("x-amz-meta-title", "EDI Files");
        request.setMetadata(metadata);
        s3client.putObject(request);
    }

}
