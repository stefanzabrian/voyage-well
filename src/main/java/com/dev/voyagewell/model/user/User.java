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
    @Column(name = "email")
    @Email
    @NotNull
    @NotBlank
    private String email;
    @Column(name = "password")
    @NotNull
    @NotBlank
    private String password;
    @Column(name = "first_name")
    @NotNull
    @NotBlank
    private String firstName;
    @Column(name = "last_name")
    @NotNull
    @NotBlank
    private String lastName;
    @Column(name = "nick_name")
    @NotNull
    @NotBlank
    private String nickName;
    @Column(name = "accepted_terms_conditions")
    @NotNull
    private boolean isTermsAndConditions;
    @Column(name = "accepted_privacy_policy")
    @NotNull
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
