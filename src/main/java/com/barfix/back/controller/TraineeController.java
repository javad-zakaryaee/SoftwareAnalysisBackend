package com.barfix.back.controller;

import com.barfix.back.model.*;
import com.barfix.back.repository.*;
import io.jsonwebtoken.Claims;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import static com.barfix.back.controller.UserController.JWTParser;

@RestController
@RequestMapping("/api/v1/trainee")
public class TraineeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private GymRepository gymRepository;
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private PlanRepository planRepository;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity getAllCoaches() {
        return new ResponseEntity(traineeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Optional<Trainee>> getTrainee(@PathVariable String id, @RequestHeader(name = "Authorization", required = false) String token) {
        if (token!=null) {
            Claims claims = JWTParser(token);
            Optional<Trainee> trainee = Optional.empty();
            if (claims.get("id").toString().equals(id)) trainee = traineeRepository.findById(id);
            if (trainee.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            else return new ResponseEntity<>(trainee, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<String> newTrainee(@RequestBody String attributes) {
        JSONObject json = new JSONObject(attributes);
        Optional<User> traineeUser = userRepository.findByEmail(json.getString("email"));
        if (traineeUser.isPresent() && traineeUser.get().getRole().equalsIgnoreCase("trainee"))
            return new ResponseEntity(null, null, HttpStatus.CONFLICT);
        else if (traineeUser.isPresent() && !traineeUser.get().getRole().equalsIgnoreCase("trainee")) {
            Trainee newTrainee = new Trainee(UUID.randomUUID().toString(), traineeUser.get(), Date.valueOf(json.getString("birthdate")), Integer.valueOf(json.getString("height")), Double.valueOf(json.getString("weight")), json.getString("goal"));
            traineeRepository.save(newTrainee);
            User updateRole = traineeUser.get();
            updateRole.setRole("trainee");
            userRepository.save(updateRole);
            JSONObject response = new JSONObject();
            response.put("id", traineeUser.get().getId());
            response.put("firstName", traineeUser.get().getFirstName());
            response.put("lastName", traineeUser.get().getLastName());
            return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity updateCoach(@RequestBody String attributes, @PathVariable String id, @RequestHeader(name = "Authorization", required = false) String token) {
        JSONObject json = new JSONObject(attributes);
        if (token != null) {
            Claims claims = JWTParser(token);
            Optional<Trainee> trainee = traineeRepository.findById(id);
            Optional<User> user = userRepository.findById(claims.get("id").toString());
            if (user.isPresent() && trainee.isPresent() && user.get().getId().equals(trainee.get().getUser().getId())) {
                Trainee traineeUpdate = trainee.get();
                if (json.has("plan")) {
                    Optional<Plan> plan = planRepository.findById(json.getString("plan"));
                    if (plan.isPresent()) {
                        traineeUpdate.setPlan(plan.get());
                        traineeUpdate.setCoach(plan.get().getCoach());
                    }
                }
                if (json.has("height")) traineeUpdate.setHeight(Integer.valueOf(json.getString("height")));
                if (json.has("weight")) traineeUpdate.setWeight(Double.valueOf(json.getString("weight")));
                if (json.has("goal")) traineeUpdate.setGoal(json.getString("goal"));

                traineeRepository.save(traineeUpdate);
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
