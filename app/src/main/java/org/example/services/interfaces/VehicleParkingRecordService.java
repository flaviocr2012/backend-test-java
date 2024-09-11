package org.example.services.interfaces;

import org.example.dtos.response.VehicleParkingRecordDTO;

public interface VehicleParkingRecordService {
    VehicleParkingRecordDTO registerVehicleEntry(Long vehicleId);
    VehicleParkingRecordDTO registerVehicleExit(Long vehicleId);
}

