package org.example.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    private String brand;
    private String model;
    private String color;
    private String plate;
    private VehicleType type;

}
