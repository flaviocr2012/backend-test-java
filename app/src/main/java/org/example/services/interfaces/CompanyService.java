package org.example.services.interfaces;

import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {

    CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);

    CompanyResponseDTO getCompanyById(Long id);

    List<CompanyResponseDTO> getAllCompanies();

    CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO companyRequestDTO);

    void deleteCompany(Long id);

}
