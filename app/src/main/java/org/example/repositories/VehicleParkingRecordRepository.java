package org.example.repositories;

import org.example.enums.ParkingStatus;
import org.example.models.Vehicle;
import org.example.models.VehicleParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface VehicleParkingRecordRepository extends JpaRepository<VehicleParkingRecord, Long> {
    VehicleParkingRecord findTopByVehicleOrderByEntryTimeDesc(Vehicle vehicle);
}


