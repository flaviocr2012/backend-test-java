package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.dtos.request.VehicleRequestDTO;
import org.example.dtos.response.VehicleResponseDTO;
import org.example.services.interfaces.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a new vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle created successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO createdVehicle = vehicleService.createVehicle(vehicleRequestDTO);
        return ResponseEntity.ok(createdVehicle);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get a vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) {
        VehicleResponseDTO vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get all vehicles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class))
                    })
    })
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update a vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable Long id, @RequestBody @Validated VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO updatedVehicle = vehicleService.updateVehicle(id, vehicleRequestDTO);
        return ResponseEntity.ok(updatedVehicle);
    }
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Delete a vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}


