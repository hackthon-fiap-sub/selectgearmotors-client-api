package br.com.selectgearmotors.client.application.database.repository;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.client.application.database.mapper.ClientTypeMapper;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.out.ClientTypeRepositoryPort;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ClientTypeRepositoryAdapter implements ClientTypeRepositoryPort {

    private final ClientTypeRepository clientTypeRepository;
    private final ClientTypeMapper clientTypeMapper;

    @Autowired
    public ClientTypeRepositoryAdapter(ClientTypeRepository clientTypeRepository, ClientTypeMapper clientTypeMapper) {
        this.clientTypeRepository = clientTypeRepository;
        this.clientTypeMapper = clientTypeMapper;
    }

    @Override
    public ClientType save(ClientType clientType) {
        try {
            ClientTypeEntity productCategoryEntity = clientTypeMapper.fromModelTpEntity(clientType);
            ClientTypeEntity saved = clientTypeRepository.save(productCategoryEntity);
            validateSavedEntity(saved);
            return clientTypeMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             clientTypeRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public ClientType findById(Long id) {
        Optional<ClientTypeEntity> buClientType = clientTypeRepository.findById(id);
        if (buClientType.isPresent()) {
            return clientTypeMapper.fromEntityToModel(buClientType.get());
        }
        return null;
    }

    @Override
    public List<ClientType> findAll() {
        List<ClientTypeEntity> all = clientTypeRepository.findAll();
        return clientTypeMapper.map(all);
    }

    @Override
    public ClientType update(Long id, ClientType clientType) {
        Optional<ClientTypeEntity> resultById = clientTypeRepository.findById(id);
        if (resultById.isPresent()) {
            ClientTypeEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return clientTypeMapper.fromEntityToModel(clientTypeRepository.save(productCategoryToChange));
        }
        return null;
    }

    private void validateSavedEntity(ClientTypeEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }

        if (saved.getName() == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: nome do produto é nulo");
        }
    }
}
