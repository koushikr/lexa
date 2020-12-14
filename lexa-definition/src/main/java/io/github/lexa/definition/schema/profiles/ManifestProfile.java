package io.github.lexa.definition.schema.profiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

/**
 * Created by koushikr on 21/09/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManifestProfile {

    private String manifestId;

    private EntityType entityType = EntityType.MANIFEST;

    private EntityStatus status = EntityStatus.INACTIVE;

    private Object data;

    private boolean mapped;

    @Length(
            max = 255
    )
    private String entityName;

    @Length(
            max = 255
    )
    private String displayName;

    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

}
