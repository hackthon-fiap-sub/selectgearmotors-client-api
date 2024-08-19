package br.com.selectgearmotors.client.infrastructure.repository;

import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientTypeEntity, Long> {
    Optional<ClientTypeEntity> findByName(String name);
}
