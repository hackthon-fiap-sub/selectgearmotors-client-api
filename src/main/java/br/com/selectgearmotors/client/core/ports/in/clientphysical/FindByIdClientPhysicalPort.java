package br.com.selectgearmotors.client.core.ports.in.clientphysical;

import br.com.selectgearmotors.client.core.domain.ClientPhysical;

public interface FindByIdClientPhysicalPort {
    ClientPhysical findById(Long id);

    ClientPhysical findByClientId(Long id);
}
