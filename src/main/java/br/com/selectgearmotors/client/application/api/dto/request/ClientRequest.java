package br.com.selectgearmotors.client.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "ProductRequest", requiredProperties = {"id", "name", "description", "price", "pic", "productCategoryId", "restaurantId"})
@Tag(name = "ProductRequest", description = "Model")
public class ClientRequest implements Serializable {

    @Schema(description = "name of the Client.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    private String name;

    @Schema(description = "name of the Client.",
            example = "V$")
    @NotNull(message = "o campo \"email\" é obrigario")
    @Size(min = 1, max = 255)
    private String email;

    @Schema(description = "Mobile Phone number of the Driver.",
            example = "(99) 9999-9999", required = true)
    @Pattern(regexp = "^\\([1-9]{2}\\) 9[7-9]{1}[0-9]{3}\\-[0-9]{4}$", message = "Mobile Phone number")
    @NotNull
    @Size(max = 15)
    private String mobile;

    @Schema(description = "picture of the Client.",
            example = "V$")
    private String pic;

    @Schema(description = "description of the Client.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String description;

    @Schema(description = "crp number of the Psychological.",
            example = "Jessica Abigail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "CPF number")
    private String socialId; //CPF

    @Schema(description = "description of the Client.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String address;

    @Schema(description = "description of the Client.",
            example = "V$")
    private Boolean dataProcessingConsent;

    @Schema(description = "Product Category of the Product.",
            example = "Bebida", ref = "ProductCategory")
    private Long clientTypeId;
}
