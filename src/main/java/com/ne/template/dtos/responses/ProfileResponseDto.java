package com.ne.template.dtos.responses;

import com.ne.template.models.Role;
import com.ne.template.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class ProfileResponseDto {
    private User user;
    private Set<Role> roles;
    public ProfileResponseDto(User user, Set<Role> roles) {
        this.user = user;
        this.roles = roles;
    }
}
