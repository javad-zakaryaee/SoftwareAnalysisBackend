package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "trainee", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Trainee {
    @Id
    @NonNull
    public String id;
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    public User user;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "coach_id")
    public Coach coach;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id" , referencedColumnName = "id")
    public Plan plan;

    @Column(name = "height")
    @NonNull
    public Integer height;
    @Column(name = "weight")
    @NonNull
    public Double weight;
    @Column(name = "goal")
    @NonNull
    public String goal;
}
