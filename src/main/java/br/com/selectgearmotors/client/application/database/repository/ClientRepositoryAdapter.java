package br.com.selectgearmotors.client.application.database.repository;

import br.com.selectgearmotors.client.application.database.mapper.ClientMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.ports.out.ClientRepositoryPort;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientRepositoryAdapter(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public Client save(Client client) {
        try {
            ClientEntity clientEntity = clientMapper.fromModelTpEntity(client);
            if (clientEntity != null) {
                clientEntity.setCode(UUID.randomUUID().toString());
                ClientEntity saved = clientRepository.save(clientEntity);
                return clientMapper.fromEntityToModel(saved);
            }
        } catch (Exception e) {
            log.info("Erro ao salvar produto: " + e.getMessage());
            return null;
        }

        return null;
    }

    @Override
    public boolean remove(Long id) {
        try {
            clientRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Client findById(Long id) {
        Optional<ClientEntity> buClient = clientRepository.findById(id);
        if (buClient.isPresent()) {
            return clientMapper.fromEntityToModel(buClient.get());
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        List<ClientEntity> all = clientRepository.findAll();
        return clientMapper.map(all);
    }

    @Override
    public Client update(Long id, Client client) {
        Optional<ClientEntity> resultById = clientRepository.findById(id);
        if (resultById.isPresent()) {

            ClientEntity clientToChange = resultById.get();
            clientToChange.update(id, clientToChange);

            return clientMapper.fromEntityToModel(clientRepository.save(clientToChange));
        }

        return null;
    }

    @Override
    public Client findByCode(String code) {
        ClientEntity byCode = clientRepository.findByCode(code);
        return clientMapper.fromEntityToModel(byCode);
    }

    @Override
    public Client findByName(String name) {
        ClientEntity byCode = clientRepository.findByName(name);
        return clientMapper.fromEntityToModel(byCode);
    }

    @Override
    public Client findByEmail(String email) {
        ClientEntity byEmail = clientRepository.findByEmail(email);
        return clientMapper.fromEntityToModel(byEmail);
    }
}
