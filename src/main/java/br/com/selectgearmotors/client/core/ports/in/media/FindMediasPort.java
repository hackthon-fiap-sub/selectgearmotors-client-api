package br.com.selectgearmotors.client.core.ports.in.media;

import br.com.selectgearmotors.client.core.domain.Media;
import java.util.List;

public interface FindMediasPort {
    List<Media> findAll();
}
