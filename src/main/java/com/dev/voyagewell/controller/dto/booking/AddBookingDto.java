package com.dev.voyagewell.controller.dto.booking;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AddBookingDto {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
}
