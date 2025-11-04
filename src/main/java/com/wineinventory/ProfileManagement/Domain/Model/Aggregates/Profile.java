package com.wineinventory.ProfileManagement.Domain.Model.Aggregates;

import com.wineinventory.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank
    @Email
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String email;

    @Size(max = 60)
    @Column(length = 60)
    private String firstName;

    @Size(max = 60)
    @Column(length = 60)
    private String lastName;

    @Size(max = 30)
    @Column(length = 30)
    private String phoneNumber;

    public Profile(String username, String email, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Profile(String username, String email) {
        this(username, email, null, null, null);
    }

    public void updateAccountInformation(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void updatePersonalInformation(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
