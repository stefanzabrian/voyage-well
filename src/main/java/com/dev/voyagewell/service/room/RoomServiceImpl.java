package com.dev.voyagewell.service.room;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.room.RoomAddDto;
import com.dev.voyagewell.controller.dto.room.RoomDtoResponse;
import com.dev.voyagewell.model.hotel.Hotel;
import com.dev.voyagewell.model.room.Feature;
import com.dev.voyagewell.model.room.Room;
import com.dev.voyagewell.model.room.Type;
import com.dev.voyagewell.repository.room.FeatureRepository;
import com.dev.voyagewell.repository.room.RoomRepository;
import com.dev.voyagewell.service.hotel.HotelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final FeatureRepository featureRepository;
    private final HotelService hotelService;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, FeatureRepository featureRepository, HotelService hotelService) {
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to save feature in DB");
        }

        try {
            roomRepository.save(roomToBeAdded);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save room in DB");
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
                .orElseThrow(() -> new ResourceNotFoundException("Room dont exist, Id: " + id ));
        try {
            featureRepository.delete(room.getFeature());
            try {

                roomRepository.delete(room);
            } catch (Exception e){
                throw new RuntimeException("Error deleting the room in DB");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting room's Feature");
        }
    }

    // daily challenge
    private static  List<Integer> minMax(List<Integer> givenList) {
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
}
