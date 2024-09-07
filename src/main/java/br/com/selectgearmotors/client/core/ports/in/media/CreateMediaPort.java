package br.com.selectgearmotors.client.core.ports.in.media;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.domain.Media;

public interface CreateMediaPort {
    Media save(Media client) throws ResourceFoundException;
}
