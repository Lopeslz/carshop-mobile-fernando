package com.carshop.api.controller;

import com.carshop.api.dto.UserProfileDTO;
import com.carshop.api.model.User;
import com.carshop.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    UserProfileDTO dto = new UserProfileDTO();
                    dto.setName(user.getName());
                    dto.setCity(user.getCity());
                    dto.setBirthDate(user.getBirthDate());
                    dto.setLatitude(user.getLatitude());
                    dto.setLongitude(user.getLongitude());
                    dto.setProfileImageUrl(user.getProfileImageUrl());  // aqui ele sempre passa o valor correto!
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping(value = "/{id}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileDTO> updateProfile(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("city") String city,
            @RequestParam("birth_date") String birthDate,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam(value = "profile_image", required = false) MultipartFile imageFile
    ) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        User user = optionalUser.get();

        user.setName(name);
        user.setCity(city);
        user.setBirthDate(birthDate);
        user.setLatitude(latitude.isEmpty() ? null : Double.parseDouble(latitude));
        user.setLongitude(longitude.isEmpty() ? null : Double.parseDouble(longitude));

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get("uploads", fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());
                user.setProfileImageUrl(fileName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        userRepo.save(user);

        // 👉 devolve DTO no retorno
        UserProfileDTO dto = new UserProfileDTO();
        dto.setName(user.getName());
        dto.setCity(user.getCity());
        dto.setBirthDate(user.getBirthDate());
        dto.setLatitude(user.getLatitude());
        dto.setLongitude(user.getLongitude());
        dto.setProfileImageUrl(user.getProfileImageUrl());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/admins")
    public List<User> getAdmins() {
        return userRepo.findByIsAdminTrue();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }


    //JAVAFXXXXXXXXXXXXXX
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setIsAdmin(updatedUser.getIsAdmin());

        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    //qlogica do login






}