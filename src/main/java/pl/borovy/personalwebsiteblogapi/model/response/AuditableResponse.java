package pl.borovy.personalwebsiteblogapi.model.response;

import java.time.Instant;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AuditableResponse {

    UserResponse createdBy;
    UserResponse lastModifiedBy;
    Instant createdAt;
    Instant lastModifiedAt;

}
