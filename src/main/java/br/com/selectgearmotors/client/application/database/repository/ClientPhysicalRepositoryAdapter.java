package br.com.selectgearmotors.client.application.database.repository;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.client.application.database.mapper.ClientPhysicalMapper;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import br.com.selectgearmotors.client.core.ports.out.ClientPhysicalRepositoryPort;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientPhysicalRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ClientPhysicalRepositoryAdapter implements ClientPhysicalRepositoryPort {

    private final ClientPhysicalRepository clientPhysicalRepository;
    private final ClientPhysicalMapper clientPhysicalMapper;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientPhysicalRepositoryAdapter(ClientPhysicalRepository clientPhysicalRepository, ClientPhysicalMapper clientPhysicalMapper, ClientRepository clientRepository) {
        this.clientPhysicalRepository = clientPhysicalRepository;
        this.clientPhysicalMapper = clientPhysicalMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientPhysical save(ClientPhysical clientPhysical) {
        try {
            ClientPhysicalEntity clientPhysicalEntity = clientPhysicalMapper.fromModelTpEntity(clientPhysical);
            Long clientId = clientPhysical.getClientId();

            if (clientId != null) {
                Optional<ClientEntity> clientLegalEntityById = clientRepository.findById(clientId);
                if (!clientLegalEntityById.isPresent()) {
                    throw new ResourceFoundException("Cliente já cadastrado");
                }
                clientPhysicalEntity.setClientEntity(clientLegalEntityById.get());
            }

            ClientPhysicalEntity saved = clientPhysicalRepository.save(clientPhysicalEntity);
            validateSavedEntity(saved);
            return clientPhysicalMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             clientPhysicalRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public ClientPhysical findById(Long id) {
        Optional<ClientPhysicalEntity> buClientPhysical = clientPhysicalRepository.findById(id);
        if (buClientPhysical.isPresent()) {
            return clientPhysicalMapper.fromEntityToModel(buClientPhysical.get());
        }
        return null;
    }

    @Override
    public List<ClientPhysical> findAll() {
        List<ClientPhysicalEntity> all = clientPhysicalRepository.findAll();
        return clientPhysicalMapper.map(all);
    }

    @Override
    public ClientPhysical update(Long id, ClientPhysical clientPhysical) {
        Optional<ClientPhysicalEntity> resultById = clientPhysicalRepository.findById(id);
        if (resultById.isPresent()) {
            ClientPhysicalEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return clientPhysicalMapper.fromEntityToModel(clientPhysicalRepository.save(productCategoryToChange));
        }
        return null;
    }

    @Override
    public ClientPhysical findByClientId(Long id) {
        Optional<ClientPhysicalEntity> resultById = clientPhysicalRepository.findByClientEntityId(id);
        if (resultById.isPresent()) {
            ClientPhysicalEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return clientPhysicalMapper.fromEntityToModel(clientPhysicalRepository.save(productCategoryToChange));
        }
        return null;
    }

    private void validateSavedEntity(ClientPhysicalEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }
    }
}
