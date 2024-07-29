package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.QrDTO;
import com.exam.service.QrService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class QrController {
	
	private QrService qrService; // QrService와 연동

	public QrController(QrService qrService) {
		this.qrService = qrService;
	}
	
//	@GetMapping("/main/qrscan")
//	public void saveQrScanData(@RequestParam String data) {
	@PostMapping("/main/qrscan")
	public void saveQrScanData(@RequestBody QrDTO qrDTO) {
		log.info("XXXX:{}", qrDTO);
		qrService.saveQrData(qrDTO);
	}
	
	

}
