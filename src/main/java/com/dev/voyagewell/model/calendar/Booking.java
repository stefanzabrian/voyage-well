package com.dev.voyagewell.model.calendar;

import com.dev.voyagewell.model.user.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "booking")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Booking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "start_date")
    private Date startDate;
    @NotNull
    @Column(name = "end_date")
    private Date endDate;
    @NotNull
    @Column(name = "number_of_days_booked")
    private Integer numberOfDaysBooked;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingCalendar> bookingCalendars = new ArrayList<>();

}
