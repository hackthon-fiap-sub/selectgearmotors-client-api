package br.com.selectgearmotors.client.application.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientLegalRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientLegalResponse;
import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.client.application.api.mapper.ClientLegalApiMapper;
import br.com.selectgearmotors.client.commons.Constants;
import br.com.selectgearmotors.client.commons.util.RestUtils;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import br.com.selectgearmotors.client.core.ports.in.clientlegal.*;
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
@RequestMapping("/v1/client-legals")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Legal, Authorization", maxAge = 3600)
public class ClientLegalResources {

    private final CreateClientLegalPort createClientLegalPort;
    private final DeleteClientLegalPort deleteClientLegalPort;
    private final FindByIdClientLegalPort findByIdClientLegalPort;
    private final FindClientLegalsPort findProductCategoriesPort;
    private final UpdateClientLegalPort updateClientLegalPort;
    private final ClientLegalApiMapper clientLegalApiMapper;

    @Autowired
    public ClientLegalResources(CreateClientLegalPort createClientLegalPort, DeleteClientLegalPort deleteClientLegalPort, FindByIdClientLegalPort findByIdClientLegalPort, FindClientLegalsPort findProductCategoriesPort, UpdateClientLegalPort updateClientLegalPort, ClientLegalApiMapper clientLegalApiMapper) {
        this.createClientLegalPort = createClientLegalPort;
        this.deleteClientLegalPort = deleteClientLegalPort;
        this.findByIdClientLegalPort = findByIdClientLegalPort;
        this.findProductCategoriesPort = findProductCategoriesPort;
        this.updateClientLegalPort = updateClientLegalPort;
        this.clientLegalApiMapper = clientLegalApiMapper;
    }

    @Operation(summary = "Create a new ClientLegal", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ClientLegalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientLegalResponse> save(@Valid @RequestBody ClientLegalRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            ClientLegal clientLegal = clientLegalApiMapper.fromRequest(request);
            ClientLegal saved = createClientLegalPort.save(clientLegal);
            if (saved == null) {
                throw new ResourceNotFoundException("Produto não encontroado ao cadastrar");
            }

            ClientLegalResponse clientLegalResponse = clientLegalApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(clientLegalResponse.getId());
            return ResponseEntity.created(location).body(clientLegalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a ClientLegal by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientLegalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientLegalResponse> update(@PathVariable("id") Long id, @Valid @RequestBody ClientLegalRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = clientLegalApiMapper.fromRequest(request);
            ClientLegal updated = updateClientLegalPort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            ClientLegalResponse clientLegalResponse = clientLegalApiMapper.fromEntity(updated);
            return ResponseEntity.ok(clientLegalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all ClientLegal", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientLegalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientLegalResponse>> findAll() {
        List<ClientLegal> clientLegalList = findProductCategoriesPort.findAll();
        List<ClientLegalResponse> clientLegalResponse = clientLegalApiMapper.map(clientLegalList);
        return clientLegalResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(clientLegalResponse);
    }

    @Operation(
            summary = "Retrieve a ClientLegal by Id",
            description = "Get a ClientLegal object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClientLegalResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientLegalResponse> findOne(@PathVariable("id") Long id) {
        try {
            ClientLegal clientLegalSaved = findByIdClientLegalPort.findById(id);
            if (clientLegalSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            ClientLegalResponse clientLegalResponse = clientLegalApiMapper.fromEntity(clientLegalSaved);
            return ResponseEntity.ok(clientLegalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a ClientLegal by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteClientLegalPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}