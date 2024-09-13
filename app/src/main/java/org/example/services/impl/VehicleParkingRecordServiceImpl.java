package org.example.services.impl;

import org.example.constants.Constants;
import org.example.dtos.response.GeneralReportDTO;
import org.example.dtos.response.VehicleHourlySummaryDTO;
import org.example.dtos.response.VehicleSummaryDTO;
import org.example.dtos.response.VehicleParkingRecordDTO;
import org.example.enums.ParkingStatus;
import org.example.enums.VehicleType;
import org.example.exceptions.vehicle.VehicleAlreadyInsideException;
import org.example.exceptions.vehicle.VehicleNotFoundException;
import org.example.exceptions.vehicle.VehicleNotInsideException;
import org.example.models.Vehicle;
import org.example.models.VehicleParkingRecord;
import org.example.repositories.VehicleParkingRecordRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.interfaces.VehicleParkingRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleParkingRecordServiceImpl implements VehicleParkingRecordService {

    private final VehicleRepository vehicleRepository;
    private final VehicleParkingRecordRepository parkingRecordRepository;
    private final ModelMapper modelMapper;
    private final Clock clock;

    @Autowired
    public VehicleParkingRecordServiceImpl(VehicleRepository vehicleRepository, VehicleParkingRecordRepository parkingRecordRepository, ModelMapper modelMapper, Clock clock) {
        this.vehicleRepository = vehicleRepository;
        this.parkingRecordRepository = parkingRecordRepository;
        this.modelMapper = modelMapper;
        this.clock = clock;
    }

    @Override
    public VehicleParkingRecordDTO registerVehicleEntry(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND));

        VehicleParkingRecord lastRecord = parkingRecordRepository.findTopByVehicleOrderByEntryTimeDesc(vehicle);
        if (lastRecord != null && lastRecord.getStatus() == ParkingStatus.IN) {
            throw new VehicleAlreadyInsideException(Constants.VEHICLE_ALREADY_INSIDE);
        }

        VehicleParkingRecord record = new VehicleParkingRecord();
        record.setVehicle(vehicle);
        record.setEntryTime(LocalDateTime.now(clock));
        record.setStatus(ParkingStatus.IN);

        parkingRecordRepository.save(record);

        return modelMapper.map(record, VehicleParkingRecordDTO.class);
    }

    @Override
    public VehicleParkingRecordDTO registerVehicleExit(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND));

        VehicleParkingRecord lastRecord = parkingRecordRepository.findTopByVehicleOrderByEntryTimeDesc(vehicle);
        if (lastRecord == null || lastRecord.getStatus() == ParkingStatus.OUT) {
            throw new VehicleNotInsideException(Constants.VEHICLE_NOT_INSIDE);
        }

        lastRecord.setExitTime(LocalDateTime.now());
        lastRecord.setStatus(ParkingStatus.OUT);

        parkingRecordRepository.save(lastRecord);

        return modelMapper.map(lastRecord, VehicleParkingRecordDTO.class);
    }

    @Override
    public VehicleSummaryDTO getVehicleSummary(Long companyId) {
        long totalEntries = parkingRecordRepository.countByStatusAndVehicle_Company_Id(ParkingStatus.IN, companyId);
        long totalExits = parkingRecordRepository.countByStatusAndVehicle_Company_Id(ParkingStatus.OUT, companyId);

        return new VehicleSummaryDTO(totalEntries, totalExits);
    }

    @Override
    public List<VehicleHourlySummaryDTO> getVehicleHourlySummary(Long companyId) {
        List<VehicleHourlySummaryDTO> summaries = parkingRecordRepository.findHourlySummaryByCompanyId(companyId);
        return summaries;
    }

    @Override
    public GeneralReportDTO generateGeneralReport(Long companyId) {
        long totalVehicles = parkingRecordRepository.countVehiclesByCompanyId(companyId);
        long totalCars = parkingRecordRepository.countVehiclesByTypeAndCompanyId(companyId, VehicleType.CAR);
        long totalMotorcycles = parkingRecordRepository.countVehiclesByTypeAndCompanyId(companyId, VehicleType.MOTORCYCLE);
        long totalEntries = parkingRecordRepository.countByStatusAndVehicle_Company_Id(ParkingStatus.IN, companyId);
        long totalExits = parkingRecordRepository.countByStatusAndVehicle_Company_Id(ParkingStatus.OUT, companyId);
        List<VehicleHourlySummaryDTO> hourlySummary = parkingRecordRepository.findHourlySummaryByCompanyId(companyId);

        return new GeneralReportDTO(totalVehicles, totalCars, totalMotorcycles, totalEntries, totalExits, hourlySummary);
    }
}



