package br.com.selectgearmotors.client.infrastructure.entity.clientlegal;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_client_legal", schema = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "ClientLegalEntity", requiredProperties = {"companyId"})
public class ClientLegalEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    @Column(name = "social_name")
    private String socialName;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    @Column(name = "fantasy_name")
    private String fantasyName;

    @Schema(description = "CompanyId number of the Cooperative.",
            example = "76.438.848/0001-69", required = false)
    @Column(name = "company_id")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "company Id number")
    @NotNull
    @Size(max = 14)
    private String companyId; //CNPJ

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    public void update(Long id, ClientLegalEntity clientLegalEntity) {
        this.id = id;
        this.companyId = clientLegalEntity.getCompanyId();
        this.clientEntity = clientLegalEntity.getClientEntity();
        this.socialName = clientLegalEntity.getSocialName();
        this.fantasyName = clientLegalEntity.getFantasyName();
    }
}
