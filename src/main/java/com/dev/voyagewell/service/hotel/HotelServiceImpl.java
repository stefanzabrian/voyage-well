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
    public void create(HotelAddDto hotelAddDto) {
        if (hotelRepository.findByName(hotelAddDto.getHotelName()).isPresent()) {
            throw new IllegalArgumentException("Hotel with name : " + hotelAddDto.getHotelName() + " already exists");
        } else {

            Hotel hotel = new Hotel();
            hotel.setName(hotelAddDto.getHotelName());
            hotel.setDescription(hotelAddDto.getDescription());
            hotel.setLocation(hotelAddDto.getLocation());
            hotel.setPicture1(hotelAddDto.getPicture1());
            hotel.setPicture2(hotelAddDto.getPicture2());
            hotel.setPicture3(hotelAddDto.getPicture3());
            hotel.setPicture4(hotelAddDto.getPicture4());
            hotel.setPicture5(hotelAddDto.getPicture5());

            Amenities hotelAmenities = new Amenities();
            hotelAmenities.setBar(hotelAddDto.isBar());
            hotelAmenities.setWifi(hotelAddDto.isWifi());
            hotelAmenities.setSpa(hotelAddDto.isSpa());
            hotelAmenities.setRestaurant(hotelAddDto.isRestaurant());
            hotelAmenities.setFreeParking(hotelAddDto.isFreeParking());
            try {
                amenitiesRepository.save(hotelAmenities);
                hotel.setAmenities(hotelAmenities);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving Hotel Amenities in DB");
            }

            RoomFeatures roomFeatures = new RoomFeatures();
            roomFeatures.setWifi(hotelAddDto.isRoomWifi());
            roomFeatures.setRoomService(hotelAddDto.isRoomService());
            roomFeatures.setTv(hotelAddDto.isTv());
            roomFeatures.setBalcony(hotelAddDto.isBalcony());
            roomFeatures.setAirConditioning(hotelAddDto.isAirConditioning());
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

    @Override
    public Hotel getHotelById(int id) throws ResourceNotFoundException {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel don't exists"));
        return hotel;
    }

    @Override
    public void update(int id, Hotel hotel) throws ResourceNotFoundException {
        Hotel hotelToBeUpdated = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel don't exists"));
        hotelToBeUpdated.setName(hotel.getName());
        hotelToBeUpdated.setLocation(hotel.getLocation());
        hotelToBeUpdated.setDescription(hotel.getDescription());
        hotelToBeUpdated.setPicture1(hotel.getPicture1());
        hotelToBeUpdated.setPicture1(hotel.getPicture1());
        hotelToBeUpdated.setPicture2(hotel.getPicture2());
        hotelToBeUpdated.setPicture3(hotel.getPicture3());
        hotelToBeUpdated.setPicture4(hotel.getPicture4());
        hotelToBeUpdated.setPicture5(hotel.getPicture5());

        Amenities amenitiesToBeUpdated = amenitiesRepository.findById(hotelToBeUpdated.getAmenities().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Amenities don't exists"));
        amenitiesToBeUpdated.setBar(hotel.getAmenities().isBar());
        amenitiesToBeUpdated.setWifi(hotel.getAmenities().isWifi());
        amenitiesToBeUpdated.setFreeParking(hotel.getAmenities().isFreeParking());
        amenitiesToBeUpdated.setRestaurant(hotel.getAmenities().isRestaurant());
        amenitiesToBeUpdated.setSpa(hotel.getAmenities().isSpa());
        try {
            amenitiesRepository.save(amenitiesToBeUpdated);
            hotelToBeUpdated.setAmenities(amenitiesToBeUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error saving Hotel Amenities in DB");
        }

        RoomFeatures roomFeaturesToBeUpdated = roomFeaturesRepository.findById(hotelToBeUpdated.getRoomFeatures().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Room features don't exists"));
        roomFeaturesToBeUpdated.setAirConditioning(hotel.getRoomFeatures().isAirConditioning());
        roomFeaturesToBeUpdated.setRoomService(hotel.getRoomFeatures().isRoomService());
        roomFeaturesToBeUpdated.setWifi(hotel.getRoomFeatures().isWifi());
        roomFeaturesToBeUpdated.setTv(hotel.getRoomFeatures().isTv());
        roomFeaturesToBeUpdated.setBalcony(hotel.getRoomFeatures().isBalcony());
        try {
            roomFeaturesRepository.save(roomFeaturesToBeUpdated);
            hotelToBeUpdated.setRoomFeatures(roomFeaturesToBeUpdated);
        }catch (Exception e){
            throw new RuntimeException("Error saving Hotel Room Features in DB");
        }

        try {
            hotelRepository.save(hotelToBeUpdated);
        }catch (Exception e) {
            throw new RuntimeException("Error saving Hotel in DB");
        }
    }
}

