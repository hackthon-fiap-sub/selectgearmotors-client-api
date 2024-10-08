package br.com.selectgearmotors.client.core.service;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import br.com.selectgearmotors.client.core.ports.in.clientlegal.*;
import br.com.selectgearmotors.client.core.ports.in.clienttype.*;
import br.com.selectgearmotors.client.core.ports.out.ClientLegalRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientLegalService implements CreateClientLegalPort, UpdateClientLegalPort, FindByIdClientLegalPort, FindClientLegalsPort, DeleteClientLegalPort {

    private final ClientLegalRepositoryPort clientLegalRepository;

    @Autowired
    public ClientLegalService(ClientLegalRepositoryPort clientLegalRepository) {
        this.clientLegalRepository = clientLegalRepository;
    }

    @Override
    public ClientLegal save(ClientLegal clientLegal) {
        return clientLegalRepository.save(clientLegal);
    }

    @Override
    public ClientLegal update(Long id, ClientLegal product) {
        ClientLegal resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);
            return clientLegalRepository.save(resultById);
        }

        return null;
    }

    @Override
    public ClientLegal findById(Long id) {
        return clientLegalRepository.findById(id);
    }

    @Override
    public ClientLegal findByClientId(Long id) {
        return clientLegalRepository.findByClientId(id);
    }

    @Override
    public List<ClientLegal> findAll() {
       return clientLegalRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            ClientLegal clientLegalById = findById(id);
            if (clientLegalById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            clientLegalRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
