package com.dev.voyagewell.controller.dto.calendar;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DateDto {
    private String year;
    private String month;
    private String date;
}
