package com.dev.voyagewell.model.calendar;

import com.dev.voyagewell.model.room.Room;
import com.dev.voyagewell.model.user.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "calendar")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    // Many calendar entries (bookings) can be associated with one room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    // Many calendar entries (bookings) can be associated with one client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "date")
    private Date date;
    @Column(name = "is_available")
    private boolean isAvailable;

    public Calendar(Date date, boolean isAvailable) {
        this.date = date;
        this.isAvailable = isAvailable;
    }
}
