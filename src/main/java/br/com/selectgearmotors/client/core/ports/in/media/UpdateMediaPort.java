package br.com.selectgearmotors.client.core.ports.in.media;

import br.com.selectgearmotors.client.core.domain.Media;

public interface UpdateMediaPort {
    Media update(Long id, Media client);
}
