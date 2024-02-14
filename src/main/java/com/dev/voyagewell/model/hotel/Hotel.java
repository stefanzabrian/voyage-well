package com.dev.voyagewell.model.hotel;

import jakarta.persistence.*;
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
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "description",columnDefinition = "LONGTEXT")
    private String description;
    @OneToOne
    @JoinColumn(name = "amenities_id")
    private Amenities amenities;
    @OneToOne
    @JoinColumn(name = "room_features_id")
    private RoomFeatures roomFeatures;

}
