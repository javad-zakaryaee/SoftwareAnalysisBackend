package com.barfix.back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Table(name = "trainee", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainee {
    @Id
    public String id;
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    public User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "coach_id" , referencedColumnName = "id")
    public Coach coach;
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "plan_id" , referencedColumnName = "id")
    public Plan plan;
    @Column(name = "birthdate")
    public Date birthdate;
    @Column(name = "height")
    public int height;
    @Column(name = "weight")
    public double weight;
    @Column(name = "goal")
    public String goal;
}
