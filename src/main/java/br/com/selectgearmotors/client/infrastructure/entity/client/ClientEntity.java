package br.com.selectgearmotors.client.infrastructure.entity.client;

import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.entity.domain.AuditDomain;
import br.com.selectgearmotors.client.infrastructure.entity.media.MediaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_client", schema = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ClientEntity", requiredProperties = {"id, code, name, email, mobile, pic, description, socialId, address, clientTypeEntity"})
@Tag(name = "ClientEntity", description = "Model")
public class ClientEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "name of the Client.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Size(min = 3, max = 255)
    @Column(name = "code", length = 255)
    private String code;

    @Schema(description = "name of the Client.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Schema(description = "name of the Client.",
            example = "V$")
    @NotNull(message = "o campo \"email\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Schema(description = "Mobile Phone number of the Driver.",
            example = "(99) 9999-9999", required = true)
    @Pattern(regexp = "^\\([1-9]{2}\\) 9[7-9]{1}[0-9]{3}\\-[0-9]{4}$", message = "Mobile Phone number")
    @NotNull
    @Size(max = 15)
    private String mobile;

    @Schema(description = "Media of the User.",
            example = "1", required = true, ref = "User")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "media_id", unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MediaEntity mediaEntity;

    @Schema(description = "description of the Client.",
            example = "V$")
    @Size(min = 0, max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Schema(description = "description of the Client.",
            example = "V$")
    @Size(min = 0, max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Schema(description = "description of the Client.",
            example = "V$")
    @Column(name = "data_processing_consent")
    private Boolean dataProcessingConsent;

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "ClientCategoryEntity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_type_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClientTypeEntity clientTypeEntity;

    public void update(Long id, ClientEntity clientEntity) {
        this.id = id;
        this.code = clientEntity.getCode();
        this.name = clientEntity.getName();
        this.email = clientEntity.getEmail();
        this.mobile = clientEntity.getMobile();
        this.mediaEntity = clientEntity.getMediaEntity();
        this.description = clientEntity.getDescription();
        this.address = clientEntity.getAddress();
        this.dataProcessingConsent = clientEntity.getDataProcessingConsent();
        this.clientTypeEntity = clientEntity.getClientTypeEntity();
    }
}
