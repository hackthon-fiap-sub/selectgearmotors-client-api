package br.com.selectgearmotors.client.core.ports.in.client;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.domain.Client;

public interface CreateClientPort {
    Client save(Client client) throws ResourceFoundException;
}
