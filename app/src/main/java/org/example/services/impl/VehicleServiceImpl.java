package org.example.services.impl;

import org.example.constants.Constants;
import org.example.dtos.request.VehicleRequestDTO;
import org.example.dtos.response.VehicleResponseDTO;
import org.example.exceptions.company.CompanyNotFoundException;
import org.example.exceptions.vehicle.VehicleAlreadyExistsException;
import org.example.exceptions.vehicle.VehicleNotFoundException;
import org.example.models.Company;
import org.example.models.Vehicle;
import org.example.repositories.CompanyRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.interfaces.VehicleService;
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
            throw new VehicleAlreadyExistsException(Constants.VEHICLE_ALREADY_EXISTS);
        }

        Company company = companyRepository.findById(vehicleRequestDTO.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND));

        Vehicle vehicle = modelMapper.map(vehicleRequestDTO, Vehicle.class);
        vehicle.setCompany(company);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(savedVehicle, VehicleResponseDTO.class);
    }

    @Override
    public VehicleResponseDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND));
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
                .orElseThrow(() -> new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND));

        modelMapper.map(vehicleRequestDTO, vehicle);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleResponseDTO.class);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND);
        }
        vehicleRepository.deleteById(id);
    }
}

