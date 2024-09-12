package org.example.services.interfaces;

import org.example.dtos.response.GeneralReportDTO;
import org.example.dtos.response.VehicleHourlySummaryDTO;
import org.example.dtos.response.VehicleSummaryDTO;
import org.example.dtos.response.VehicleParkingRecordDTO;

import java.util.List;

public interface VehicleParkingRecordService {
    VehicleParkingRecordDTO registerVehicleEntry(Long vehicleId);
    VehicleParkingRecordDTO registerVehicleExit(Long vehicleId);

    VehicleSummaryDTO getVehicleSummary();
    List<VehicleHourlySummaryDTO> getVehicleHourlySummary();
    GeneralReportDTO generateGeneralReport();
}

