package com.dev.voyagewell.model.hotel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotel")
@NoArgsConstructor
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", unique = true)
    @NotNull
    @NotBlank
    private String name;
    @Column(name = "location")
    @NotNull
    @NotBlank
    private String location;
    @Column(name = "description",columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String description;
    @Column(name = "picture_1",columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture1;
    @Column(name = "picture_2",columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture2;
    @Column(name = "picture_3",columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture3;
    @Column(name = "picture_4",columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture4;
    @Column(name = "picture_5",columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture5;
    @OneToOne
    @JoinColumn(name = "amenities_id")
    private Amenities amenities;
    @OneToOne
    @JoinColumn(name = "room_features_id")
    private RoomFeatures roomFeatures;

    public Hotel(String name, String location, String description, String picture1, String picture2, String picture3, String picture4, String picture5) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.picture4 = picture4;
        this.picture5 = picture5;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                ", picture4='" + picture4 + '\'' +
                ", picture5='" + picture5 + '\'' +
                ", amenities=" + amenities +
                ", roomFeatures=" + roomFeatures +
                '}';
    }
}
