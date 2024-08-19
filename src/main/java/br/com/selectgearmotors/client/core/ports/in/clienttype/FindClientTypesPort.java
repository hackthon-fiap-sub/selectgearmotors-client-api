package br.com.selectgearmotors.client.core.ports.in.clienttype;

import br.com.selectgearmotors.client.core.domain.ClientType;

import java.util.List;

public interface FindClientTypesPort {
    List<ClientType> findAll();
}
