package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralReportDTO {
    private long totalVehicles;
    private long totalCars;
    private long totalMotorcycles;
    private long totalEntries;
    private long totalExits;
    private List<VehicleHourlySummaryDTO> hourlySummary;
}

