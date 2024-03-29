package com.dev.voyagewell.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@NoArgsConstructor
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name ="phone_number")
    private String phoneNumber;
    @Column(name = "profile_picture_url", columnDefinition = "LONGTEXT")
    private String profilePictureUrl;
    @Column(name = "bio_info", columnDefinition = "LONGTEXT")
    private String bioInfo;

    public Client(String phoneNumber, String profilePictureUrl, String bioInfo) {
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
        this.bioInfo = bioInfo;
    }
}
