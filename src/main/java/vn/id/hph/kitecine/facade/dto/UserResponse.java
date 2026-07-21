package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String fullName;
    String avatar;
    String email;
    Instant createdAt;
    Instant updatedAt;
    Set<RoleResponse> roles;
}
