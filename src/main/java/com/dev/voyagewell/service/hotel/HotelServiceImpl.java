package com.dev.voyagewell.service.hotel;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.hotel.HotelAddDto;
import com.dev.voyagewell.model.hotel.Amenities;
import com.dev.voyagewell.model.hotel.Hotel;
import com.dev.voyagewell.model.hotel.RoomFeatures;
import com.dev.voyagewell.repository.hotel.AmenitiesRepository;
import com.dev.voyagewell.repository.hotel.HotelRepository;
import com.dev.voyagewell.repository.hotel.RoomFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final RoomFeaturesRepository roomFeaturesRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, AmenitiesRepository amenitiesRepository, RoomFeaturesRepository roomFeaturesRepository) {
        this.hotelRepository = hotelRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.roomFeaturesRepository = roomFeaturesRepository;
    }

    @Override
    public void create(HotelAddDto hotelDto) {
        if (hotelRepository.findByName(hotelDto.getHotelName()).isPresent()) {
            throw new IllegalArgumentException("Hotel with name : " + hotelDto.getHotelName() + " already exists");
        } else {

            Hotel hotel = new Hotel();
            hotel.setName(hotelDto.getHotelName());
            hotel.setDescription(hotelDto.getDescription());
            hotel.setLocation(hotelDto.getLocation());
            hotel.setPicture1(hotelDto.getPicture1());
            hotel.setPicture2(hotelDto.getPicture2());
            hotel.setPicture3(hotelDto.getPicture3());
            hotel.setPicture4(hotelDto.getPicture4());
            hotel.setPicture5(hotelDto.getPicture5());

            Amenities hotelAmenities = new Amenities();
            hotelAmenities.setBar(hotelDto.isBar());
            hotelAmenities.setWifi(hotelDto.isWifi());
            hotelAmenities.setSpa(hotelDto.isSpa());
            hotelAmenities.setRestaurant(hotelDto.isRestaurant());
            hotelAmenities.setFreeParking(hotelDto.isFreeParking());
            try {
                amenitiesRepository.save(hotelAmenities);
                hotel.setAmenities(hotelAmenities);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving Hotel Amenities in DB");
            }

            RoomFeatures roomFeatures = new RoomFeatures();
            roomFeatures.setWifi(hotelDto.isRoomWifi());
            roomFeatures.setRoomService(hotelDto.isRoomService());
            roomFeatures.setTv(hotelDto.isTv());
            roomFeatures.setBalcony(hotelDto.isBalcony());
            roomFeatures.setAirConditioning(hotelDto.isAirConditioning());
            try {
                roomFeaturesRepository.save(roomFeatures);
                hotel.setRoomFeatures(roomFeatures);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving Hotel Room Features in DB");
            }

            try {
                hotelRepository.save(hotel);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving Hotel in DB");
            }
        }
    }

    @Override
    public List<Hotel> getAll() throws ResourceNotFoundException {
        if (hotelRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No Hotels yet");
        }
        return hotelRepository.findAll().stream().toList();
    }
}

