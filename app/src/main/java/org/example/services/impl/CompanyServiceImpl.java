package org.example.services.impl;

import org.example.constants.Constants;
import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;
import org.example.exceptions.company.CompanyDeletionException;
import org.example.exceptions.company.CompanyNotFoundException;
import org.example.models.Company;
import org.example.models.Phone;
import org.example.repositories.CompanyRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.interfaces.CompanyService;
import org.example.validator.CompanyValidator;
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
    private ModelMapper modelMapper;

    @Autowired
    private CompanyValidator companyValidator;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO) {

        companyValidator.validate(companyRequestDTO);

        Company company = modelMapper.map(companyRequestDTO, Company.class);

        if (company.getPhones() != null) {
            for (Phone phone : company.getPhones()) {
                phone.setCompany(company);
            }
        }

        Company savedCompany = companyRepository.save(company);
        return modelMapper.map(savedCompany, CompanyResponseDTO.class);
    }
    @Override
    public CompanyResponseDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND));
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
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND));
        modelMapper.map(companyRequestDTO, existingCompany);
        Company updatedCompany = companyRepository.save(existingCompany);
        return modelMapper.map(updatedCompany, CompanyResponseDTO.class);
    }

    @Override
    public void deleteCompany(Long id) throws CompanyDeletionException {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND));

        long vehicleCount = vehicleRepository.countByCompanyId(id);
        if (vehicleCount > 0) {
            throw new CompanyDeletionException(Constants.COMPANY_HAS_VEHICLES);
        }

        companyRepository.delete(company);
    }


}

