package br.com.selectgearmotors.client.infrastructure.repository;

import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientLegalRepository extends JpaRepository<ClientLegalEntity, Long> {
    Optional<ClientLegalEntity> findByCompanyId(String companyId);
    Optional<ClientLegalEntity> findByClientEntityId(Long id);
}
