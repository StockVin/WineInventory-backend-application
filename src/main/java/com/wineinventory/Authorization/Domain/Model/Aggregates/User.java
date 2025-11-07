package com.wineinventory.Authorization.Domain.Model.Aggregates;

import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 */
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 120)
    private String validationPassword;

    private UserRoles role;

    /**
     * This constructor is used by JPA to create an instance of the User entity.
     */
    protected User() {}

    /**
     * Constructor for creating a new User entity.
     * @param username The username of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param validationPassword The validation password of the user.
     * @param role The role of the user.
     */
    public User(String username, String email, String password, String validationPassword, UserRoles role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.validationPassword = validationPassword;
        this.role = role;
    }
}
