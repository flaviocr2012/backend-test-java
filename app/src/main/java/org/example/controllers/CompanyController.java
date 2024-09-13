package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.dtos.request.CompanyRequestDTO;
import org.example.dtos.response.CompanyResponseDTO;
import org.example.exceptions.company.CompanyDeletionException;
import org.example.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Create a new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO createdCompany = companyService.createCompany(companyRequestDTO);
        return ResponseEntity.ok(createdCompany);
    }

    @Operation(summary = "Get a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable Long id) {
        CompanyResponseDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @Operation(summary = "Get all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanies() {
        List<CompanyResponseDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @Operation(summary = "Update a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO updatedCompany = companyService.updateCompany(id, companyRequestDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @Operation(summary = "Delete a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) throws CompanyDeletionException {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
