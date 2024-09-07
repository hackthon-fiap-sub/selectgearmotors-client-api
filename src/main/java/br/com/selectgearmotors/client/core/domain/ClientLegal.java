package br.com.selectgearmotors.client.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Schema(description = "ClientLegalEntity", requiredProperties = {"companyId"})
public class ClientLegal implements Serializable {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    private Long id;

    @Schema(description = "CNPJ number of the Client.", example = "12.345.678/0001-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "CNPJ number")
    private String companyId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long clientId;

    public void update(Long id, ClientLegal clientLegal) {
        this.id = id;
        this.companyId = clientLegal.getCompanyId();
        this.clientId = clientLegal.getClientId();
    }
}
