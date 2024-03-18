package com.dev.voyagewell.model.room;

import com.dev.voyagewell.model.hotel.Hotel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room")
@NoArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Column(name = "number")
    @NotNull
    private String number;
    @Column(name = "price")
    @NotNull
    @NotBlank
    private String price;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String description;
    @Column(name = "picture_1", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture1;
    @Column(name = "picture_2", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture2;
    @Column(name = "picture_3", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture3;
    @Column(name = "picture_4", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture4;
    @Column(name = "picture_5", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String picture5;


    @OneToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;


    public Room(String number, String price, String description, String picture1, String picture2, String picture3, String picture4, String picture5, Type type) {
        this.number = number;
        this.price = price;
        this.description = description;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.picture4 = picture4;
        this.picture5 = picture5;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", number='" + number + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                ", picture4='" + picture4 + '\'' +
                ", picture5='" + picture5 + '\'' +
                ", feature=" + feature +
                ", type=" + type +
                '}';
    }
}
