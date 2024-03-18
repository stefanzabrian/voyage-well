package com.dev.voyagewell.controller.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RoomAddDto {
    @NotBlank
    @NotNull
    private String number;
    @NotBlank
    @NotNull
    private String price;
    @NotBlank
    @NotNull
    private String description;
    @NotBlank
    @NotNull
    private String picture1;
    @NotBlank
    @NotNull
    private String picture2;
    @NotBlank
    @NotNull
    private String picture3;
    @NotBlank
    @NotNull
    private String picture4;
    @NotBlank
    @NotNull
    private String picture5;
    @JsonProperty
    private boolean wifi;
    @JsonProperty
    private boolean balcony;
    @JsonProperty
    private boolean bathroom;
    @JsonProperty
    private boolean tv;
    @JsonProperty
    private boolean airConditioning;
    @JsonProperty
    private boolean heat;
    @JsonProperty
    private boolean roomService;
    @NotBlank
    @NotNull
    private String type;
}
