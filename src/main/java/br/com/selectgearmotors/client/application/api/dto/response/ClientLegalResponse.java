package br.com.selectgearmotors.client.application.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ClientLegalRequest", requiredProperties = {"id", "name"})
@Tag(name = "ClientLegalRequest", description = "Model")
public class ClientLegalResponse implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1")
    private Long id;

    @Schema(description = "Name of the Product Category.",
            example = "Bebida")
    @Size(min = 3, max = 255)
    private String companyId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long clientId;
}
