package br.com.selectgearmotors.client.core.service;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.ports.in.client.*;
import br.com.selectgearmotors.client.core.ports.out.ClientRepositoryPort;
import br.com.selectgearmotors.client.core.domain.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService implements CreateClientPort, UpdateClientPort, FindByIdClientPort, FindClientsPort, DeleteClientPort {

    private final ClientRepositoryPort clientRepository;

    @Override
    public Client save(Client client) throws ResourceFoundException {
        Client byEmail = findByEmail(client.getEmail());
        if (byEmail != null) {
            throw new ResourceFoundException("Client already exists");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, client);

            return clientRepository.save(resultById);
        }

        return null;
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client findByCode(String code) {
        return clientRepository.findByCode(code);
    }

    @Override
    public List<Client> findAll() {
       return clientRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Client clientById = findById(id);
            if (clientById == null) {
                throw new ResourceFoundException("Client not found");
            }

            clientRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
