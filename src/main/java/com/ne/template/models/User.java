package com.ne.template.models;

import com.ne.template.enums.EGender;
import com.ne.template.utils.ExpirationTokenUtils;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;
    @Column(nullable = false,name = "date_of_birth")
    private Date dob;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String fullName = this.firstName+" "+this.lastName;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    private String password;
    @Column(name = "activation_code")
    private String activationCode;
    @Column
    private String expirationToken = ExpirationTokenUtils.generateToken();
    @OneToOne
    @JoinColumn(name ="role_id")
    private Role role;
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
    public User(String firstName, String lastName, String email, Date dob, EGender gender, String password,Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.gender=gender;
        this.password = password;
        this.role=role;

    }


}
