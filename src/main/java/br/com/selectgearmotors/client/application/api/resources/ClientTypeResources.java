package br.com.selectgearmotors.client.application.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientTypeRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientTypeResponse;
import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.client.application.api.mapper.ClientTypeApiMapper;
import br.com.selectgearmotors.client.commons.Constants;
import br.com.selectgearmotors.client.commons.util.RestUtils;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.in.clienttype.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/client-types")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class ClientTypeResources {

    private final CreateClientTypePort createClientTypePort;
    private final DeleteClientTypePort deleteClientTypePort;
    private final FindByIdClientTypePort findByIdClientTypePort;
    private final FindClientTypesPort findProductCategoriesPort;
    private final UpdateClientTypePort updateClientTypePort;
    private final ClientTypeApiMapper clientTypeApiMapper;

    @Autowired
    public ClientTypeResources(CreateClientTypePort createClientTypePort, DeleteClientTypePort deleteClientTypePort, FindByIdClientTypePort findByIdClientTypePort, FindClientTypesPort findProductCategoriesPort, UpdateClientTypePort updateClientTypePort, ClientTypeApiMapper clientTypeApiMapper) {
        this.createClientTypePort = createClientTypePort;
        this.deleteClientTypePort = deleteClientTypePort;
        this.findByIdClientTypePort = findByIdClientTypePort;
        this.findProductCategoriesPort = findProductCategoriesPort;
        this.updateClientTypePort = updateClientTypePort;
        this.clientTypeApiMapper = clientTypeApiMapper;
    }

    @Operation(summary = "Create a new ClientType", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ClientTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientTypeResponse> save(@Valid @RequestBody ClientTypeRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            ClientType clientType = clientTypeApiMapper.fromRequest(request);
            ClientType saved = createClientTypePort.save(clientType);
            if (saved == null) {
                throw new ResourceNotFoundException("Produto não encontroado ao cadastrar");
            }

            ClientTypeResponse clientTypeResponse = clientTypeApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(clientTypeResponse.getId());
            return ResponseEntity.created(location).body(clientTypeResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a ClientType by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientTypeResponse> update(@PathVariable("id") Long id, @Valid @RequestBody ClientTypeRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = clientTypeApiMapper.fromRequest(request);
            ClientType updated = updateClientTypePort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            ClientTypeResponse clientTypeResponse = clientTypeApiMapper.fromEntity(updated);
            return ResponseEntity.ok(clientTypeResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all ClientType", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientTypeResponse>> findAll() {
        List<ClientType> clientTypeList = findProductCategoriesPort.findAll();
        List<ClientTypeResponse> clientTypeResponse = clientTypeApiMapper.map(clientTypeList);
        return clientTypeResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(clientTypeResponse);
    }

    @Operation(
            summary = "Retrieve a ClientType by Id",
            description = "Get a ClientType object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClientTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientTypeResponse> findOne(@PathVariable("id") Long id) {
        try {
            ClientType clientTypeSaved = findByIdClientTypePort.findById(id);
            if (clientTypeSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            ClientTypeResponse clientTypeResponse = clientTypeApiMapper.fromEntity(clientTypeSaved);
            return ResponseEntity.ok(clientTypeResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a ClientType by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteClientTypePort.remove(id);
        return ResponseEntity.noContent().build();
    }
}