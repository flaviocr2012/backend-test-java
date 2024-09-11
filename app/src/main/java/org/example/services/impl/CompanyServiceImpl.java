package org.example.services.impl;

import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;
import org.example.models.Company;
import org.example.models.Phone;
import org.example.repositories.CompanyRepository;
import org.example.services.interfaces.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO) {

        Company company = modelMapper.map(companyRequestDTO, Company.class);

        // Assegurar que cada telefone esteja associado à empresa corretamente
        if (company.getPhones() != null) {
            for (Phone phone : company.getPhones()) {
                phone.setCompany(company);
            }
        }

        // Verifique se os valores de motorcycleSpots e carSpots estão sendo corretamente populados
        if (company.getMotorCycleParkingSpot() == null || company.getCarParkingSpot() == null) {
            throw new IllegalArgumentException("Parking spots cannot be null");
        }

        Company savedCompany = companyRepository.save(company);
        return modelMapper.map(savedCompany, CompanyResponseDTO.class);
    }

    @Override
    public CompanyResponseDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        return modelMapper.map(company, CompanyResponseDTO.class);
    }

    @Override
    public List<CompanyResponseDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO companyRequestDTO) {
        Company existingCompany = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        modelMapper.map(companyRequestDTO, existingCompany);
        Company updatedCompany = companyRepository.save(existingCompany);
        return modelMapper.map(updatedCompany, CompanyResponseDTO.class);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
