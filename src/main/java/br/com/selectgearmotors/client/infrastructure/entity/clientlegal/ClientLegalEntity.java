package br.com.selectgearmotors.client.infrastructure.entity.clientlegal;

import br.com.selectgearmotors.client.commons.Constants;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.domain.AuditDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE)
    @Column(name = "foundation_date")
    private LocalDate foundationDate; //Data de Nascimento

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
        this.foundationDate = clientLegalEntity.getFoundationDate();
    }
}
