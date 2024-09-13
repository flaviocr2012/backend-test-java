package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Register vehicle entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle entry registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleParkingRecordDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/entry/{id}")
    public ResponseEntity<VehicleParkingRecordDTO> registerEntry(@PathVariable Long id) {
        VehicleParkingRecordDTO record = vehicleParkingRecordService.registerVehicleEntry(id);
        return ResponseEntity.ok(record);
    }

    @Operation(summary = "Register vehicle exit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle exit registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleParkingRecordDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/exit/{id}")
    public ResponseEntity<VehicleParkingRecordDTO> registerExit(@PathVariable Long id) {
        VehicleParkingRecordDTO record = vehicleParkingRecordService.registerVehicleExit(id);
        return ResponseEntity.ok(record);
    }

    @Operation(summary = "Get vehicle summary by company ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle summary retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/summary/{companyId}")
    public ResponseEntity<VehicleSummaryDTO> getVehicleSummary(@PathVariable Long companyId) {
        VehicleSummaryDTO summary = vehicleParkingRecordService.getVehicleSummary(companyId);
        return ResponseEntity.ok(summary);
    }

    @Operation(summary = "Get vehicle hourly summary by company ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle hourly summary retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleHourlySummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/summary/hourly/{companyId}")
    public ResponseEntity<List<VehicleHourlySummaryDTO>> getVehicleHourlySummary(@PathVariable Long companyId) {
        List<VehicleHourlySummaryDTO> summaries = vehicleParkingRecordService.getVehicleHourlySummary(companyId);
        return ResponseEntity.ok(summaries);
    }

    @Operation(summary = "Get general report by company ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "General report retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/report/{companyId}")
    public ResponseEntity<GeneralReportDTO> getGeneralReport(@PathVariable Long companyId) {
        GeneralReportDTO report = vehicleParkingRecordService.generateGeneralReport(companyId);
        return ResponseEntity.ok(report);
    }

}

