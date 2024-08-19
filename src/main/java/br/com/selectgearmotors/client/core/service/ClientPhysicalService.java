package br.com.selectgearmotors.client.core.service;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import br.com.selectgearmotors.client.core.ports.in.clientphysical.*;
import br.com.selectgearmotors.client.core.ports.in.clienttype.*;
import br.com.selectgearmotors.client.core.ports.out.ClientPhysicalRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientPhysicalService implements CreateClientPhysicalPort, UpdateClientPhysicalPort, FindByIdClientPhysicalPort, FindClientPhysicalsPort, DeleteClientPhysicalPort {

    private final ClientPhysicalRepositoryPort clientPhysicalRepository;

    @Autowired
    public ClientPhysicalService(ClientPhysicalRepositoryPort clientPhysicalRepository) {
        this.clientPhysicalRepository = clientPhysicalRepository;
    }

    @Override
    public ClientPhysical save(ClientPhysical clientPhysical) {
        return clientPhysicalRepository.save(clientPhysical);
    }

    @Override
    public ClientPhysical update(Long id, ClientPhysical product) {
        ClientPhysical resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return clientPhysicalRepository.save(resultById);
        }

        return null;
    }

    @Override
    public ClientPhysical findById(Long id) {
        return clientPhysicalRepository.findById(id);
    }

    @Override
    public List<ClientPhysical> findAll() {
       return clientPhysicalRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            ClientPhysical clientPhysicalById = findById(id);
            if (clientPhysicalById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            clientPhysicalRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
