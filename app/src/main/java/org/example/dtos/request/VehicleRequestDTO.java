package org.example.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    @NotNull(message = "Brand cannot be null")
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    @NotNull(message = "Model cannot be null")
    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @NotNull(message = "Color cannot be null")
    @NotEmpty(message = "Color cannot be empty")
    private String color;

    @NotNull(message = "Plate cannot be null")
    @NotEmpty(message = "Plate cannot be empty")
    private String plate;

    @NotNull(message = "Type cannot be null")
    private VehicleType type;

    @NotNull(message = "Company ID cannot be null")
    private Long companyId;

}
