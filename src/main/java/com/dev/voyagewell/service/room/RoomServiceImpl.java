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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
