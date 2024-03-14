package com.dev.voyagewell.service.room;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.room.RoomAddDto;
import com.dev.voyagewell.controller.dto.room.RoomDtoResponse;
import com.dev.voyagewell.controller.room.RoomController;
import com.dev.voyagewell.model.hotel.Hotel;
import com.dev.voyagewell.model.room.Feature;
import com.dev.voyagewell.model.room.Room;
import com.dev.voyagewell.model.room.Type;
import com.dev.voyagewell.model.calendar.Calendar;
import com.dev.voyagewell.repository.calendar.CalendarRepository;
import com.dev.voyagewell.repository.room.FeatureRepository;
import com.dev.voyagewell.repository.room.RoomRepository;
import com.dev.voyagewell.service.hotel.HotelService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final CalendarRepository calendarRepository;
    private final RoomRepository roomRepository;
    private final FeatureRepository featureRepository;
    private final HotelService hotelService;

    @Autowired
    public RoomServiceImpl(CalendarRepository calendarRepository, RoomRepository roomRepository, FeatureRepository featureRepository, HotelService hotelService) {
        this.calendarRepository = calendarRepository;
        this.roomRepository = roomRepository;
        this.featureRepository = featureRepository;
        this.hotelService = hotelService;
    }

    @Override
    public void add(int id, RoomAddDto roomAddDto) throws ResourceNotFoundException {
        Hotel hotel = hotelService.getHotelById(id);

        Room roomToBeAdded = new Room();
        roomToBeAdded.setHotel(hotel);
        roomToBeAdded.setNumber(roomAddDto.getNumber());
        roomToBeAdded.setDescription(roomAddDto.getDescription());
        roomToBeAdded.setPicture1(roomAddDto.getPicture1());
        roomToBeAdded.setPicture2(roomAddDto.getPicture2());
        roomToBeAdded.setPicture3(roomAddDto.getPicture3());
        roomToBeAdded.setPicture4(roomAddDto.getPicture4());
        roomToBeAdded.setPicture5(roomAddDto.getPicture5());
        roomToBeAdded.setType(Type.valueOf(roomAddDto.getType()));

        Feature feature = new Feature();
        feature.setRoomService(roomAddDto.isRoomService());
        feature.setTv(roomAddDto.isTv());
        feature.setWifi(roomAddDto.isWifi());
        feature.setHeat(roomAddDto.isHeat());
        feature.setBalcony(roomAddDto.isBalcony());
        feature.setBathroom(roomAddDto.isBathroom());
        feature.setAirConditioning(roomAddDto.isAirConditioning());

        try {
            featureRepository.save(feature);
            roomToBeAdded.setFeature(feature);
            logger.info("Service: Feature saved successfully with ID: {} ", feature.getId());
        } catch (Exception e) {
            logger.error("Service: Error saving in DB feature with id {}: {}", feature.getId(), e.getMessage());
            throw new RuntimeException("Failed to save feature in DB");
        }

        try {
            roomRepository.save(roomToBeAdded);
            logger.info("Service: Room saved successfully with ID: {}", roomToBeAdded.getId());
        } catch (Exception e) {
            logger.error("Service: Error saving in DB room with id {}: {}", roomToBeAdded.getId(), e.getMessage());
            throw new RuntimeException("Failed to save room in DB");
        }

        LocalDate currentDate = LocalDate.now();

        LocalDate endDate = currentDate.plusYears(1);

        while (currentDate.isBefore(endDate)) {
            Calendar calendar = new Calendar();

            calendar.setRoom(roomToBeAdded);
            calendar.setAvailable(true);
            calendar.setDate(java.sql.Date.valueOf(currentDate));

            try {
                calendarRepository.save(calendar);
                logger.info("Service: Calendar with ID: {} added successfully for room ID: {}", calendar.getId(), roomToBeAdded.getId());
            } catch (Exception e) {
                logger.error("Service: Error adding calendar for room with id {}: {}", roomToBeAdded.getId(), e.getMessage());
                throw new RuntimeException("Failed to save Calendar in DB for room");
            }
            currentDate = currentDate.plusDays(1);
        }

    }

    @Override
    public List<RoomDtoResponse> getAll(int id) throws ResourceNotFoundException {
        List<Room> hotelsRooms = roomRepository.findAllByHotelId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rooms for hotel with id: " + id));
        List<RoomDtoResponse> listToBeReturned = new ArrayList<>();
        hotelsRooms.forEach(room -> {
            RoomDtoResponse newRoom = new RoomDtoResponse();
            newRoom.setId(room.getId());
            newRoom.setNumber(room.getNumber());
            newRoom.setDescription(room.getDescription());
            newRoom.setFeature(room.getFeature());
            newRoom.setType(room.getType());
            newRoom.setPicture1(room.getPicture1());
            newRoom.setPicture2(room.getPicture2());
            newRoom.setPicture3(room.getPicture3());
            newRoom.setPicture4(room.getPicture4());
            newRoom.setPicture5(room.getPicture5());
            listToBeReturned.add(newRoom);
        });
        return listToBeReturned;
    }

    @Override
    public RoomDtoResponse getById(int id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room does not exists with id: " + id));
        RoomDtoResponse dtoResponse = new RoomDtoResponse();
        dtoResponse.setId(room.getId());
        dtoResponse.setType(room.getType());
        dtoResponse.setFeature(room.getFeature());
        dtoResponse.setDescription(room.getDescription());
        dtoResponse.setNumber(room.getNumber());
        dtoResponse.setPicture1(room.getPicture1());
        dtoResponse.setPicture2(room.getPicture2());
        dtoResponse.setPicture3(room.getPicture3());
        dtoResponse.setPicture4(room.getPicture4());
        dtoResponse.setPicture5(room.getPicture5());
        return dtoResponse;
    }

    @Override
    public void update(int id, RoomDtoResponse roomDtoResponse) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room does not exists with id: " + id));
        room.setType(roomDtoResponse.getType());
        room.setFeature(roomDtoResponse.getFeature());
        room.setDescription(roomDtoResponse.getDescription());
        room.setNumber(roomDtoResponse.getNumber());
        room.setPicture1(roomDtoResponse.getPicture1());
        room.setPicture2(roomDtoResponse.getPicture2());
        room.setPicture3(roomDtoResponse.getPicture3());
        room.setPicture4(roomDtoResponse.getPicture4());
        room.setPicture5(roomDtoResponse.getPicture5());
        try {
            featureRepository.save(room.getFeature());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            roomRepository.save(room);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(int id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room dont exist, Id: " + id));
        try {
            List<Calendar> calendars = calendarRepository.findAllByRoomId(id);
            calendars.forEach(calendar -> {
                calendarRepository.delete(calendar);
                logger.info("Service: Calendar with ID: {}, deleted successfully for room ID: {}", calendar.getId(), id);
            });
            featureRepository.delete(room.getFeature());
            logger.info("Service: Feature with ID: {}, deleted successfully for room ID: {}", room.getFeature().getId(), id);
            try {
                roomRepository.delete(room);
            } catch (Exception e) {
                throw new RuntimeException("Error deleting the room in DB");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting room's Feature");
        }
    }

    // daily challenge
    private static List<Integer> minMax(List<Integer> givenList) {
        List<Integer> result = new ArrayList<>();
        if (givenList == null || givenList.isEmpty()) {
            return result; // Return empty list if input is null or empty, we can also throw a custom exception anyway
        }
        int min = Collections.min(givenList);
        int max = Collections.max(givenList);

        result.add(min);
        result.add(max);

        return result;
    }

    // daily challenge
    // create a function that will take a string and split each word, then transform each work in a number, then filter them starting from the lowest value,
    // then add them all together, then convert it into a string, split by each character, then reverse the characters then join them back to a string
    private String stringChallenge(String input) {
        String[] words = input.split(" ");

        List<Integer> numbers = Arrays.stream(words)
                .map(Integer::parseInt)
                .sorted()
                .toList();

        int sum = numbers.stream()
                .mapToInt(Integer::intValue).sum();

        return new StringBuilder(String.valueOf(sum))
                .reverse().toString();
    }
}
