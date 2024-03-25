package com.dev.voyagewell.service.booking;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.booking.AddBookingDto;
import com.dev.voyagewell.controller.dto.calendar.CalendarDto;
import com.dev.voyagewell.controller.dto.calendar.DateDto;
import com.dev.voyagewell.controller.room.RoomController;
import com.dev.voyagewell.model.calendar.Booking;
import com.dev.voyagewell.model.calendar.BookingCalendar;
import com.dev.voyagewell.model.calendar.Calendar;
import com.dev.voyagewell.model.room.Room;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.repository.calendar.BookingCalendarRepository;
import com.dev.voyagewell.repository.calendar.BookingRepository;
import com.dev.voyagewell.repository.calendar.CalendarRepository;
import com.dev.voyagewell.repository.room.RoomRepository;
import com.dev.voyagewell.service.user.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final BookingRepository bookingRepository;
    private final BookingCalendarRepository bookingCalendarRepository;
    private final UserService userService;
    private final CalendarRepository calendarRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, BookingCalendarRepository bookingCalendarRepository, UserService userService, CalendarRepository calendarRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.userService = userService;
        this.calendarRepository = calendarRepository;
        this.roomRepository = roomRepository;
    }

    private void validateBookingAvailability(int roomId, Date checkInDate, Date checkOutDate) {
        List<Calendar> calendars = calendarRepository.findByRoomIdAndDateBetween(roomId, checkInDate, checkOutDate);
        for (Calendar calendar : calendars) {
            if (!calendar.isAvailable()) {
                throw new IllegalStateException("Date " + calendar.getDate() + " is not available");
            }
        }
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, ResourceNotFoundException.class})
    public void createBooking(int roomId, AddBookingDto addBookingDto, Principal principal) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room don't exists with id: " + roomId));

        try {
            Date checkInDate = addBookingDto.getStartDate();
            Date checkOutDate = addBookingDto.getEndDate();


            // check if the room is available for the entire booking period
            validateBookingAvailability(roomId, checkInDate, checkOutDate);

            // assuming all dates are available, proceeding with booking
            Booking newBooking = new Booking();

            User user = userService.findByEmail(principal.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found in DB"));
            newBooking.setClient(user.getClient());

            newBooking.setStartDate(checkInDate);
            newBooking.setEndDate(checkOutDate);

            // Calculate the number of days booked
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(checkInDate.toInstant(), checkOutDate.toInstant());
            newBooking.setNumberOfDaysBooked((int) daysBetween);

            // save the booking in DB
            try {
                bookingRepository.save(newBooking);
                logger.info("Booking Service: Booking with ID: {}, saved successfully for client ID: {}", newBooking.getId(), user.getClient().getId());
            } catch (Exception e) {
                logger.error("Booking Service: Error saving Booking with ID: {}, Error msj : {}", newBooking.getId(), e.getMessage());
                throw new RuntimeException("Error saving Booking with ID: " + newBooking.getId());
            }


            // Update availability of calendars
            LocalDate currentDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

            while (!currentDate.isAfter(checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                Calendar currentCalendar = calendarRepository.findByRoomIdAndDate(roomId, java.sql.Date.valueOf(currentDate))
                        .orElseThrow(() -> new ResourceNotFoundException("Calendar doesn't exist for room with id: " + roomId));

                currentCalendar.setAvailable(false); // Set availability to false for booked dates
                currentCalendar.setClient(user.getClient()); // Set the client that booked current date
                try {
                    calendarRepository.save(currentCalendar);
                    logger.info("Booking Service: Calendar with ID: {}, updated successfully for client ID: {}", currentCalendar.getId(), user.getClient().getId());
                } catch (Exception e) {
                    logger.error("Booking Service: Error updating calendar with ID: {}, Error msj : {}", currentCalendar.getId(), e.getMessage());
                    throw new RuntimeException("Error saving calendar with ID: " + currentCalendar.getId());
                }

                BookingCalendar bookingCalendar = new BookingCalendar();
                bookingCalendar.setBooking(newBooking);
                bookingCalendar.setCalendar(currentCalendar);
                try {
                    bookingCalendarRepository.save(bookingCalendar);
                    logger.info("Booking Service: Booking Calendar with ID: {}, saved successfully for Calendar ID: {}, and Booking ID: {}", bookingCalendar.getId(), currentCalendar.getId(), newBooking.getId());
                } catch (Exception e){
                    logger.error("Booking Service: Booking Calendar with ID: {}, saved failed for Calendar ID: {}, and Booking ID: {}", bookingCalendar.getId(), currentCalendar.getId(), newBooking.getId());
                    throw new RuntimeException("Error saving Booking Calendar with ID: " + bookingCalendar.getId());
                }

                currentDate = currentDate.plusDays(1);
            }

        } catch (DateTimeParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<CalendarDto> getCalendarByRoomIdAndMonthAndYear(int roomId, int month, int year) throws ResourceNotFoundException {
        List<Calendar> allCalendarsForRoom = calendarRepository.findAllByRoomId(roomId);
        if(allCalendarsForRoom.isEmpty()){
            throw new ResourceNotFoundException("Calendars dont exist for room with ID: " + roomId);
        }
        // Define start and end dates for the specified month and year in UTC timezone
        java.util.Calendar startDate = java.util.Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startDate.set(year, month - 1, 1, 0, 0, 0); // Month is 0-indexed in Calendar
        startDate.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Calendar endDate = java.util.Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endDate.set(year, month - 1, startDate.getActualMaximum(java.util.Calendar.DAY_OF_MONTH), 23, 59, 59); // Set to last day of month in UTC timezone
        endDate.set(java.util.Calendar.MILLISECOND, 999);



        List<CalendarDto> filteredCalendars = allCalendarsForRoom.stream()
                .filter( calendar -> {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(calendar.getDate());
                    return cal.after(startDate) && cal.before(endDate);
                })
                .map(calendar -> {
                    CalendarDto calendarDto = new CalendarDto();
                    calendarDto.setId(calendar.getId());
                    calendarDto.setDate(mapDateDto(calendar.getDate())); // Mapping DateDto
                    calendarDto.setAvailable(calendar.isAvailable());
                    return calendarDto;
                })
                .collect(Collectors.toList());

        return filteredCalendars;
    }
    // Helper method to map Date to DateDto
    private DateDto mapDateDto(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance(TimeZone.getTimeZone("UTC+1"));
        calendar.setTime(date);
        return new DateDto(
                Integer.toString(calendar.get(java.util.Calendar.YEAR)),
                Integer.toString(calendar.get(java.util.Calendar.MONTH) + 1), // Month is 0-indexed
                Integer.toString(calendar.get(java.util.Calendar.DATE))
        );
    }
}
