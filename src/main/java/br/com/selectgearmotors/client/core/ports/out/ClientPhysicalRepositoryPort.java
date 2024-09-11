package br.com.selectgearmotors.client.core.ports.out;

import br.com.selectgearmotors.client.core.domain.ClientPhysical;

import java.util.List;

public interface ClientPhysicalRepositoryPort {
    ClientPhysical save(ClientPhysical clientPhysical);
    boolean remove(Long id);
    ClientPhysical findById(Long id);
    List<ClientPhysical> findAll();
    ClientPhysical update(Long id, ClientPhysical clientPhysical);
    ClientPhysical findByClientId(Long id);
}
