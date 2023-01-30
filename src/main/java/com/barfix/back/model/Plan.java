package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "plan", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Plan {
    @Id
    @NonNull
    public String id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @NonNull
    @JoinColumn(name = "coach_id")
    public Coach coach;
    @Column(name = "name")
    @NonNull
    public String name;
    @Column(name = "difficulty")
    @NonNull
    public String text;
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PlanExercise> exercises;
}
