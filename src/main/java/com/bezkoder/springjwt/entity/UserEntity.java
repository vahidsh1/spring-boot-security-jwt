package com.bezkoder.springjwt.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
public class UserEntity extends UserDetailsImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

//    @NotBlank
//    @Size(max = 50)
//    @Email
    private String email;

    @NotBlank
    @Size(max = 200)
    private String password;


    @Column(columnDefinition = "boolean DEFAULT TRUE")
    private boolean isFirstLogin;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();

    public UserEntity(String username, String email, String password, boolean isFirstLogin, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isFirstLogin = isFirstLogin;
        this.roles = roles;
    }


    public UserEntity(String username, String email, Set<Role> roles) {
    }
}
