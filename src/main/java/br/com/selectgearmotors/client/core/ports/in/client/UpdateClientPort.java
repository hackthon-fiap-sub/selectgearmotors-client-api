package br.com.selectgearmotors.client.core.ports.in.client;

import br.com.selectgearmotors.client.core.domain.Client;

public interface UpdateClientPort {
    Client update(Long id, Client client);
}
