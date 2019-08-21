package com.dom.wiremocksample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
public class MyController {

	@PostMapping("/devices")
	public ResponseEntity createDevice(DeviceRequest deviceRequest) {
		return (ResponseEntity) ResponseEntity.created(URI.create("http://localhost:8080/device/" + UUID.randomUUID().toString()));
	}

	@GetMapping("/devices/{deviceId}")
	// >>> Breaking changes
	//public ResponseEntity<DeviceResource> createDevice(@PathVariable long deviceId) {
	public ResponseEntity<DeviceResource> getDevice(@PathVariable String deviceId) {
		var deviceResource = new DeviceResource();
		deviceResource.id = deviceId;
		deviceResource.tenant = "tenant";
		return ResponseEntity.ok(deviceResource);
	}
}
