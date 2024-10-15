package br.com.selectgearmotors.client.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientRequest;
import br.com.selectgearmotors.client.application.api.mapper.ClientApiMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.service.ClientService;
import br.com.selectgearmotors.client.core.service.ClientTypeService;
import br.com.selectgearmotors.client.factory.ObjectFactory;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import br.com.selectgearmotors.client.infrastructure.repository.MediaRepository;
import br.com.selectgearmotors.client.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    private static final Logger log = LoggerFactory.getLogger(ClientResourcesTest.class);

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

    @Autowired
    private MediaRepository mediaRepository;

    private ClientTypeEntity clientTypeEntity;

    private Long clientTypeEntityId;

    @Mock
    private ClientApiMapper clientApiMapper;

    private Faker faker = new Faker();
    private Long clientCategoryId;
    private Long mediaId;
    private Long clientId;
    private ClientRequest clientRequest;
    private String clientCode;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        clientTypeRepository.deleteAll();

        this.clientTypeEntity = createClientTypeEntity();
        this.clientTypeEntityId = clientTypeEntity.getId();
        log.info("clientTypeEntityId: {}", clientTypeEntityId);


        MediaEntity mediaEntity = mediaRepository.save(getMedia());
        this.mediaId = mediaEntity.getId();
        log.info("mediaId: {}", mediaId);
    }

    public MediaEntity getMedia() {
        return MediaEntity.builder()
                .name(faker.food().ingredient())
                .mediaType(br.com.selectgearmotors.client.infrastructure.entity.domain.MediaType.JPG)
                .build();
    }

    private ClientRequest createClientRequest(Long clientTypeEntityId, Long mediaId) {
        return ClientRequest.builder()
                .clientTypeId(clientTypeEntityId)
                .name(faker.company().name())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .description(faker.lorem().sentence())
                .socialId("967.382.310-32")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .mediaId(mediaId)
                .build();
    }

    private Client getClient(Long clientTypeId, Long mediaId) {
        return Client.builder()
                .name(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .clientTypeId(clientTypeId)
                .mediaId(mediaId)
                .build();
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
        repository.deleteAll();
        Client client = getClient(this.clientTypeEntityId, this.mediaId);
        Client saved = service.save(client);
        log.info("Update: {}", saved);

        MvcResult result = mockMvc.perform(get("/v1/clients/{id}", saved.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void getAll() throws Exception {
        repository.deleteAll();
        Client client = getClient(this.clientTypeEntityId, this.mediaId);
        service.save(client);

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
        ClientRequest clientRequest1 = createClientRequest(this.clientTypeEntityId, this.mediaId);
        this.clientRequest = clientRequest1;
        log.info("clientRequest: {}", clientRequest);

        String create = JsonUtil.getJson(this.clientRequest);

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
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void update() throws Exception {
        repository.deleteAll();
        Client client = getClient(this.clientTypeEntityId, this.mediaId);
        Client saved = service.save(client);

        String update = JsonUtil.getJson(saved);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/clients/{id}", saved.getId())
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clients/{id}", this.clientTypeEntityId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void findByCode_clientFound() throws Exception {
        repository.deleteAll();
        Client client = getClient(this.clientTypeEntityId, this.mediaId);
        Client saved = service.save(client);

        MvcResult result = mockMvc.perform(get("/v1/clients/code/{code}", saved.getCode()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(get("/v1/clients/code/{code}", saved.getCode()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Disabled
    void testByCode_Exception() throws Exception {
        // Simulando a exceção que será lançada
        String errorMessage = "Produto nao encontrado ao buscar por codigo";
        when(clientApiMapper.fromRequest(any(ClientRequest.class))).thenThrow(new RuntimeException(errorMessage));

        // Executa a requisição e espera um status 404
        MvcResult result = mockMvc.perform(get("/v1/clients/code/{code}", 99L))
                .andDo(print())
                .andExpect(status().isNotFound()) // Verifica se o status HTTP é 404
                .andReturn();

        // Verifica o corpo da resposta
        String responseContent = result.getResponse().getContentAsString();

        // Usa uma biblioteca de assertivas para garantir que o conteúdo da resposta contém as informações esperadas
        assertThat(responseContent).contains("\"statusCode\":404");
        assertThat(responseContent).contains("\"message\":\"" + errorMessage + "\"");
    }
}