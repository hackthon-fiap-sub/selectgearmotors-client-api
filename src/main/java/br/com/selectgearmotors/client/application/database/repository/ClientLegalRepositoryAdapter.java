package br.com.selectgearmotors.client.application.database.repository;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.client.application.database.mapper.ClientLegalMapper;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import br.com.selectgearmotors.client.core.ports.out.ClientLegalRepositoryPort;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientLegalRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ClientLegalRepositoryAdapter implements ClientLegalRepositoryPort {

    private final ClientLegalRepository clientLegalRepository;
    private final ClientRepository clientRepository;
    private final ClientLegalMapper clientLegalMapper;

    @Autowired
    public ClientLegalRepositoryAdapter(ClientLegalRepository clientLegalRepository, ClientRepository clientRepository, ClientLegalMapper clientLegalMapper) {
        this.clientLegalRepository = clientLegalRepository;
        this.clientRepository = clientRepository;
        this.clientLegalMapper = clientLegalMapper;
    }

    @Override
    public ClientLegal save(ClientLegal clientLegal) {
        try {
            ClientLegalEntity clientLegalEntity = clientLegalMapper.fromModelTpEntity(clientLegal);
            Long clientId = clientLegal.getClientId();

            if (clientId != null) {
                Optional<ClientEntity> clientLegalEntityById = clientRepository.findById(clientId);
                if (!clientLegalEntityById.isPresent()) {
                    throw new ResourceFoundException("Cliente já cadastrado");
                }
                clientLegalEntity.setClientEntity(clientLegalEntityById.get());
            }

            log.info("Salvando produto: {}", clientLegalEntity);
            ClientLegalEntity saved = clientLegalRepository.save(clientLegalEntity);
            validateSavedEntity(saved);
            return clientLegalMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             clientLegalRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public ClientLegal findById(Long id) {
        Optional<ClientLegalEntity> buClientLegal = clientLegalRepository.findById(id);
        if (buClientLegal.isPresent()) {
            return clientLegalMapper.fromEntityToModel(buClientLegal.get());
        }
        return null;
    }

    @Override
    public List<ClientLegal> findAll() {
        List<ClientLegalEntity> all = clientLegalRepository.findAll();
        return clientLegalMapper.map(all);
    }

    @Override
    public ClientLegal update(Long id, ClientLegal clientLegal) {
        Optional<ClientLegalEntity> resultById = clientLegalRepository.findById(id);
        if (resultById.isPresent()) {
            ClientLegalEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return clientLegalMapper.fromEntityToModel(clientLegalRepository.save(productCategoryToChange));
        }
        return null;
    }

    @Override
    public ClientLegal findByClientId(Long id) {
        Optional<ClientLegalEntity> buClientLegal = clientLegalRepository.findByClientEntityId(id);
        if (buClientLegal.isPresent()) {
            return clientLegalMapper.fromEntityToModel(buClientLegal.get());
        }
        return null;
    }

    private void validateSavedEntity(ClientLegalEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }
    }
}
