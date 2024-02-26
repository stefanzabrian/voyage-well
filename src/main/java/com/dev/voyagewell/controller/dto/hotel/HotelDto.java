package com.dev.voyagewell.controller.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class HotelDto {
    private int id;
    private String hotelName;
    private String location;
    private String description;
    private String picture1;
    private String picture2;
    private String picture3;
    private String picture4;
    private String picture5;
    private boolean isFreeParking;
    private boolean isRestaurant;
    private boolean isBar;
    private boolean isSpa;
    private boolean isWifi;
    private boolean isAirConditioning;
    private boolean isRoomService;
    private boolean isBalcony;
    private boolean isTv;
    private boolean isRoomWifi;
}
