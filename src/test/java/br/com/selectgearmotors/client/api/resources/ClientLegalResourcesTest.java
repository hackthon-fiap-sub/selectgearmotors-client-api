package br.com.selectgearmotors.client.api.resources;

import br.com.selectgearmotors.client.application.api.mapper.ClientLegalApiMapper;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import br.com.selectgearmotors.client.core.service.ClientLegalService;
import br.com.selectgearmotors.client.factory.ObjectFactory;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientLegalRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import br.com.selectgearmotors.client.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class ClientLegalResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(ClientLegalResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientLegalService service;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientLegalRepository clientLegalRepository;

    private ClientLegal clientLegalRequest;

    private Long clientTypeEntityId;

    private Long clientEntityId;

    private Long clientLegalEntityId;

    @Mock
    private ClientLegalApiMapper clientLegalApiMapper;

    private Faker faker = new Faker();

    @BeforeEach
    void setup() {
        clientLegalRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        ClientTypeEntity clientTypeEntity = createClientTypeEntity();
        this.clientTypeEntityId = clientTypeEntity.getId();
        log.info("clientTypeEntityId: {}", clientTypeEntityId);

        ClientEntity clientEntity = createClientEntity(clientTypeEntity);
        this.clientEntityId = clientEntity.getId();
        log.info("clientEntityId: {}", clientEntityId);

        this.clientLegalRequest = getClientLegalRequest(clientEntity.getId());
        this.clientLegalEntityId = clientLegalRequest.getClientId();
        log.info("clientLegalEntityId: {}", clientLegalEntityId);
    }

    @AfterEach
    void tearDown() {
        clientLegalRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();
    }

    public ClientLegal getClientLegalRequest(Long clientId) {
        ClientLegal build = ClientLegal.builder()
                .clientId(clientId)
                .companyId("12.345.678/0001-00")
                .build();

        ClientLegal save = service.save(build);
        return save;
    }

    public ClientLegalEntity getClientLegal(ClientEntity clientEntity) {
        ClientLegalEntity clientLegalEntity = ObjectFactory.getInstance().getClientLegal(clientEntity);
        ClientLegalEntity clientLegalEntitySaved = clientLegalRepository.save(clientLegalEntity);

        verifyDataSavedClientLegalEntity(clientLegalEntitySaved);
        return clientLegalEntitySaved;
    }

    private void verifyDataSavedClientLegalEntity(ClientLegalEntity clientLegalEntity) {
        assertThat(clientLegalRepository.findById(clientLegalEntity.getId())).isPresent();
    }

    private ClientEntity createClientEntity(ClientTypeEntity clientTypeEntity) {
        ClientEntity clientEntity = ObjectFactory.getInstance().getClient(clientTypeEntity);
        ClientEntity clientEntitySaved = clientRepository.save(clientEntity);

        verifyDataSavedClientEntity(clientEntitySaved);
        return clientEntitySaved;
    }

    private void verifyDataSavedClientEntity(ClientEntity clientEntity) {
        assertThat(clientRepository.findById(clientEntity.getId()).isPresent());
    }

    private ClientTypeEntity createClientTypeEntity() {
        ClientTypeEntity clientTypeEntity = ObjectFactory.getInstance().getClientType();
        ClientTypeEntity clientTypeEntitySaved = clientTypeRepository.save(clientTypeEntity);

        verifyDataSavedClientTypeEntity(clientTypeEntitySaved);
        return clientTypeEntitySaved;
    }

    private void verifyDataSavedClientTypeEntity(ClientTypeEntity clientTypeEntitySaved) {
        assertThat(clientTypeRepository.findById(clientTypeEntitySaved.getId())).isPresent();
    }

    @Test
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/client-legals/{id}", this.clientLegalEntityId))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.companyId").value("12345678000100"));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client-legals")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].companyId").exists());
    }

    @Test
    void getAll_isNull() throws Exception {
        clientLegalRepository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client-legals")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void create() throws Exception {
        String create = JsonUtil.getJson(this.clientLegalRequest);

        assertThat(create).isNotNull().isNotEmpty();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/client-legals")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotNull().isNotEmpty();
    }

    @Test
    void update() throws Exception {
        String update = JsonUtil.getJson(this.clientLegalRequest);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/client-legals/{id}", this.clientLegalEntityId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContentUpdate = result.getResponse().getContentAsString();
        log.info("Response update {}", responseContentUpdate);
        assertThat(responseContentUpdate).isNotNull();
    }

    @Test
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/client-legals/{id}", this.clientLegalEntityId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContentDelete = result.getResponse().getContentAsString();
        log.info("Response delete {}", responseContentDelete);
        assertThat(responseContentDelete).isEmpty();
    }
}