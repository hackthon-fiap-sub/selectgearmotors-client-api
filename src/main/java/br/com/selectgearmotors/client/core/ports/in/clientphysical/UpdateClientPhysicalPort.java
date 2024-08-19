package br.com.selectgearmotors.client.core.ports.in.clientphysical;

import br.com.selectgearmotors.client.core.domain.ClientPhysical;

public interface UpdateClientPhysicalPort {
    ClientPhysical update(Long id, ClientPhysical clientPhysical);
}
