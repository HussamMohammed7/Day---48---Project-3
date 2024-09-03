package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Username cannot be null")
    @Size(min = 4, max = 10, message = "Username length must be between 4 and 10 characters")
    @Column(columnDefinition = "VARCHAR(10) NOT NULL UNIQUE")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password length must be at least 6 characters")
    @Column(columnDefinition = "VARCHAR(120) NOT NULL")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name length must be between 2 and 20 characters")
    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be in a valid format")
    @Column(columnDefinition = "VARCHAR(255) NOT NULL UNIQUE")
    private String email;

    @NotNull(message = "Role cannot be null")
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "Role must be either 'CUSTOMER', 'EMPLOYEE', or 'ADMIN' only")
    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String role;

    @OneToOne(cascade = CascadeType.ALL ,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL ,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Employee employee;
//
//    public User(Integer id, String username, String password, String name, String email, String role) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.name = name;
//        this.email = email;
//        this.role = role;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
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
