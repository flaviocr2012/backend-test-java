package org.example.configs;

import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.request.VehicleRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;
import org.example.dtos.response.VehicleParkingRecordDTO;
import org.example.dtos.response.VehicleResponseDTO;
import org.example.models.Company;
import org.example.models.Vehicle;
import org.example.models.VehicleParkingRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<CompanyRequestDTO, Company> requestTypeMap = modelMapper.createTypeMap(CompanyRequestDTO.class, Company.class);
        requestTypeMap.addMappings(mapper -> {
            mapper.map(CompanyRequestDTO::getMotorcycleSpots, Company::setMotorCycleParkingSpot);
            mapper.map(CompanyRequestDTO::getCarSpots, Company::setCarParkingSpot);
        });

        TypeMap<Company, CompanyResponseDTO> responseTypeMap = modelMapper.createTypeMap(Company.class, CompanyResponseDTO.class);
        responseTypeMap.addMappings(mapper -> {
            mapper.map(Company::getMotorCycleParkingSpot, CompanyResponseDTO::setMotorcycleSpots);
            mapper.map(Company::getCarParkingSpot, CompanyResponseDTO::setCarSpots);
        });

        TypeMap<VehicleRequestDTO, Vehicle> vehicleRequestTypeMap = modelMapper.createTypeMap(VehicleRequestDTO.class, Vehicle.class);
        vehicleRequestTypeMap.addMappings(mapper -> {
            mapper.map(VehicleRequestDTO::getBrand, Vehicle::setBrand);
            mapper.map(VehicleRequestDTO::getModel, Vehicle::setModel);
            mapper.map(VehicleRequestDTO::getColor, Vehicle::setColor);
            mapper.map(VehicleRequestDTO::getPlate, Vehicle::setPlate);
            mapper.map(VehicleRequestDTO::getType, Vehicle::setType);
        });

        TypeMap<Vehicle, VehicleResponseDTO> vehicleResponseTypeMap = modelMapper.createTypeMap(Vehicle.class, VehicleResponseDTO.class);
        vehicleResponseTypeMap.addMappings(mapper -> {
            mapper.map(Vehicle::getBrand, VehicleResponseDTO::setBrand);
            mapper.map(Vehicle::getModel, VehicleResponseDTO::setModel);
            mapper.map(Vehicle::getColor, VehicleResponseDTO::setColor);
            mapper.map(Vehicle::getPlate, VehicleResponseDTO::setPlate);
            mapper.map(Vehicle::getType, VehicleResponseDTO::setType);
        });

        TypeMap<VehicleParkingRecord, VehicleParkingRecordDTO> recordMap = modelMapper.createTypeMap(VehicleParkingRecord.class, VehicleParkingRecordDTO.class);
        recordMap.addMappings(mapper -> {
                       mapper.map(src -> src.getVehicle().getId(), VehicleParkingRecordDTO::setVehicleId);
            mapper.map(VehicleParkingRecord::getEntryTime, VehicleParkingRecordDTO::setEntryTime);
            mapper.map(VehicleParkingRecord::getExitTime, VehicleParkingRecordDTO::setExitTime);
            mapper.map(VehicleParkingRecord::getStatus, VehicleParkingRecordDTO::setStatus);
        });

        return modelMapper;
    }
}



