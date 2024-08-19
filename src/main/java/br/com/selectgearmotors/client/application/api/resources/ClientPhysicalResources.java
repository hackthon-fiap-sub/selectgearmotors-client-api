package br.com.selectgearmotors.client.application.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientPhysicalRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientPhysicalResponse;
import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.client.application.api.mapper.ClientPhysicalApiMapper;
import br.com.selectgearmotors.client.commons.Constants;
import br.com.selectgearmotors.client.commons.util.RestUtils;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import br.com.selectgearmotors.client.core.ports.in.clientlegal.*;
import br.com.selectgearmotors.client.core.ports.in.clientphysical.*;
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
@RequestMapping("/v1/client-physicals")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Physical, Authorization", maxAge = 3600)
public class ClientPhysicalResources {

    private final CreateClientPhysicalPort createClientPhysicalPort;
    private final DeleteClientPhysicalPort deleteClientPhysicalPort;
    private final FindByIdClientPhysicalPort findByIdClientPhysicalPort;
    private final FindClientPhysicalsPort findProductCategoriesPort;
    private final UpdateClientPhysicalPort updateClientPhysicalPort;
    private final ClientPhysicalApiMapper clientPhysicalApiMapper;

    @Autowired
    public ClientPhysicalResources(CreateClientPhysicalPort createClientPhysicalPort, DeleteClientPhysicalPort deleteClientPhysicalPort, FindByIdClientPhysicalPort findByIdClientPhysicalPort, FindClientPhysicalsPort findProductCategoriesPort, UpdateClientPhysicalPort updateClientPhysicalPort, ClientPhysicalApiMapper clientPhysicalApiMapper) {
        this.createClientPhysicalPort = createClientPhysicalPort;
        this.deleteClientPhysicalPort = deleteClientPhysicalPort;
        this.findByIdClientPhysicalPort = findByIdClientPhysicalPort;
        this.findProductCategoriesPort = findProductCategoriesPort;
        this.updateClientPhysicalPort = updateClientPhysicalPort;
        this.clientPhysicalApiMapper = clientPhysicalApiMapper;
    }

    @Operation(summary = "Create a new ClientPhysical", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ClientPhysicalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientPhysicalResponse> save(@Valid @RequestBody ClientPhysicalRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            ClientPhysical clientPhysical = clientPhysicalApiMapper.fromRequest(request);
            ClientPhysical saved = createClientPhysicalPort.save(clientPhysical);
            if (saved == null) {
                throw new ResourceNotFoundException("Produto não encontroado ao cadastrar");
            }

            ClientPhysicalResponse clientPhysicalResponse = clientPhysicalApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(clientPhysicalResponse.getId());
            return ResponseEntity.created(location).body(clientPhysicalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a ClientPhysical by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientPhysicalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientPhysicalResponse> update(@PathVariable("id") Long id, @Valid @RequestBody ClientPhysicalRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = clientPhysicalApiMapper.fromRequest(request);
            ClientPhysical updated = updateClientPhysicalPort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            ClientPhysicalResponse clientPhysicalResponse = clientPhysicalApiMapper.fromEntity(updated);
            return ResponseEntity.ok(clientPhysicalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all ClientPhysical", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientPhysicalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientPhysicalResponse>> findAll() {
        List<ClientPhysical> clientPhysicalList = findProductCategoriesPort.findAll();
        List<ClientPhysicalResponse> clientPhysicalResponse = clientPhysicalApiMapper.map(clientPhysicalList);
        return clientPhysicalResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(clientPhysicalResponse);
    }

    @Operation(
            summary = "Retrieve a ClientPhysical by Id",
            description = "Get a ClientPhysical object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClientPhysicalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientPhysicalResponse> findOne(@PathVariable("id") Long id) {
        try {
            ClientPhysical clientPhysicalSaved = findByIdClientPhysicalPort.findById(id);
            if (clientPhysicalSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            ClientPhysicalResponse clientPhysicalResponse = clientPhysicalApiMapper.fromEntity(clientPhysicalSaved);
            return ResponseEntity.ok(clientPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a ClientPhysical by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteClientPhysicalPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}