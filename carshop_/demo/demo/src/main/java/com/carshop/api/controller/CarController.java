package com.carshop.api.controller;

import com.carshop.api.model.Car;
import com.carshop.api.model.User;
import com.carshop.api.repository.CarRepository;
import com.carshop.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<Car>> searchCars(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String variant
    ) {

        if (title == null && year == null && variant == null) {
            return ResponseEntity.ok(carRepo.findAllByOrderByCreatedAtDesc());
        }
        return ResponseEntity.ok(carRepo.searchCars(title, year, variant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        return carRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Car> getUserCars(@PathVariable Long userId) {
        return carRepo.findBySellerId(userId);
    }

    // ✅ Método adaptado para multipart/form-data (cRIANDO CARRO)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Car> createCar(
            @RequestParam("title") String title,
            @RequestParam("price") String price,
            @RequestParam("year") String year,
            @RequestParam("mileage") String mileage,
            @RequestParam("variant") String variant,
            @RequestParam("meetingLat") String meetingLat,
            @RequestParam("meetingLng") String meetingLng,
            @RequestParam("image") MultipartFile image,
            @RequestParam("sellerId") Long sellerId
    ) {
        try {
            User seller = userRepo.findById(sellerId).orElse(null);
            if (seller == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Garante que o diretório de uploads existe
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                boolean created = uploadFolder.mkdirs();
                if (!created) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(null);
                }
            }

            // Salva o arquivo com nome único
            String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            image.transferTo(dest);

            Car car = new Car();
            car.setTitle(title);
            car.setPrice(price);
            car.setYear(year);
            car.setMileage(mileage);
            car.setVariant(variant);
            car.setMeetingLat(Double.valueOf(meetingLat));
            car.setMeetingLng(Double.valueOf(meetingLng));
            car.setImageUrl(filename);
            car.setCreatedAt(LocalDateTime.now());
            car.setSeller(seller);

            Car saved = carRepo.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Car> updateCar(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String price,
            @RequestParam String year,
            @RequestParam String mileage,
            @RequestParam String variant,
            @RequestParam String meetingLat,
            @RequestParam String meetingLng,
            @RequestParam(required = false) MultipartFile image
    ) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar.isEmpty()) return ResponseEntity.notFound().build();

        Car car = optionalCar.get();
        car.setTitle(title);
        car.setPrice(price);
        car.setYear(year);
        car.setMileage(mileage);
        car.setVariant(variant);
        car.setMeetingLat(Double.valueOf(meetingLat));
        car.setMeetingLng(Double.valueOf(meetingLng));

        if (image != null && !image.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                new File(uploadDir).mkdirs();
                String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                image.transferTo(new File(uploadDir + filename));
                car.setImageUrl(filename);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.ok(carRepo.save(car));
    }



}
