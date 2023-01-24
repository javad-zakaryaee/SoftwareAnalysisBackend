package com.barfix.back.controller;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Gym;
import com.barfix.back.model.User;
import com.barfix.back.repository.GymRepository;
import com.barfix.back.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static com.barfix.back.controller.UserController.JWTParser;

@RestController
@RequestMapping("/api/v1/gym")
public class GymController {
    @Autowired
    GymRepository gymRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<String> newGym(@RequestBody String attributes) {
        JSONObject json = new JSONObject(attributes);
        Optional<User> owner = userRepository.findByEmail(json.getString("email"));
        if (owner.isPresent()) {
            Gym newGym = new Gym(UUID.randomUUID().toString(), owner.get(), json.getString("name"), Double.valueOf(json.getString("rating")), json.getString("address"), Double.valueOf(json.getString("pricePerSession")), Double.valueOf(json.getString("pricePerMonth")));
            gymRepository.save(newGym);
            JSONObject response = new JSONObject();
            response.put("id", newGym.getId());
            response.put("name", newGym.getName());
            response.put("address", newGym.getAddress());
            response.put("rating", newGym.getStarRating());
            return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity getAllGyms(){
        return new ResponseEntity(gymRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity getGymById(@PathVariable(name = "id") String id){
        if(gymRepository.findById(id).isPresent()) return new ResponseEntity(gymRepository.findById(id), HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "update/{id}", produces = "application/json")
    public ResponseEntity updateGym(@RequestBody String attributes, @PathVariable String id, @RequestHeader(name = "Authorization", required = false) String token) {
        JSONObject json = new JSONObject(attributes);
        if (!token.isBlank()) {
            Optional<Gym> gym = gymRepository.findById(id);
            if (!gym.isPresent()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            Claims claims = JWTParser(token);
            Optional<User> user = Optional.empty();
            if (claims.get("id").toString().equals(gym.get().getOwner().getId())) {
                Gym gymUpdate = gym.get();
                if (json.has("owner") && userRepository.findById(json.getString("owner")).isPresent() ) gymUpdate.setOwner(userRepository.findById(json.getString("owner")).get());
                if (json.has("name")) gymUpdate.setName(json.getString("name"));
                if (json.has("rating")) gymUpdate.setStarRating(Double.valueOf(json.getString("rating")));
                if (json.has("address")) gymUpdate.setAddress(json.getString("address"));
                if (json.has("plusCode")) gymUpdate.setPlusCode(json.getString("plusCode"));
                if (json.has("pricePerSession")) gymUpdate.setPricePerSession(Double.valueOf(json.getString("pricePerSession")));
                if (json.has("pricePerMonth")) gymUpdate.setPricePerMonth(Double.valueOf(json.getString("pricePerMonth")));
                gymRepository.save(gymUpdate);
                return new ResponseEntity(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity deleteGym(@PathVariable(name = "id") String id, @RequestHeader(name = "Authorization", required = false) String token) {
        String userIdFromToken = JWTParser(token).get("id").toString();
        Optional<Gym> gym = gymRepository.findById(id);
        if (gym.isPresent()) {
            User owner = gym.get().getOwner();
            if (owner.getId().equals(userIdFromToken)) {
                gymRepository.delete(gym.get());
                return new ResponseEntity(HttpStatus.OK);
            } else return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        } else return new ResponseEntity(HttpStatus.NOT_FOUND);

    }
}
