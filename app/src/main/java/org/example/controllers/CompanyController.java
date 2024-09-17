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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company created successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO createdCompany = companyService.createCompany(companyRequestDTO);
        return ResponseEntity.ok(createdCompany);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable Long id) {
        CompanyResponseDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class))
                    })
    })
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanies() {
        List<CompanyResponseDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = CompanyResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })

    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO updatedCompany = companyService.updateCompany(id, companyRequestDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Delete a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class))
                    })
    })
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) throws CompanyDeletionException {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}

