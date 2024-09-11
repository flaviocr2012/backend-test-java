package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ParkingStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleParkingRecordDTO {

    private Long id;
    private Long vehicleId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private ParkingStatus status;
}

