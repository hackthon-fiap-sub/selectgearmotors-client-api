package br.com.selectgearmotors.client.infrastructure.repository;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByCode(String code);
    ClientEntity findByName(String name);
    ClientEntity findByEmail(String email);
}
