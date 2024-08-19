package br.com.selectgearmotors.client.core.ports.in.clientlegal;

import br.com.selectgearmotors.client.core.domain.ClientLegal;

import java.util.List;

public interface FindClientLegalsPort {
    List<ClientLegal> findAll();
}
