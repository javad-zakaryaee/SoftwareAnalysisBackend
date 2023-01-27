package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "coach", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Coach {

    @Id
    @NonNull
    public String id;


    @Column(name = "birthdate")
    @NonNull
    public Date birthdate;


    @ManyToOne
    @JsonBackReference
    @NonNull
    @JoinColumn(name = "gym_id")
    public Gym gym;


    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NonNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    public User user;


    @OneToMany(targetEntity = Plan.class, mappedBy = "coach")
    @JsonManagedReference
    private Set<Plan> plans;


    @OneToMany(targetEntity = Trainee.class, mappedBy = "coach")
    @JsonManagedReference
    private List<Trainee> trainees;
}
