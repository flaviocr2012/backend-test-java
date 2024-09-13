package org.example.services;

import org.example.dtos.request.VehicleRequestDTO;
import org.example.dtos.response.VehicleResponseDTO;
import org.example.exceptions.company.CompanyNotFoundException;
import org.example.exceptions.vehicle.VehicleAlreadyExistsException;
import org.example.exceptions.vehicle.VehicleNotFoundException;
import org.example.models.Company;
import org.example.models.Vehicle;
import org.example.repositories.CompanyRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVehicle_ValidRequest_ReturnsVehicleResponseDTO() {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();
        requestDTO.setPlate("ABC123");
        requestDTO.setCompanyId(1L);
        Company company = new Company();
        Vehicle vehicle = new Vehicle();
        Vehicle savedVehicle = new Vehicle();
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        when(vehicleRepository.existsByPlate(requestDTO.getPlate())).thenReturn(false);
        when(companyRepository.findById(requestDTO.getCompanyId())).thenReturn(Optional.of(company));
        when(modelMapper.map(requestDTO, Vehicle.class)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(savedVehicle);
        when(modelMapper.map(savedVehicle, VehicleResponseDTO.class)).thenReturn(responseDTO);

        VehicleResponseDTO result = vehicleServiceImpl.createVehicle(requestDTO);

        assertEquals(responseDTO, result);
        verify(vehicleRepository).existsByPlate(requestDTO.getPlate());
        verify(companyRepository).findById(requestDTO.getCompanyId());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void createVehicle_ExistingPlate_ThrowsVehicleAlreadyExistsException() {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();
        requestDTO.setPlate("ABC123");

        when(vehicleRepository.existsByPlate(requestDTO.getPlate())).thenReturn(true);

        assertThrows(VehicleAlreadyExistsException.class, () -> vehicleServiceImpl.createVehicle(requestDTO));
        verify(vehicleRepository).existsByPlate(requestDTO.getPlate());
        verify(companyRepository, never()).findById(anyLong());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void createVehicle_NonExistingCompany_ThrowsCompanyNotFoundException() {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();
        requestDTO.setPlate("ABC123");
        requestDTO.setCompanyId(1L);

        when(vehicleRepository.existsByPlate(requestDTO.getPlate())).thenReturn(false);
        when(companyRepository.findById(requestDTO.getCompanyId())).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> vehicleServiceImpl.createVehicle(requestDTO));
        verify(vehicleRepository).existsByPlate(requestDTO.getPlate());
        verify(companyRepository).findById(requestDTO.getCompanyId());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void getVehicleById_ExistingId_ReturnsVehicleResponseDTO() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(modelMapper.map(vehicle, VehicleResponseDTO.class)).thenReturn(responseDTO);

        VehicleResponseDTO result = vehicleServiceImpl.getVehicleById(vehicleId);

        assertEquals(responseDTO, result);
        verify(vehicleRepository).findById(vehicleId);
    }

    @Test
    void getVehicleById_NonExistingId_ThrowsVehicleNotFoundException() {
        Long vehicleId = 1L;

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleServiceImpl.getVehicleById(vehicleId));
        verify(vehicleRepository).findById(vehicleId);
    }

    @Test
    void getAllVehicles_ReturnsListOfVehicleResponseDTO() {
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        VehicleResponseDTO responseDTO1 = new VehicleResponseDTO();
        VehicleResponseDTO responseDTO2 = new VehicleResponseDTO();

        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));
        when(modelMapper.map(vehicle1, VehicleResponseDTO.class)).thenReturn(responseDTO1);
        when(modelMapper.map(vehicle2, VehicleResponseDTO.class)).thenReturn(responseDTO2);

        List<VehicleResponseDTO> result = vehicleServiceImpl.getAllVehicles();

        assertEquals(Arrays.asList(responseDTO1, responseDTO2), result);
        verify(vehicleRepository).findAll();
    }

    @Test
    void updateVehicle_ExistingId_UpdatesAndReturnsVehicleResponseDTO() {
        Long vehicleId = 1L;
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();
        Vehicle vehicle = new Vehicle();
        Vehicle updatedVehicle = new Vehicle();
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(updatedVehicle);
        when(modelMapper.map(updatedVehicle, VehicleResponseDTO.class)).thenReturn(responseDTO);

        VehicleResponseDTO result = vehicleServiceImpl.updateVehicle(vehicleId, requestDTO);

        assertEquals(responseDTO, result);
        verify(vehicleRepository).findById(vehicleId);
        verify(modelMapper).map(requestDTO, vehicle);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void updateVehicle_NonExistingId_ThrowsVehicleNotFoundException() {
        Long vehicleId = 1L;
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleServiceImpl.updateVehicle(vehicleId, requestDTO));
        verify(vehicleRepository).findById(vehicleId);
    }

    @Test
    void deleteVehicle_ExistingId_DeletesVehicle() {
        Long vehicleId = 1L;

        when(vehicleRepository.existsById(vehicleId)).thenReturn(true);

        vehicleServiceImpl.deleteVehicle(vehicleId);

        verify(vehicleRepository).existsById(vehicleId);
        verify(vehicleRepository).deleteById(vehicleId);
    }

    @Test
    void deleteVehicle_NonExistingId_ThrowsVehicleNotFoundException() {
        Long vehicleId = 1L;

        when(vehicleRepository.existsById(vehicleId)).thenReturn(false);

        assertThrows(VehicleNotFoundException.class, () -> vehicleServiceImpl.deleteVehicle(vehicleId));
        verify(vehicleRepository).existsById(vehicleId);
        verify(vehicleRepository, never()).deleteById(vehicleId);
    }
}
