package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "exercise", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Exercise {
    @Id
    @NonNull
    public String id;
    @Column(name = "name")
    @NonNull
    public String name;
    @Column(name = "description")
    @NonNull
    public String description;
    @Column(name = "pic_url")
    public String picUrl;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PlanExercise> plans;
}
