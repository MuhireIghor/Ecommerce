package com.ne.template.dtos.requests;

import com.ne.template.enums.EGender;
import com.ne.template.enums.ERole;
import com.ne.template.security.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email
    private String email;
    @ValidPassword(message = "Password validations are not well done")
    private String password;
    private ERole role;
    private Date dob;
    private EGender gender;

}
