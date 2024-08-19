package br.com.selectgearmotors.client.application.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientResponse;
import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.mapper.ClientApiMapper;
import br.com.selectgearmotors.client.commons.util.RestUtils;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.ports.in.client.*;
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
@RequestMapping("/v1/clients")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class ClientResources {

    private final CreateClientPort createClientPort;
    private final DeleteClientPort deleteClientPort;
    private final FindByIdClientPort findByIdClientPort;
    private final FindClientsPort findClientsPort;
    private final UpdateClientPort updateClientPort;
    private final ClientApiMapper productApiMapper;

    @Autowired
    public ClientResources(CreateClientPort createClientPort, DeleteClientPort deleteClientPort, FindByIdClientPort findByIdClientPort, FindClientsPort findClientsPort, UpdateClientPort updateClientPort, ClientApiMapper productApiMapper) {
        this.createClientPort = createClientPort;
        this.deleteClientPort = deleteClientPort;
        this.findByIdClientPort = findByIdClientPort;
        this.findClientsPort = findClientsPort;
        this.updateClientPort = updateClientPort;
        this.productApiMapper = productApiMapper;
    }

    @Operation(summary = "Create a new Client", tags = {"products", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ClientResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientResponse> save(@Valid @RequestBody ClientRequest request) {
        log.info("Chegada do objeto para ser salvo {}", request);
        Client client = productApiMapper.fromRequest(request);
        Client saved = createClientPort.save(client);
        if (saved == null) {
            throw new ResourceFoundException("Produto não encontroado ao cadastrar");
        }

        ClientResponse clientResponse = productApiMapper.fromEntity(saved);
        URI location = RestUtils.getUri(clientResponse.getId());

        return ResponseEntity.created(location).body(clientResponse);
    }

    @Operation(summary = "Update a Client by Id", tags = {"products", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long id, @Valid @RequestBody ClientRequest request) {
        log.info("Chegada do objeto para ser alterado {}", request);
        var product = productApiMapper.fromRequest(request);
        Client updated = updateClientPort.update(id, product);
        if (updated == null) {
            throw new ResourceFoundException("Produto não encontroado ao atualizar");
        }

        ClientResponse clientResponse = productApiMapper.fromEntity(updated);
        return ResponseEntity.ok(clientResponse);
    }

    @Operation(summary = "Retrieve all Client", tags = {"products", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ClientResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientResponse>> findAll() {
        List<Client> productList = findClientsPort.findAll();
        List<ClientResponse> clientResponse = productApiMapper.map(productList);
        return clientResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(clientResponse);
    }

    @Operation(
            summary = "Retrieve a Client by Id",
            description = "Get a Client object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"products", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClientResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientResponse> findOne(@PathVariable("id") Long id) {
        Client productSaved = findByIdClientPort.findById(id);
        if (productSaved == null) {
            throw new ResourceFoundException("Produto não encontrado ao buscar por id");
        }

        ClientResponse clientResponse = productApiMapper.fromEntity(productSaved);
        return ResponseEntity.ok(clientResponse);
    }

    @Operation(
            summary = "Retrieve a Client by Id",
            description = "Get a Client object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"products", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClientResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientResponse> findByCode(@PathVariable("code") String code) {
        Client productSaved = findByIdClientPort.findByCode(code);
        if (productSaved == null) {
            throw new ResourceFoundException("Produto não encontrado ao buscar por código");
        }

        ClientResponse clientResponse = productApiMapper.fromEntity(productSaved);
        return ResponseEntity.ok(clientResponse);
    }

    @Operation(summary = "Delete a Client by Id", tags = {"producttrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteClientPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}