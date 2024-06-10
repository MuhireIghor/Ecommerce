package com.ne.template.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ne.template.enums.EGender;
import com.ne.template.models.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Data
@Builder

public class UserPrincipal implements UserDetails {

    private UUID id;
     private String email;
     private String username;
     @JsonIgnore
     private String password;
     private EGender gender;
     private Collection<? extends GrantedAuthority> authorities;
     public static UserPrincipal create(User user) {
         List<GrantedAuthority> authorities = new ArrayList<>();
         authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName().toString()));

//         Collection<SimpleGrantedAuthority> authorities = user.getRole().stream().map(role->{
//             return new SimpleGrantedAuthority(role.getRoleName().toString());
//         }).collect(Collectors.toList());
         return new UserPrincipal(
                 user.getId(),
                 user.getEmail(),
                 user.getUsername(),
                 user.getPassword(),
                 user.getGender(),
                 authorities

         );

     }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
