package br.com.selectgearmotors.client.core.ports.in.client;

import br.com.selectgearmotors.client.core.domain.Client;

import java.util.List;

public interface FindClientsPort {
    List<Client> findAll();
}
