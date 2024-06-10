package com.ne.template.models;

import com.ne.template.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ERole roleName;
    public Role(ERole roleName) {
        this.roleName = roleName;
    }
}
