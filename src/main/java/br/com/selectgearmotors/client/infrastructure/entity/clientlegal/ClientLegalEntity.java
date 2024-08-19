package br.com.selectgearmotors.client.infrastructure.entity.clientlegal;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_client_legal", schema = "client")
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "ClientLegalEntity", requiredProperties = {"companyId"})
public class ClientLegalEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "CNPJ number of the Client.", example = "12.345.678/0001-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "company_id")
    private String companyId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    public void update(Long id, ClientLegalEntity clientLegalEntity) {
        this.id = id;
        this.companyId = clientLegalEntity.getCompanyId();
        this.clientEntity = clientLegalEntity.getClientEntity();
    }
}
