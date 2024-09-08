package br.com.selectgearmotors.client.core.domain;

import br.com.selectgearmotors.client.commons.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

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

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String socialName;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String fantasyName;

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE)
    private LocalDate foundationDate; //Data de Fundação

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
