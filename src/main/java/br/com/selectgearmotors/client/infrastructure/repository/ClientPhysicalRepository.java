package br.com.selectgearmotors.client.infrastructure.repository;

import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientPhysicalRepository extends JpaRepository<ClientPhysicalEntity, Long> {
    Optional<ClientPhysicalEntity> findBySocialId(String socialId);
    Optional<ClientPhysicalEntity> findByClientEntityId(Long id);
}
