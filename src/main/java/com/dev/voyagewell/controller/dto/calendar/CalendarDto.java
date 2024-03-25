package com.dev.voyagewell.controller.dto.calendar;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CalendarDto {
    private int id;
    private DateDto date;
    private boolean isAvailable;
}
