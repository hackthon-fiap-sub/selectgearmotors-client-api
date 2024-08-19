package br.com.selectgearmotors.client.core.ports.in.client;

import br.com.selectgearmotors.client.core.domain.Client;

public interface FindByIdClientPort {
    Client findById(Long id);
    Client findByCode(String code);
    Client findByEmail(String email);
}
