package com.dev.voyagewell.controller.dto.hotel;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private boolean isFreeParking;
    @JsonProperty
    private boolean isRestaurant;
    @JsonProperty
    private boolean isBar;
    @JsonProperty
    private boolean isSpa;
    @JsonProperty
    private boolean isWifi;
    @JsonProperty
    private boolean isAirConditioning;
    @JsonProperty
    private boolean isRoomService;
    @JsonProperty
    private boolean isBalcony;
    @JsonProperty
    private boolean isTv;
    @JsonProperty
    private boolean isRoomWifi;
}
