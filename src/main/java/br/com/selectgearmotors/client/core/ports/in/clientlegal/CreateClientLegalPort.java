package br.com.selectgearmotors.client.core.ports.in.clientlegal;

import br.com.selectgearmotors.client.core.domain.ClientLegal;

public interface CreateClientLegalPort {
    ClientLegal save(ClientLegal clientLegal);
}
