package org.example.services.impl;

import org.example.dtos.request.VehicleRequestDTO;
import org.example.dtos.response.VehicleResponseDTO;
import org.example.models.Company;
import org.example.models.Vehicle;
import org.example.repositories.CompanyRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.services.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        if (vehicleRepository.existsByPlate(vehicleRequestDTO.getPlate())) {
            throw new IllegalArgumentException("A vehicle with this plate already exists.");
        }

        // Buscar a empresa pelo ID fornecido no DTO
        Company company = companyRepository.findById(vehicleRequestDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found."));

        Vehicle vehicle = modelMapper.map(vehicleRequestDTO, Vehicle.class);
        vehicle.setCompany(company); // Associar a empresa ao veículo

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(savedVehicle, VehicleResponseDTO.class);
    }

    @Override
    public VehicleResponseDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));
        return modelMapper.map(vehicle, VehicleResponseDTO.class);
    }

    @Override
    public List<VehicleResponseDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));

        modelMapper.map(vehicleRequestDTO, vehicle);  // Atualiza a entidade com os novos dados
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleResponseDTO.class);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found.");
        }
        vehicleRepository.deleteById(id);
    }
}

