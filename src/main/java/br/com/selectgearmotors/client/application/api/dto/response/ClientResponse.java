package br.com.selectgearmotors.client.application.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ClientResponse", requiredProperties = {"id", "name", "description", "price", "pic", "productCategoryId", "restaurantId"})
@Tag(name = "ClientResponse", description = "Model")
public class ClientResponse implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1")
    private Long id;

    @Schema(description = "Name of the Product.",
            example = "Vicente")
    @Size(min = 3, max = 255)
    private String name;

    private String email;

    @Schema(description = "Name of the Product.",
            example = "Vicente")
    @Size(min = 3, max = 255)
    private String code;

    @Schema(description = "Description of the Product.",
            example = "Vicente")
    @Size(min = 0, max = 255)
    private String description;

    @Schema(description = "value the Product.",
            example = "V$")
    private String pic;

    private String socialId; //CPF

    private ClientResponse clientResponse;

}
