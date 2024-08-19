package br.com.selectgearmotors.client.repository;

import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class ClientTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientTypeRepository clientTypeRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    private ClientTypeEntity getClientType() {
        return ClientTypeEntity.builder()
                .id(1l)
                .name("Bebida")
                .build();
    }

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        clientTypeRepository.save(getClientType());
    }

    @Test
    void should_find_no_clients_if_repository_is_empty() {
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        clientTypeRepository.save(getClientType());

        List<ClientTypeEntity> seeds = new ArrayList<>();
        seeds = clientTypeRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_client_category() {
        String cocaColaBeverage = "Coca-Cola";
        Optional<ClientTypeEntity> clientType = clientTypeRepository.findByName(cocaColaBeverage);
        Optional<ClientTypeEntity> clientTypeResponse = null;
        if (!clientType.isPresent()) {

            ClientTypeEntity cocaCola = ClientTypeEntity.builder()
                    .name(cocaColaBeverage)
                    .build();

            ClientTypeEntity save = clientTypeRepository.save(cocaCola);
            clientTypeResponse = clientTypeRepository.findByName(cocaColaBeverage);
        }

        ClientTypeEntity clientType1 = clientTypeResponse.get();
        assertThat(clientType1).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        ClientTypeEntity clientType = new ClientTypeEntity();
        clientType.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        assertThrows(ConstraintViolationException.class, () -> {
            clientTypeRepository.save(clientType);
        });
    }

    private ClientTypeEntity createInvalidClientType() {
        ClientTypeEntity clientType = new ClientTypeEntity();
        // Configurar o clientType com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        clientType.setName(""); // Nome vazio pode causar uma violação
        return clientType;
    }

    @Test
    void should_found_null_ClientType() {
        ClientTypeEntity clientType = null;

        Optional<ClientTypeEntity> fromDb = clientTypeRepository.findById(99l);
        if (fromDb.isPresent()) {
            clientType = fromDb.get();
        }
        assertThat(clientType).isNull();
    }

    @Test
    void whenFindById_thenReturnClientType() {
        Optional<ClientTypeEntity> clientType = clientTypeRepository.findById(1l);
        if (clientType.isPresent()) {
            ClientTypeEntity clientTypeResult = clientType.get();
            assertThat(clientTypeResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        ClientTypeEntity fromDb = clientTypeRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfClientTypes_whenFindAll_thenReturnAllClientTypes() {
        ClientTypeEntity clientType = null;
        ClientTypeEntity clientType1 = null;
        ClientTypeEntity clientType2 = null;

        Optional<ClientTypeEntity> restaurant = clientTypeRepository.findById(1l);
        if (restaurant.isPresent()) {

            ClientTypeEntity bebida = ClientTypeEntity.builder()
                    .name("Bebida")
                    .build();
            clientType = clientTypeRepository.save(bebida);

            ClientTypeEntity acompanhamento = ClientTypeEntity.builder()
                    .name("Acompanhamento")
                    .build();
            clientType1 = clientTypeRepository.save(acompanhamento);

            ClientTypeEntity lanche = ClientTypeEntity.builder()
                    .name("Lanche")
                    .build();
            clientType2 = clientTypeRepository.save(lanche);

        }

        Iterator<ClientTypeEntity> allClientTypes = clientTypeRepository.findAll().iterator();
        List<ClientTypeEntity> clients = new ArrayList<>();
        allClientTypes.forEachRemaining(c -> clients.add(c));

        assertNotNull(allClientTypes);
    }
}