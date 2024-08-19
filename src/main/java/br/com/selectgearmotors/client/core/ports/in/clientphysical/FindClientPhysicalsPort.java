package br.com.selectgearmotors.client.core.ports.in.clientphysical;

import br.com.selectgearmotors.client.core.domain.ClientPhysical;

import java.util.List;

public interface FindClientPhysicalsPort {
    List<ClientPhysical> findAll();
}
