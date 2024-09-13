package org.example.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;
import org.example.exceptions.company.CompanyDeletionException;
import org.example.exceptions.company.CompanyNotFoundException;
import org.example.models.Company;
import org.example.models.Phone;
import org.example.repositories.CompanyRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.impl.CompanyServiceImpl;
import org.example.validator.CompanyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class CompanyServiceImplTest {

    @Mock
    private CompanyValidator companyValidator;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCompany_ValidRequest_ReturnsCompanyResponseDTO() {
        CompanyRequestDTO requestDTO = new CompanyRequestDTO();
        Company company = new Company();
        Company savedCompany = new Company();
        CompanyResponseDTO responseDTO = new CompanyResponseDTO();

        when(modelMapper.map(requestDTO, Company.class)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(savedCompany);
        when(modelMapper.map(savedCompany, CompanyResponseDTO.class)).thenReturn(responseDTO);

        CompanyResponseDTO result = companyServiceImpl.createCompany(requestDTO);

        assertEquals(responseDTO, result);
        verify(companyValidator).validate(requestDTO);
        verify(companyRepository).save(company);
    }

    @Test
    void createCompany_NullPhones_DoesNotThrowException() {
        CompanyRequestDTO requestDTO = new CompanyRequestDTO();
        Company company = new Company();
        company.setPhones(null);
        Company savedCompany = new Company();
        CompanyResponseDTO responseDTO = new CompanyResponseDTO();

        when(modelMapper.map(requestDTO, Company.class)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(savedCompany);
        when(modelMapper.map(savedCompany, CompanyResponseDTO.class)).thenReturn(responseDTO);

        assertDoesNotThrow(() -> companyServiceImpl.createCompany(requestDTO));
    }

    @Test
    void createCompany_PhonesSetCompanyReference() {
        CompanyRequestDTO requestDTO = new CompanyRequestDTO();
        Company company = new Company();
        Phone phone = new Phone();
        company.setPhones(List.of(phone));
        Company savedCompany = new Company();
        CompanyResponseDTO responseDTO = new CompanyResponseDTO();

        when(modelMapper.map(requestDTO, Company.class)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(savedCompany);
        when(modelMapper.map(savedCompany, CompanyResponseDTO.class)).thenReturn(responseDTO);

        companyServiceImpl.createCompany(requestDTO);

        assertEquals(company, phone.getCompany());
    }

    @Test
    void getCompanyById_ExistingId_ReturnsCompanyResponseDTO() {
        Long companyId = 1L;
        Company company = new Company();
        CompanyResponseDTO responseDTO = new CompanyResponseDTO();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(modelMapper.map(company, CompanyResponseDTO.class)).thenReturn(responseDTO);

        CompanyResponseDTO result = companyServiceImpl.getCompanyById(companyId);

        assertEquals(responseDTO, result);
        verify(companyRepository).findById(companyId);
    }

    @Test
    void getCompanyById_NonExistingId_ThrowsCompanyNotFoundException() {
        Long companyId = 1L;

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyServiceImpl.getCompanyById(companyId));
        verify(companyRepository).findById(companyId);
    }

    @Test
    void getAllCompanies_ReturnsListOfCompanyResponseDTOs() {
        List<Company> companies = List.of(new Company(), new Company());
        List<CompanyResponseDTO> responseDTOs = List.of(new CompanyResponseDTO(), new CompanyResponseDTO());

        when(companyRepository.findAll()).thenReturn(companies);
        when(modelMapper.map(any(Company.class), eq(CompanyResponseDTO.class)))
                .thenReturn(responseDTOs.get(0), responseDTOs.get(1));

        List<CompanyResponseDTO> result = companyServiceImpl.getAllCompanies();

        assertEquals(responseDTOs, result);
        verify(companyRepository).findAll();
    }

    @Test
    void updateCompany_ExistingId_ValidRequest_ReturnsUpdatedCompanyResponseDTO() {
        Long companyId = 1L;
        CompanyRequestDTO requestDTO = new CompanyRequestDTO();
        Company existingCompany = new Company();
        Company updatedCompany = new Company();
        CompanyResponseDTO responseDTO = new CompanyResponseDTO();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(existingCompany)).thenReturn(updatedCompany);
        when(modelMapper.map(updatedCompany, CompanyResponseDTO.class)).thenReturn(responseDTO);

        CompanyResponseDTO result = companyServiceImpl.updateCompany(companyId, requestDTO);

        assertEquals(responseDTO, result);
        verify(companyRepository).findById(companyId);
        verify(companyRepository).save(existingCompany);
    }

    @Test
    void updateCompany_NonExistingId_ThrowsCompanyNotFoundException() {
        Long companyId = 1L;
        CompanyRequestDTO requestDTO = new CompanyRequestDTO();

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyServiceImpl.updateCompany(companyId, requestDTO));
        verify(companyRepository).findById(companyId);
    }

    @Test
    void deleteCompany_ExistingId_NoVehicles_DeletesCompany() {
        Long companyId = 1L;
        Company company = new Company();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(vehicleRepository.countByCompanyId(companyId)).thenReturn(0L);

        assertDoesNotThrow(() -> companyServiceImpl.deleteCompany(companyId));
        verify(companyRepository).delete(company);
    }

    @Test
    void deleteCompany_ExistingId_HasVehicles_ThrowsCompanyDeletionException() {
        Long companyId = 1L;
        Company company = new Company();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(vehicleRepository.countByCompanyId(companyId)).thenReturn(1L);

        assertThrows(CompanyDeletionException.class, () -> companyServiceImpl.deleteCompany(companyId));
        verify(companyRepository, never()).delete(company);
    }

    @Test
    void deleteCompany_NonExistingId_ThrowsCompanyNotFoundException() {
        Long companyId = 1L;

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyServiceImpl.deleteCompany(companyId));
        verify(companyRepository, never()).delete(any(Company.class));
    }
}