package br.com.selectgearmotors.client.core.ports.out;

import br.com.selectgearmotors.client.core.domain.ClientLegal;

import java.util.List;

public interface ClientLegalRepositoryPort {
    ClientLegal save(ClientLegal clientLegal);
    boolean remove(Long id);
    ClientLegal findById(Long id);
    List<ClientLegal> findAll();
    ClientLegal update(Long id, ClientLegal clientLegal);
}
