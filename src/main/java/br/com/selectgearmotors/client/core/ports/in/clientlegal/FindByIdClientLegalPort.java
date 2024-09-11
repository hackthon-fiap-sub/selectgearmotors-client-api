package br.com.selectgearmotors.client.core.ports.in.clientlegal;

import br.com.selectgearmotors.client.core.domain.ClientLegal;

public interface FindByIdClientLegalPort {
    ClientLegal findById(Long id);
    ClientLegal findByClientId(Long id);
}
