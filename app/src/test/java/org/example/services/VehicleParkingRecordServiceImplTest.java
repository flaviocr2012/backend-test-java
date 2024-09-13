package org.example.services;

import org.example.dtos.response.VehicleParkingRecordDTO;
import org.example.enums.ParkingStatus;
import org.example.exceptions.vehicle.VehicleAlreadyInsideException;
import org.example.exceptions.vehicle.VehicleNotFoundException;
import org.example.models.Vehicle;
import org.example.models.VehicleParkingRecord;
import org.example.repositories.VehicleParkingRecordRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.impl.VehicleParkingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VehicleParkingRecordServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleParkingRecordRepository parkingRecordRepository;

    private Clock clock;

    @Mock
    private ModelMapper modelMapper;

    private VehicleParkingRecordServiceImpl parkingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clock = Clock.fixed(Instant.parse("2024-09-12T21:20:32.190224071Z"), ZoneOffset.UTC);
        modelMapper = new ModelMapper();
        parkingRecordService = new VehicleParkingRecordServiceImpl(vehicleRepository, parkingRecordRepository, modelMapper, clock);
    }


    @Test
    void registerVehicleEntry_ValidVehicle_RegistersEntry() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        LocalDateTime fixedTime = LocalDateTime.ofInstant(clock.instant(), ZoneOffset.UTC);

        VehicleParkingRecord record = new VehicleParkingRecord();
        record.setId(1L);
        record.setVehicle(vehicle);
        record.setStatus(ParkingStatus.IN);
        record.setEntryTime(fixedTime);

        VehicleParkingRecordDTO expectedRecordDTO = new VehicleParkingRecordDTO();
        expectedRecordDTO.setId(1L);
        expectedRecordDTO.setVehicleId(vehicleId);
        expectedRecordDTO.setStatus(ParkingStatus.IN);
        expectedRecordDTO.setEntryTime(fixedTime);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(parkingRecordRepository.findTopByVehicleOrderByEntryTimeDesc(vehicle)).thenReturn(null);
        when(parkingRecordRepository.save(any(VehicleParkingRecord.class))).thenAnswer(invocation -> {
            VehicleParkingRecord savedRecord = invocation.getArgument(0);
            savedRecord.setId(1L);
            return savedRecord;
        });

        VehicleParkingRecordDTO result = parkingRecordService.registerVehicleEntry(vehicleId);

        assertEquals(expectedRecordDTO, result);
        verify(vehicleRepository).findById(vehicleId);
        verify(parkingRecordRepository).findTopByVehicleOrderByEntryTimeDesc(vehicle);
    }


    @Test
    void registerVehicleEntry_VehicleAlreadyInside_ThrowsIllegalStateException() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        VehicleParkingRecord lastRecord = new VehicleParkingRecord();
        lastRecord.setStatus(ParkingStatus.IN);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(parkingRecordRepository.findTopByVehicleOrderByEntryTimeDesc(vehicle)).thenReturn(lastRecord);

        assertThrows(VehicleAlreadyInsideException.class, () -> parkingRecordService.registerVehicleEntry(vehicleId));
        verify(vehicleRepository).findById(vehicleId);
        verify(parkingRecordRepository).findTopByVehicleOrderByEntryTimeDesc(vehicle);
        verify(parkingRecordRepository, never()).save(any(VehicleParkingRecord.class));
    }

    @Test
    void registerVehicleEntry_VehicleNotFound_ThrowsIllegalArgumentException() {
        Long vehicleId = 1L;

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> parkingRecordService.registerVehicleEntry(vehicleId));
        verify(vehicleRepository).findById(vehicleId);
        verify(parkingRecordRepository, never()).findTopByVehicleOrderByEntryTimeDesc(any(Vehicle.class));
        verify(parkingRecordRepository, never()).save(any(VehicleParkingRecord.class));
    }
}