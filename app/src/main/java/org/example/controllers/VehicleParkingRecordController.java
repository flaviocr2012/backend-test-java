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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-parking-records")
public class VehicleParkingRecordController {

    private final VehicleParkingRecordService vehicleParkingRecordService;

    @Autowired
    public VehicleParkingRecordController(VehicleParkingRecordService vehicleParkingRecordService) {
        this.vehicleParkingRecordService = vehicleParkingRecordService;
    }

    @PostMapping(value = "/entry/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Register vehicle entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle entry registered successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleParkingRecordDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleParkingRecordDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })

    public ResponseEntity<VehicleParkingRecordDTO> registerEntry(@PathVariable Long id) {
        VehicleParkingRecordDTO record = vehicleParkingRecordService.registerVehicleEntry(id);
        return ResponseEntity.ok(record);
    }

    @PostMapping(value = "/exit/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Register vehicle exit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle exit registered successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleParkingRecordDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleParkingRecordDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })

    public ResponseEntity<VehicleParkingRecordDTO> registerExit(@PathVariable Long id) {
        VehicleParkingRecordDTO record = vehicleParkingRecordService.registerVehicleExit(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping(value = "/summary/{companyId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get vehicle summary by company ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle summary retrieved successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleSummaryDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleSummaryDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })

    public ResponseEntity<VehicleSummaryDTO> getVehicleSummary(@PathVariable Long companyId) {
        VehicleSummaryDTO summary = vehicleParkingRecordService.getVehicleSummary(companyId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping(value = "/summary/hourly/{companyId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get vehicle hourly summary by company ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle hourly summary retrieved successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleHourlySummaryDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleHourlySummaryDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })

    public ResponseEntity<List<VehicleHourlySummaryDTO>> getVehicleHourlySummary(@PathVariable Long companyId) {
        List<VehicleHourlySummaryDTO> summaries = vehicleParkingRecordService.getVehicleHourlySummary(companyId);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping(value = "/report/{companyId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get general report by company ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "General report retrieved successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GeneralReportDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = GeneralReportDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })

    public ResponseEntity<GeneralReportDTO> getGeneralReport(@PathVariable Long companyId) {
        GeneralReportDTO report = vehicleParkingRecordService.generateGeneralReport(companyId);
        return ResponseEntity.ok(report);
    }

}


