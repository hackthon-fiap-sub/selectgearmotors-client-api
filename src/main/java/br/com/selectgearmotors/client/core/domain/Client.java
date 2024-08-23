package br.com.selectgearmotors.client.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Client", requiredProperties = {"id, code, name, email, mobile, pic, description, socialId, address, clientTypeId"})
@Tag(name = "Client", description = "Model")
public class Client implements Serializable {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private Long id;

    @Schema(description = "code of the Product.",
            example = "V$")
    private String code;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String name;

    private String email;

    private String mobile;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String description;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String pic;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String socialId; //CPF

    @Schema(description = "name of the Product.",
            example = "V$")
    private String address;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Boolean dataProcessingConsent;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long clientTypeId;

    public void update(Long id, Client client) {
        this.id = id;
        this.code = client.getCode();
        this.name = client.getName();
        this.email = client.getEmail();
        this.mobile = client.getMobile();
        this.description = client.getDescription();
        this.socialId = client.getSocialId();
        this.pic = client.getPic();
        this.address = client.getAddress();
        this.dataProcessingConsent = client.getDataProcessingConsent();
        this.clientTypeId = client.getClientTypeId();
    }
}
