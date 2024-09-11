package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDTO {

    private Long id;
    private String brand;
    private String model;
    private String color;
    private String plate;
    private VehicleType type;
    private CompanyResponseDTO company;

}
