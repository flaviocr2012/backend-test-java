package org.example.config;

import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;
import org.example.models.Company;
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

        // Configurando manualmente o mapeamento para os campos do RequestDTO para a entidade Company
        TypeMap<CompanyRequestDTO, Company> requestTypeMap = modelMapper.createTypeMap(CompanyRequestDTO.class, Company.class);
        requestTypeMap.addMappings(mapper -> {
            mapper.map(CompanyRequestDTO::getMotorcycleSpots, Company::setMotorCycleParkingSpot);
            mapper.map(CompanyRequestDTO::getCarSpots, Company::setCarParkingSpot);
        });

        // Configurando manualmente o mapeamento para os campos da entidade Company para o ResponseDTO
        TypeMap<Company, CompanyResponseDTO> responseTypeMap = modelMapper.createTypeMap(Company.class, CompanyResponseDTO.class);
        responseTypeMap.addMappings(mapper -> {
            mapper.map(Company::getMotorCycleParkingSpot, CompanyResponseDTO::setMotorcycleSpots);
            mapper.map(Company::getCarParkingSpot, CompanyResponseDTO::setCarSpots);
        });

        return modelMapper;
    }
}


