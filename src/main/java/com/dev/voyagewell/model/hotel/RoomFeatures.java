package com.dev.voyagewell.model.hotel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room_features")
@NoArgsConstructor
@Getter
@Setter
public class RoomFeatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_air_conditioning")
    private boolean isAirConditioning;
    @Column(name = "is_room_service")
    private boolean isRoomService;
    @Column(name = "is_balcony")
    private boolean isBalcony;
    @Column(name = "is_tv")
    private boolean isTv;
    @Column(name = "is_wifi")
    private boolean isWifi;

    public RoomFeatures(boolean isAirConditioning, boolean isRoomService, boolean isBalcony, boolean isTv, boolean isWifi) {
        this.isAirConditioning = isAirConditioning;
        this.isRoomService = isRoomService;
        this.isBalcony = isBalcony;
        this.isTv = isTv;
        this.isWifi = isWifi;
    }
}
