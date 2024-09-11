package org.example.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "CNPJ cannot be null")
    @NotEmpty(message = "CNPJ cannot be empty")
    private String cnpj;

    @NotNull(message = "Address cannot be null")
    private AddressRequestDTO address;

    @NotNull(message = "Phones cannot be null")
    @NotEmpty(message = "Phones cannot be empty")
    private List<PhoneRequestDTO> phones;

    @NotNull(message = "Motorcycle spots cannot be null")
    @Min(1)
    private Integer motorcycleSpots;

    @NotNull(message = "Car spots cannot be null")
    @Min(1)
    private Integer carSpots;
}
