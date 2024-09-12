package org.example.controllers;

import org.example.dtos.response.GeneralReportDTO;
import org.example.dtos.response.VehicleHourlySummaryDTO;
import org.example.dtos.response.VehicleSummaryDTO;
import org.example.dtos.response.VehicleParkingRecordDTO;
import org.example.services.interfaces.VehicleParkingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-parking-records")
public class VehicleParkingRecordController {

    @Autowired
    private VehicleParkingRecordService vehicleParkingRecordService;

    @PostMapping("/entry/{id}")
    public ResponseEntity<VehicleParkingRecordDTO> registerEntry(@PathVariable Long id) {
        VehicleParkingRecordDTO record = vehicleParkingRecordService.registerVehicleEntry(id);
        return ResponseEntity.ok(record);
    }

    @PostMapping("/exit/{id}")
    public ResponseEntity<VehicleParkingRecordDTO> registerExit(@PathVariable Long id) {
        VehicleParkingRecordDTO record = vehicleParkingRecordService.registerVehicleExit(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/summary")
    public ResponseEntity<VehicleSummaryDTO> getVehicleSummary() {
        VehicleSummaryDTO summary = vehicleParkingRecordService.getVehicleSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/summary/hourly")
    public ResponseEntity<List<VehicleHourlySummaryDTO>> getVehicleHourlySummary() {
        List<VehicleHourlySummaryDTO> summaries = vehicleParkingRecordService.getVehicleHourlySummary();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/report")
    public ResponseEntity<GeneralReportDTO> getGeneralReport() {
        GeneralReportDTO report = vehicleParkingRecordService.generateGeneralReport();
        return ResponseEntity.ok(report);
    }
}

