package org.example.repositories;

import org.example.enums.VehicleType;
import org.example.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlate(String plate);

    long countByType(VehicleType vehicleType);

    long countByCompanyId(Long id);
}

