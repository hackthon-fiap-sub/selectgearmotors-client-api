package br.com.selectgearmotors.client.infrastructure.repository;

import br.com.selectgearmotors.client.infrastructure.entity.media.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    MediaEntity findByName(String name);
}
