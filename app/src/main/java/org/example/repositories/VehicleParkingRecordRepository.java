package org.example.repositories;

import org.example.dtos.response.VehicleHourlySummaryDTO;
import org.example.enums.ParkingStatus;
import org.example.enums.VehicleType;
import org.example.models.Vehicle;
import org.example.models.VehicleParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface VehicleParkingRecordRepository extends JpaRepository<VehicleParkingRecord, Long> {

    VehicleParkingRecord findTopByVehicleOrderByEntryTimeDesc(Vehicle vehicle);

    long countByStatusAndVehicle_Company_Id(ParkingStatus parkingStatus, Long companyId);

    @Query("SELECT new org.example.dtos.response.VehicleHourlySummaryDTO(" +
            "FUNCTION('date_trunc', 'hour', v.entryTime), " +
            "SUM(CASE WHEN v.status = 'IN' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN v.status = 'OUT' THEN 1 ELSE 0 END)) " +
            "FROM VehicleParkingRecord v " +
            "WHERE v.vehicle.company.id = :companyId " +
            "GROUP BY FUNCTION('date_trunc', 'hour', v.entryTime)")
    List<VehicleHourlySummaryDTO> findHourlySummaryByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.company.id = :companyId")
    long countVehiclesByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.company.id = :companyId AND v.type = :type")
    long countVehiclesByTypeAndCompanyId(@Param("companyId") Long companyId, @Param("type") VehicleType type);
}



