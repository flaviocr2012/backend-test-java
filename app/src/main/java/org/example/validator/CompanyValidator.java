package org.example.validator;

import org.example.dtos.request.CompanyRequestDTO;
import org.example.exceptions.company.CnpjAlreadyExistsException;
import org.example.exceptions.company.InvalidParkingSpotsException;
import org.example.repositories.CompanyRepository;
import org.example.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void validate(CompanyRequestDTO companyRequestDTO) {
        validateCnpj(companyRequestDTO.getCnpj());
        validateParkingSpots(companyRequestDTO.getMotorcycleSpots(), companyRequestDTO.getCarSpots());
    }

    private void validateCnpj(String cnpj) {
        if (companyRepository.existsByCnpj(cnpj)) {
            throw new CnpjAlreadyExistsException(Constants.CNPJ_ALREADY_EXISTS);
        }
    }

    private void validateParkingSpots(Integer motorcycleSpots, Integer carSpots) {
        if (motorcycleSpots == null || carSpots == null) {
            throw new InvalidParkingSpotsException(Constants.PARKING_SPOTS_NULL);
        }
    }
}
