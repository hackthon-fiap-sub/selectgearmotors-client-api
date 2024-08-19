package br.com.selectgearmotors.client.core.ports.in.clienttype;

import br.com.selectgearmotors.client.core.domain.ClientType;

public interface FindByIdClientTypePort {
    ClientType findById(Long id);
}
