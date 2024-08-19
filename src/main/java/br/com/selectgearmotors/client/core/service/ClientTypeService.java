package br.com.selectgearmotors.client.core.service;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.in.clienttype.*;
import br.com.selectgearmotors.client.core.ports.out.ClientTypeRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientTypeService implements CreateClientTypePort, UpdateClientTypePort, FindByIdClientTypePort, FindClientTypesPort, DeleteClientTypePort {

    private final ClientTypeRepositoryPort clientTypeRepository;

    @Autowired
    public ClientTypeService(ClientTypeRepositoryPort clientTypeRepository) {
        this.clientTypeRepository = clientTypeRepository;
    }

    @Override
    public ClientType save(ClientType clientType) {
        return clientTypeRepository.save(clientType);
    }

    @Override
    public ClientType update(Long id, ClientType product) {
        ClientType resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return clientTypeRepository.save(resultById);
        }

        return null;
    }

    @Override
    public ClientType findById(Long id) {
        return clientTypeRepository.findById(id);
    }

    @Override
    public List<ClientType> findAll() {
       return clientTypeRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            ClientType clientTypeById = findById(id);
            if (clientTypeById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            clientTypeRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
