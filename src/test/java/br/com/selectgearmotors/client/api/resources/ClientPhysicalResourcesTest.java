package br.com.selectgearmotors.client.api.resources;

import br.com.selectgearmotors.client.application.api.mapper.ClientPhysicalApiMapper;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import br.com.selectgearmotors.client.core.service.ClientPhysicalService;
import br.com.selectgearmotors.client.factory.ObjectFactory;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientPhysicalRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import br.com.selectgearmotors.client.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class ClientPhysicalResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(ClientPhysicalResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientPhysicalService service;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPhysicalRepository clientPhysicalRepository;

    private ClientPhysical clientPhysicalRequest;

    private Long clientTypeEntityId;

    private Long clientEntityId;

    private Long clientPhysicalEntityId;

    @Mock
    private ClientPhysicalApiMapper clientPhysicalApiMapper;

    private Faker faker = new Faker();

    @BeforeEach
    void setup() {
        clientPhysicalRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        ClientTypeEntity clientTypeEntity = createClientTypeEntity();
        this.clientTypeEntityId = clientTypeEntity.getId();
        log.info("clientTypeEntityId: {}", clientTypeEntityId);

        ClientEntity clientEntity = createClientEntity(clientTypeEntity);
        this.clientEntityId = clientEntity.getId();
        log.info("clientEntityId: {}", clientEntityId);

        this.clientPhysicalRequest = getClientPhysicalRequest(clientEntity.getId());
        this.clientPhysicalEntityId = clientPhysicalRequest.getClientId();
        log.info("clientPhysicalEntityId: {}", clientPhysicalEntityId);
    }

    @AfterEach
    void tearDown() {
        clientPhysicalRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();
    }

    public ClientPhysical getClientPhysicalRequest(Long clientId) {
        ClientPhysical build = ClientPhysical.builder()
                .clientId(clientId)
                .socialId("967.382.310-32")
                .build();

        ClientPhysical saved = service.save(build);
        return saved;
    }

    public ClientPhysicalEntity getClientPhysical(ClientEntity clientEntity) {
        ClientPhysicalEntity clientPhysicalEntity = ObjectFactory.getInstance().getClientPhysical(clientEntity);
        ClientPhysicalEntity clientPhysicalEntitySaved = clientPhysicalRepository.save(clientPhysicalEntity);

        verifyDataSavedClientPhysicalEntity(clientPhysicalEntitySaved);
        return clientPhysicalEntitySaved;
    }

    private void verifyDataSavedClientPhysicalEntity(ClientPhysicalEntity clientPhysicalEntity) {
        assertThat(clientPhysicalRepository.findById(clientPhysicalEntity.getId())).isPresent();
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

    @Disabled
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/client-physicals/{id}", this.clientPhysicalEntityId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socialId").value("96738231032"));
    }

    @Disabled
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client-physicals")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].socialId").exists());
    }

    @Disabled
    void getAll_isNull() throws Exception {
        clientPhysicalRepository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client-physicals")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        String create = JsonUtil.getJson(this.clientPhysicalRequest);

        assertThat(create).isNotNull().isNotEmpty();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/client-physicals")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotNull().isNotEmpty();
    }

    @Disabled
    void update() throws Exception {
        String update = JsonUtil.getJson(this.clientPhysicalRequest);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/client-physicals/{id}", this.clientPhysicalEntityId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContentUpdate = result.getResponse().getContentAsString();
        log.info("Response update {}", responseContentUpdate);
        assertThat(responseContentUpdate).isNotNull().isNotEmpty();
    }

    @Disabled
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/client-physicals/{id}", this.clientPhysicalEntityId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContentDelete = result.getResponse().getContentAsString();
        log.info("Response delete {}", responseContentDelete);
        assertThat(responseContentDelete).isEmpty();
    }
}