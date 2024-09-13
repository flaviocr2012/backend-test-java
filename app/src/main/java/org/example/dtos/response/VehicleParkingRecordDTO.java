package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ParkingStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleParkingRecordDTO {

    private Long id;
    private Long vehicleId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private ParkingStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleParkingRecordDTO that = (VehicleParkingRecordDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(vehicleId, that.vehicleId) && Objects.equals(entryTime, that.entryTime) && Objects.equals(exitTime, that.exitTime) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleId, entryTime, exitTime, status);
    }
}

