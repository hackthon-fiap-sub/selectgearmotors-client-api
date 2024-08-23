package br.com.selectgearmotors.client.api.resources;

import br.com.selectgearmotors.client.application.api.dto.request.ClientTypeRequest;
import br.com.selectgearmotors.client.application.api.mapper.ClientTypeApiMapper;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.service.ClientTypeService;
import br.com.selectgearmotors.client.factory.ObjectFactory;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import br.com.selectgearmotors.client.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class ClientTypeResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(ClientTypeResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientTypeService service;

    @Autowired
    private ClientTypeRepository repository;

    private ClientType clientType;

    private Long clientTypeId;

    @Mock
    private ClientTypeApiMapper clientTypeApiMapper;

    private ClientType getClientType() {
        return ClientType.builder()
                .name("Pragmatico")
                .build();
    }

    private ClientType getClientTypeUpdate() {
        return ClientType.builder()
                .id(1L)
                .name("Pragmatico")
                .build();
    }

    @BeforeEach
    void setup() {
        repository.deleteAll();

        ClientType clientTypeSaved = service.save(getClientType());
        this.clientType = clientTypeSaved;
        this.clientTypeId = clientTypeSaved.getId();
    }

    @Test
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/client-types/{id}", this.clientTypeId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pragmatico"));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }

    @Test
    void getAll_isNull() throws Exception {
        repository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void create() throws Exception {
        String create = JsonUtil.getJson(this.clientType);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/client-types")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new ClientType());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/client-types")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void testSave_Exception() throws Exception {
        ClientTypeRequest clientTypeRequest = new ClientTypeRequest();
        String create = JsonUtil.getJson(clientTypeRequest);

        when(clientTypeApiMapper.fromRequest(clientTypeRequest)).thenThrow(new RuntimeException("Client não encontroado ao cadastrar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/client-types")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void update() throws Exception {
        String update = JsonUtil.getJson(this.clientType);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/client-types/{id}", this.clientTypeId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pragmatico"));
    }

    @Test
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new ClientTypeRequest());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/client-types/{id}", this.clientTypeId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void testUpdate_Exception() throws Exception {
        ClientTypeRequest product = new ClientTypeRequest();
        String create = JsonUtil.getJson(product);

        when(clientTypeApiMapper.fromRequest(product)).thenThrow(new RuntimeException("Produto não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/client-types/{id}", this.clientTypeId)
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/client-types/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Disabled
    void findByCode_productIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/client-types/{id}", 99l))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        ClientTypeRequest clientTypeRequest = new ClientTypeRequest();
        when(clientTypeApiMapper.fromRequest(clientTypeRequest)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por código"));

        MvcResult result = mockMvc.perform(get("/v1/client-types/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}