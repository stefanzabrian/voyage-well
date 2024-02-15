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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Column(name = "number")
    @NotNull
    private Integer number;
    @Column(name = "picture_url", columnDefinition = "LONGTEXT")
    @NotNull
    @NotBlank
    private String pictureUrl;
    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    public Room(Integer number, String pictureUrl, Type type) {
        this.number = number;
        this.pictureUrl = pictureUrl;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number=" + number +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", feature=" + feature +
                ", type=" + type +
                '}';
    }
}
