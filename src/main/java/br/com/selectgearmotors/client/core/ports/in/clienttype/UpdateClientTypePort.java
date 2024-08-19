package br.com.selectgearmotors.client.core.ports.in.clienttype;

import br.com.selectgearmotors.client.core.domain.ClientType;

public interface UpdateClientTypePort {
    ClientType update(Long id, ClientType clientType);
}
