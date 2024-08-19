package br.com.selectgearmotors.client.infrastructure.entity.clientphysical;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_client_physical", schema = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ClientPhysicalEntity", requiredProperties = {"socialId"})
public class ClientPhysicalEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "CPF number of the Client.", example = "123.456.789-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "social_id")
    @Size(min = 1, max = 11)
    private String socialId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    public void update(Long id, ClientPhysicalEntity clientPhysicalEntity) {
        this.id = id;
        this.socialId = clientPhysicalEntity.getSocialId();
        this.clientEntity = clientPhysicalEntity.getClientEntity();
    }
}
