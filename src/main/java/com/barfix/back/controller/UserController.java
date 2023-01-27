package com.barfix.back.controller;

import com.barfix.back.model.User;
import com.barfix.back.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.barfix.back.BackApplication.secretKey;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<String> newUser(@RequestBody String attributes) {
        JSONObject json = new JSONObject(attributes);
        Optional<User> findUser = userRepository.findByEmail(json.getString("email"));
        if (findUser.isPresent())
            return new ResponseEntity(null, null, HttpStatus.CONFLICT);
        else {
            User user = new User(UUID.randomUUID().toString(), json.getString("firstName"), json.getString("lastName"), json.getString("email"), json.getString("password"), json.getString("role"));
            userRepository.save(user);
            String generatedJWT = JWTBuilder(user);
            JSONObject response = new JSONObject();
            response.put("id", user.id);
            response.put("firstName", user.firstName);
            response.put("lastName", user.lastName);
            response.put("email", user.email);
            response.put("role", user.role);
            response.put("token", generatedJWT);
            return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
        }

    }
    @GetMapping("/get")
    public ResponseEntity<List<User>> listAll() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.size() == 0) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String id, @RequestHeader(name = "Authorization", required = false) String token) {
        if (token != null) {
            Claims claims = JWTParser(token);
            Optional<User> user = Optional.empty();
            if (claims.get("id").toString().equals(id)) user = userRepository.findById(id);
            if (user.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            else return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }



    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity updateUser(@RequestBody String attributes, @PathVariable String id, @RequestHeader(name = "Authorization", required = false) String token) {
        JSONObject json = new JSONObject(attributes);
        if (token!=null) {
            Claims claims = JWTParser(token);
            Optional<User> user = Optional.empty();
            if (claims.get("id").toString().equals(id)) user = userRepository.findById(id);
            if (user.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            else {
                User userUpdate = user.get();
                if (json.has("role")) userUpdate.setRole(json.getString("role"));
                if (json.has("email")) userUpdate.setEmail(json.getString("email"));
                if (json.has("firstName")) userUpdate.setFirstName(json.getString("firstName"));
                if (json.has("lastName")) userUpdate.setLastName(json.getString("lastName"));
                if (json.has("password")) userUpdate.setPassword(json.getString("password"));
                userRepository.save(userUpdate);
                return new ResponseEntity(HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

//    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
//    public ResponseEntity deleteUser(@PathVariable(name = "id") String id, @RequestHeader(name = "Authorization", required = false) String token) {
//        if (token == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
//        String userIdFromToken = JWTParser(token).get("id").toString();
//        Optional<User> user = userRepository.findById(id);
//        if (user.isPresent()) {
//            if (user.get().getId().equals(userIdFromToken)) {
//                userRepository.delete(user.get());
//                return new ResponseEntity(HttpStatus.OK);
//            } else return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<String> login(@RequestBody String attributes) {
        JSONObject json = new JSONObject(attributes);
        Optional<User> optionalUser = userRepository.findByEmail(json.getString("email"));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (json.get("password").equals(user.getPassword())) {
                String generatedJWT = JWTBuilder(user);
                JSONObject response = new JSONObject();
                response.put("id", user.getId());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("email", user.getEmail());
                response.put("role", user.getRole());
                response.put("token", generatedJWT);
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    private String JWTBuilder(User user) {
        return Jwts.builder()
                .signWith(secretKey)
                .setIssuer("Barfix")
                .setSubject("Userdata")
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .claim("role", user.getRole())
                .setIssuedAt(Calendar.getInstance().getTime())
                .compact();
    }
    public static Claims JWTParser(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
