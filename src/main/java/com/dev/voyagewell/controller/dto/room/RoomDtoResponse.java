package com.dev.voyagewell.controller.dto.room;

import com.dev.voyagewell.model.room.Feature;
import com.dev.voyagewell.model.room.Type;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RoomDtoResponse {
    private int id;
    private String number;
    private String description;
    private String picture1;
    private String picture2;
    private String picture3;
    private String picture4;
    private String picture5;
    private Feature feature;
    private Type type;
}
