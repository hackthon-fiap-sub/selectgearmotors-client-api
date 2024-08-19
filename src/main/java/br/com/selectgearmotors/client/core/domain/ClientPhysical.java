package br.com.selectgearmotors.client.core.domain;

import br.com.selectgearmotors.client.infrastructure.entity.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ClientPhysicalEntity", requiredProperties = {"socialId"})
public class ClientPhysical extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    private Long id;

    @Schema(description = "CPF number of the Client.", example = "123.456.789-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "CPF number")
    private String socialId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long clientId;

    public void update(Long id, ClientPhysical clientPhysical) {
        this.id = id;
        this.socialId = clientPhysical.getSocialId();
        this.clientId = clientPhysical.getClientId();
    }
}
