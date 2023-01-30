package com.barfix.back.controller;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Gym;
import com.barfix.back.model.User;
import com.barfix.back.repository.CoachRepository;
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
@RequestMapping("/api/v1/coach")
public class CoachController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private GymRepository gymRepository;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity getAllCoaches() {
        return new ResponseEntity(coachRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Optional<Coach>> getCoach(@PathVariable String id) {
        Optional<Coach> coach = coachRepository.findByUser(userRepository.findById(id).get());
        if (coach.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(coach, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<String> newCoach(@RequestBody String attributes) {
        JSONObject json = new JSONObject(attributes);
        Optional<User> coachUser = userRepository.findByEmail(json.getString("email"));
        Optional<Gym> gym = gymRepository.findById(json.getString("gym"));
        Coach newCoach = new Coach(UUID.randomUUID().toString(), gym.get(), coachUser.get());
        coachRepository.save(newCoach);
        User updateRole = coachUser.get();
        updateRole.setRole("coach");
        userRepository.save(updateRole);
        JSONObject response = new JSONObject();
        response.put("id", coachUser.get().getId());
        response.put("firstName", coachUser.get().getFirstName());
        response.put("lastName", coachUser.get().getLastName());
        response.put("gym_name", newCoach.getGym().getName());
        return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);

    }

    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity updateCoach(@RequestBody String attributes, @PathVariable String id, @RequestHeader(name = "Authorization", required = false) String token) {
        JSONObject json = new JSONObject(attributes);
        if (token != null) {
            Claims claims = JWTParser(token);
            Optional<Coach> coach = coachRepository.findById(id);
            Optional<User> user = userRepository.findById(claims.get("id").toString());
            if (user.isPresent() && coach.isPresent() && user.get().getId().equals(coach.get().getUser().getId())) {
                Optional<Gym> gym = Optional.empty();
                if (json.has("gym")) gym = gymRepository.findById(json.getString("gym"));
                Coach coachUpdate = coach.get();
                if (gym.isPresent()) coachUpdate.setGym(gym.get());
                coachRepository.save(coachUpdate);
                return new ResponseEntity(HttpStatus.OK);
            } else return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

//    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
//    public ResponseEntity deleteCoach(@PathVariable(name = "id") String id, @RequestHeader(name = "Authorization", required = false) String token) {
//        String userIdFromToken = JWTParser(token).get("id").toString();
//        Optional<Coach> coach = coachRepository.findById(id);
//        if (coach.isPresent()) {
//            User user = coach.get().getUser();
//            if (user.getId().equals(userIdFromToken)) {
//                coachRepository.delete(coach.get());
//                user.setRole("trainee");
//                userRepository.save(user);
//                return new ResponseEntity(HttpStatus.OK);
//            } else return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//
//        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//    }
}
