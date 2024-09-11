package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

    private Long id;
    private String name;
    private String cnpj;
    private AddressResponseDTO address;
    private List<PhoneResponseDTO> phones;
    private Integer motorcycleSpots;
    private Integer carSpots;

}
