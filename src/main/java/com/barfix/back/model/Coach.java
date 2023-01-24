package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
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
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    public User user;
    @OneToMany(mappedBy="coach")
    private Set<Plan> plans;
}
