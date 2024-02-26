package com.dev.voyagewell.service.hotel;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.hotel.HotelDto;
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
    public void create(HotelDto hotelDto) {
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

    @Override
    public HotelDto getHotelById(int id) throws ResourceNotFoundException {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel don't exists"));
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setHotelName(hotel.getName());
        hotelDto.setLocation(hotel.getLocation());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setPicture1(hotel.getPicture1());
        hotelDto.setPicture2(hotel.getPicture2());
        hotelDto.setPicture3(hotel.getPicture3());
        hotelDto.setPicture4(hotel.getPicture4());
        hotelDto.setPicture5(hotel.getPicture5());
        hotelDto.setFreeParking(hotel.getAmenities().isFreeParking());
        hotelDto.setRestaurant(hotel.getAmenities().isRestaurant());
        hotelDto.setBar(hotel.getAmenities().isBar());
        hotelDto.setSpa(hotel.getAmenities().isSpa());
        hotelDto.setWifi(hotel.getAmenities().isWifi());
        hotelDto.setAirConditioning(hotel.getRoomFeatures().isAirConditioning());
        hotelDto.setRoomService(hotel.getRoomFeatures().isRoomService());
        hotelDto.setBalcony(hotel.getRoomFeatures().isBalcony());
        hotelDto.setTv(hotel.getRoomFeatures().isTv());
        hotelDto.setRoomWifi(hotel.getRoomFeatures().isWifi());
        return hotelDto;
    }
}

