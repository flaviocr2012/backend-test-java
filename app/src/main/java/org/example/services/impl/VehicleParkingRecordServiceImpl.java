package org.example.services.impl;

import org.example.dtos.response.VehicleParkingRecordDTO;
import org.example.enums.ParkingStatus;
import org.example.models.Vehicle;
import org.example.models.VehicleParkingRecord;
import org.example.repositories.VehicleParkingRecordRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.interfaces.VehicleParkingRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VehicleParkingRecordServiceImpl implements VehicleParkingRecordService {

    private final VehicleRepository vehicleRepository;
    private final VehicleParkingRecordRepository parkingRecordRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleParkingRecordServiceImpl(VehicleRepository vehicleRepository, VehicleParkingRecordRepository parkingRecordRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.parkingRecordRepository = parkingRecordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VehicleParkingRecordDTO registerVehicleEntry(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));

        // Verifica se o veículo já está dentro com base no último registro de entrada
        VehicleParkingRecord lastRecord = parkingRecordRepository.findTopByVehicleOrderByEntryTimeDesc(vehicle);
        if (lastRecord != null && lastRecord.getStatus() == ParkingStatus.IN) {
            throw new IllegalStateException("Vehicle is already inside.");
        }

        // Cria um novo registro de entrada
        VehicleParkingRecord record = new VehicleParkingRecord();
        record.setVehicle(vehicle);
        record.setEntryTime(LocalDateTime.now());
        record.setStatus(ParkingStatus.IN);

        parkingRecordRepository.save(record);

        return modelMapper.map(record, VehicleParkingRecordDTO.class);
    }

    @Override
    public VehicleParkingRecordDTO registerVehicleExit(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));

        // Encontra o registro mais recente de entrada do veículo
        VehicleParkingRecord lastRecord = parkingRecordRepository.findTopByVehicleOrderByEntryTimeDesc(vehicle);
        if (lastRecord == null || lastRecord.getStatus() == ParkingStatus.OUT) {
            throw new IllegalStateException("Vehicle is not inside.");
        }

        // Atualiza o registro com o horário de saída
        lastRecord.setExitTime(LocalDateTime.now());
        lastRecord.setStatus(ParkingStatus.OUT);

        parkingRecordRepository.save(lastRecord);

        return modelMapper.map(lastRecord, VehicleParkingRecordDTO.class);
    }
}


