package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleHourlySummaryDTO {
    private LocalDateTime hour;
    private long totalEntries;
    private long totalExits;
}

