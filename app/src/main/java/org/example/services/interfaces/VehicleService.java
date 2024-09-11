package org.example.services.interfaces;

import org.example.dtos.request.VehicleRequestDTO;
import org.example.dtos.response.VehicleResponseDTO;

import java.util.List;

public interface VehicleService {
    VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO);
    VehicleResponseDTO getVehicleById(Long id);
    List<VehicleResponseDTO> getAllVehicles();
    VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO vehicleRequestDTO);
    void deleteVehicle(Long id);
}

