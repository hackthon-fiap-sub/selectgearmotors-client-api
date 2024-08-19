package br.com.selectgearmotors.client.core.ports.in.clientlegal;

import br.com.selectgearmotors.client.core.domain.ClientLegal;

public interface UpdateClientLegalPort {
    ClientLegal update(Long id, ClientLegal clientLegal);
}
