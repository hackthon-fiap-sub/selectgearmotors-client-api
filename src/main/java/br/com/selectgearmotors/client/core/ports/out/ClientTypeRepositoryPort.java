package br.com.selectgearmotors.client.core.ports.out;

import br.com.selectgearmotors.client.core.domain.ClientType;

import java.util.List;

public interface ClientTypeRepositoryPort {
    ClientType save(ClientType clientType);
    boolean remove(Long id);
    ClientType findById(Long id);
    List<ClientType> findAll();
    ClientType update(Long id, ClientType clientType);
}
