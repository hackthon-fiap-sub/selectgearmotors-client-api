package br.com.selectgearmotors.client.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientRequest;
import br.com.selectgearmotors.client.application.api.mapper.ClientApiMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.service.ClientService;
import br.com.selectgearmotors.client.core.service.ClientTypeService;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import br.com.selectgearmotors.client.util.CnpjGenerator;
import br.com.selectgearmotors.client.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class ClientResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ClientTypeService clientTypeService;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Mock
    private ClientApiMapper clientApiMapper;

    private Faker faker = new Faker();
    private Long clientCategoryId;
    private Long restaurantId;
    private Long clientId;
    private String clientCode;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        clientTypeRepository.deleteAll();

        ClientType clientType = getClientCategory();
        clientType = clientTypeService.save(clientType);
        this.clientCategoryId = clientType.getId();

        Client clientFounded = getClient(clientCategoryId, restaurantId);
        var clientSaved = service.save(clientFounded);
        this.clientId = clientSaved.getId();
        this.clientCode = clientSaved.getCode();// Save the client ID for use in tests

        verifyDataSaved(clientType, clientSaved);
    }

    private void verifyDataSaved(ClientType clientType, Client client) {
        assertThat(clientTypeRepository.findById(clientType.getId())).isPresent();
        assertThat(repository.findById(client.getId())).isPresent();
    }

    private ClientType getClientCategory() {
        return ClientType.builder()
                .name(faker.commerce().department())
                .build();
    }

    private Client getClient(Long clientCategoryId, Long restaurantId) {
        return Client.builder()
                .name(faker.commerce().productName())
                .pic(faker.internet().avatar())
                .address(faker.address().fullAddress())
                .clientTypeId(clientCategoryId)
                .description(faker.lorem().sentence())
                .dataProcessingConsent(faker.bool().bool())
                .email(faker.internet().emailAddress())
                .mobile(faker.phoneNumber().cellPhone())
                .socialId(CnpjGenerator.generateCpf())
                .build();
    }

    private ClientEntity getClientEntity(Long clientCategoryId, Long restaurantId) {
        Optional<ClientTypeEntity> clientCategoryById = clientTypeRepository.findById(clientCategoryId);

        return ClientEntity.builder()
                .name(faker.commerce().productName())
                .pic(faker.internet().avatar())
                .address(faker.address().fullAddress())
                .clientTypeEntity(clientCategoryById != null ? clientCategoryById.get() : null)
                .description(faker.lorem().sentence())
                .dataProcessingConsent(faker.bool().bool())
                .email(faker.internet().emailAddress())
                .mobile(faker.phoneNumber().cellPhone())
                .socialId(CnpjGenerator.generateCpf())
                .build();
    }

    private Client getClientUpdate(Long clientCategoryId, Long restaurantId) {
        return Client.builder()
                .id(clientId) // Ensure we are updating the same client
                .name(faker.commerce().productName())
                .pic(faker.internet().avatar())
                .address(faker.address().fullAddress())
                .clientTypeId(clientCategoryId)
                .description(faker.lorem().sentence())
                .dataProcessingConsent(faker.bool().bool())
                .email(faker.internet().emailAddress())
                .mobile(faker.phoneNumber().cellPhone())
                .socialId(CnpjGenerator.generateCpf())
                .build();
    }

    @Disabled
    void findsTaskById() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/clients/{id}", clientId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(get("/v1/clients/{id}", clientId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Disabled
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }

    @Disabled
    void getAll_isNull() throws Exception {
        repository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        repository.deleteAll();
        clientTypeRepository.findById(this.clientCategoryId).ifPresent(clientCategory -> {
            assertThat(clientCategory).isNotNull();
            this.clientCategoryId = clientCategory.getId();
        });

        Client client = getClient(this.clientCategoryId, this.restaurantId);
        String create = JsonUtil.getJson(client);

        assertThat(create).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/clients")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/clients")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Disabled
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new Client());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/clients")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testSave_Exception() throws Exception {
        ClientRequest client = new ClientRequest();
        String create = JsonUtil.getJson(client);

        when(clientApiMapper.fromRequest(client)).thenThrow(new RuntimeException("Produto não encontroado ao cadastrar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/clients")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void update() throws Exception {
        Client clientUpdate = getClientUpdate(clientCategoryId, restaurantId);
        String update = JsonUtil.getJson(clientUpdate);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/clients/{id}", clientId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/clients/{id}", clientId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Disabled
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new Client());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/clients/{id}", clientId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testUpdate_Exception() throws Exception {
        ClientRequest client = new ClientRequest();
        String create = JsonUtil.getJson(client);

        when(clientApiMapper.fromRequest(client)).thenThrow(new RuntimeException("Produto não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/clients/{id}", clientId)
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clients/{id}", clientId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clients/{id}", clientId))
                .andExpect(status().isNoContent());
    }

    @Disabled
    void findByCode_clientFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/clients/code/{code}", this.clientCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(get("/v1/clients/code/{code}", clientCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Disabled
    void testByCode_Exception() throws Exception {
        ClientRequest client = new ClientRequest();
        when(clientApiMapper.fromRequest(client)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por código"));

        MvcResult result = mockMvc.perform(get("/v1/clients/code/{code}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void findByCode_clientIsNull() throws Exception {
        String clientCode = UUID.randomUUID().toString();
        MvcResult result = mockMvc.perform(get("/v1/clients/code/{code}", clientCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        ClientRequest client = new ClientRequest();
        when(clientApiMapper.fromRequest(client)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por id"));

        MvcResult result = mockMvc.perform(get("/v1/clients/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}