package com.dev.voyagewell.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Email
    @NotNull
    @NotBlank
    @Column(name = "email")
    private String email;
    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotNull
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @NotBlank
    @Column(name = "nick_name")
    private String nickName;
    @NotNull
    @Column(name = "accepted_terms_conditions")
    private boolean isTermsAndConditions;
    @NotNull
    @Column(name = "accepted_privacy_policy")
    private boolean isPrivacyPolicy;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "cliend_id")
    private Client client;

    public User(String email, String password, String firstName, String lastName, String nickName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
    }
}
