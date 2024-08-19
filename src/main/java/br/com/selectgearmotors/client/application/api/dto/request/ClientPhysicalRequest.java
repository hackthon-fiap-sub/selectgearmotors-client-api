package br.com.selectgearmotors.client.application.api.dto.request;

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
@Schema(description = "ClientPhysicalRequest", requiredProperties = {"id", "name"})
@Tag(name = "ClientPhysicalRequest", description = "Model")
public class ClientPhysicalRequest implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1")
    private Long id;

    @Schema(description = "Name of the Product Category.",
            example = "Bebida")
    @Size(min = 3, max = 255)
    private String socialId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long clientId;
}
